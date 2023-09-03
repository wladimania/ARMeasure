/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tensorflow.lite.examples.objectdetection


import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityMainBinding
import com.ingenieriajhr.teachablemachine.tflite.ClassifyImageTf
import com.ingenieriajhr.teachablemachine.tflite.ReturnInterpreter
import com.ingenieriiajhr.jhrCameraX.BitmapResponse
import com.ingenieriiajhr.jhrCameraX.CameraJhr
import org.tensorflow.lite.examples.objectdetection.util.LecturasDAO
import org.tensorflow.lite.examples.objectdetection.util.SessionManager


/**
 * Main entry point into our app. This app follows the single-activity pattern, and all
 * functionality is implemented in the form of fragments.
 */
class MainActivity : AppCompatActivity() {
    private var isResetting = false
    lateinit var binding: ActivityMainBinding
    lateinit var cameraJhr: CameraJhr
    lateinit var classifyImageTf: ClassifyImageTf
    val formulasCorrectas = mapOf(
        "CUADRADO" to "Área del cuadrado: Lado * Lado",
        "ESTRELLA" to "Área de la estrella: (Diagonal mayor * Diagonal menor) / 2",
        "TRIANGULO" to "Área del triángulo: (Base * Altura) / 2",
        "CIRCULO" to "Área del círculo: π * Radio^2",
        "HEXAGONO" to "Área del hexágono: (Lado * Apotema) / 2",
        "RECTANGULO" to "Área del rectángulo: Base * Altura",
        "ROMBO" to "Área del rombo: (Diagonal mayor * Diagonal menor) / 2"
    )

    companion object {
        const val INPUT_SIZE = 224
        const val OUTPUT_SIZE = 3
    }

    val classes =
        arrayOf("CUADRADO", "ESTRELLA", "TRIANGULO", "CIRCULO", "HEXAGONO", "RECTANGULO", "ROMBO")

    private var correctAnswer: String? = null
    private fun clearFormulaBackgrounds() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            // Código a ejecutar después de 3 segundos
            // Aquí puedes agregar cualquier acción que desees realizar después del retraso
            binding.textViewFormulaCorrect.setBackgroundResource(R.drawable.border_orange)
            binding.textViewFormulaIncorrect1.setBackgroundResource(R.drawable.border_orange)
            binding.textViewFormulaIncorrect2.setBackgroundResource(R.drawable.border_orange)
        }, 10000) // 3000 milisegundos = 3 segundos
    }


    private fun handleFormulaOptionClick(clickedView: TextView, formulaOptions: List<TextView>) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startTimer()
            // reset()
            clearFormulaBackgrounds()
            isResetting = false
        }, 10000) // 10000 milisegundos = 10 segundos

        val formulaClickeada = clickedView.text.toString()
        var resu = "No";
        if (formulaClickeada == formulasCorrectas[correctAnswer]) {
            resu = "Si"
            clickedView.setBackgroundResource(R.drawable.background_gree)
        } else {
            for (optionView in formulaOptions) {
                if (optionView == clickedView) {
                    optionView.setBackgroundResource(R.drawable.background_red)
                } else if (formulasCorrectas[correctAnswer] == optionView.text.toString()) {
                    optionView.setBackgroundResource(R.drawable.background_gree)
                }
            }
        }
        val clas = LecturasDAO()
        val user = SessionManager.getCurrentUser();
        val userInfo = SessionManager.getDataUser();
        val idAcceso = SessionManager.getIdAcceso();
        if (user != null && userInfo != null && idAcceso != null) {
            Log.d("APP_MACHONA", "nombre de usuario" + userInfo.getNombre())
            clas.guardarRegistro(user.uid,
                userInfo.getIdCurso(),
                userInfo.getNombre(),
                this.correctAnswer.toString(),
                resu,
                idAcceso,
                onComplete = {
                    // La operación de guardado se ha completado con éxito
                    println("Guardado exitoso")
                    // Aquí puedes realizar otras acciones después de un guardado exitoso
                },
                onError = { excepcion ->
                    // Ocurrió un error durante la operación de guardado
                    println("Error durante el guardado: $excepcion")
                    // Aquí puedes manejar el error de acuerdo a tus necesidades
                })
        }
    }

    private fun startTimer() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            clearFormulaBackgrounds()
        }, 10000) // 10000 milisegundos = 10 segundos
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val formulaOptions = listOf(
            binding.textViewFormulaCorrect,
            binding.textViewFormulaIncorrect1,
            binding.textViewFormulaIncorrect2
        )
        cameraJhr = CameraJhr(this)
        classifyImageTf = ClassifyImageTf(this)
        binding.textViewFormulaCorrect.setOnClickListener {
            handleFormulaOptionClick(binding.textViewFormulaCorrect, formulaOptions)
        }

        binding.textViewFormulaIncorrect1.setOnClickListener {
            handleFormulaOptionClick(binding.textViewFormulaIncorrect1, formulaOptions)
        }

        binding.textViewFormulaIncorrect2.setOnClickListener {
            handleFormulaOptionClick(binding.textViewFormulaIncorrect2, formulaOptions)
        }
        startTimer()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (cameraJhr.allpermissionsGranted() && !cameraJhr.ifStartCamera) {
            startCameraJhr()
        } else {
            cameraJhr.noPermissions()
        }
    }

    private fun startCameraJhr() {
        var timeRepeat = System.currentTimeMillis()
        cameraJhr.addlistenerBitmap(object : BitmapResponse {
            override fun bitmapReturn(bitmap: Bitmap?) {
                if (bitmap != null) {
                    if (System.currentTimeMillis() > timeRepeat + 10000) {
                        classifyImage(bitmap)
                        timeRepeat = System.currentTimeMillis()
                    }
                }
            }
        })
        cameraJhr.initBitmap()
        cameraJhr.initImageProxy()
        cameraJhr.start(1, 0, binding.cameraPreview, true, false, true)
    }

    private fun classifyImage(img: Bitmap?) {
        val imgReScale = Bitmap.createScaledBitmap(img!!, INPUT_SIZE, INPUT_SIZE, false)
        classifyImageTf.listenerInterpreter(object : ReturnInterpreter {
            override fun classify(confidence: FloatArray, maxConfidence: Int) {
                val predictedClass = classes[maxConfidence]
                val confidenceString = confidence[maxConfidence].decimal()
                correctAnswer = predictedClass
                binding.txtResult.UiThread("Elije la formula correcta para medir el area de un : $predictedClass\nLvl Detección: $confidenceString")
                clearFormulaTexts()
                clearFormulaBackgrounds()
                when (predictedClass) {

                    "CUADRADO" -> {
                        val formulas = listOf(
                            "Área del cuadrado: Lado * Lado",
                            "Área del cuadrado: Lado * Lado / 2",
                            "Área del cuadrado: Lado + Lado + Lado + Lado"
                        )
                        setRandomFormulaTexts(formulas)
                    }

                    "ESTRELLA" -> {
                        val formulas = listOf(
                            "Área de la estrella: (Diagonal mayor * Diagonal menor) / 2",
                            "Área de la estrella: Base * Altura",
                            "Área de la estrella: (Base + Altura) / 2"
                        )
                        setRandomFormulaTexts(formulas)
                    }

                    "TRIANGULO" -> {
                        val formulas = listOf(
                            "Área del triángulo: (Base * Altura) / 2",
                            "Área del triángulo: Lado * Lado * Lado",
                            "Área del triángulo: Diagonal mayor * Diagonal menor / 2"
                        )
                        setRandomFormulaTexts(formulas)
                    }

                    "CIRCULO" -> {
                        val formulas = listOf(
                            "Área del círculo: π * Radio^2",
                            "Área del círculo: Lado * Lado",
                            "Área del círculo: Diagonal mayor * Diagonal menor / 2"
                        )
                        setRandomFormulaTexts(formulas)
                    }

                    "HEXAGONO" -> {
                        val formulas = listOf(
                            "Área del hexágono: (Lado * Apotema) / 2",
                            "Área del hexágono: Diagonal mayor * Diagonal menor / 2",
                            "Área del hexágono: Base * Altura"
                        )
                        setRandomFormulaTexts(formulas)
                    }

                    "RECTANGULO" -> {
                        val formulas = listOf(
                            "Área del rectángulo: Base * Altura",
                            "Área del rectángulo: Lado * Lado",
                            "Área del rectángulo: (Base + Altura) / 2"
                        )
                        setRandomFormulaTexts(formulas)
                    }

                    "ROMBO" -> {
                        val formulas = listOf(
                            "Área del rombo: (Diagonal mayor * Diagonal menor) / 2",
                            "Área del rombo: Lado * Lado",
                            "Área del rombo: Base * Altura"
                        )
                        setRandomFormulaTexts(formulas)
                    }

                    else -> {
                        clearFormulaTexts()
                        clearFormulaBackgrounds()
                    }
                }
            }
        })

        classifyImageTf.classify(imgReScale)
    }

    private fun setRandomFormulaTexts(formulas: List<String>) {
        val shuffledFormulas = formulas.shuffled()
        binding.textViewFormulaCorrect.text = shuffledFormulas[0]
        binding.textViewFormulaIncorrect1.text = shuffledFormulas[1]
        binding.textViewFormulaIncorrect2.text = shuffledFormulas[2]
    }


    private fun setFormulaTexts(
        correctFormula: String,
        incorrectFormula1: String,
        incorrectFormula2: String
    ) {
        binding.textViewFormulaCorrect.text = correctFormula
        binding.textViewFormulaIncorrect1.text = incorrectFormula1
        binding.textViewFormulaIncorrect2.text = incorrectFormula2
    }

    private fun clearFormulaTexts() {
        binding.textViewFormulaCorrect.text = ""
        binding.textViewFormulaIncorrect1.text = ""
        binding.textViewFormulaIncorrect2.text = ""
    }

    private fun TextView.UiThread(string: String) {
        runOnUiThread {
            this.text = string
        }
    }

    private fun Float.decimal(): String {
        return "%.2f".format(this)
    }
}