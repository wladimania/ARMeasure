package org.tensorflow.lite.examples.objectdetection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.tensorflow.lite.examples.objectdetection.data.model.DataUser
import org.tensorflow.lite.examples.objectdetection.util.SessionManager

class vercodigo : AppCompatActivity() {

    private lateinit var tvCode: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vercodigo)

        tvCode = findViewById(R.id.tvCode)

        // Supongamos que aquí obtienes el nuevo código
        val userInfo = SessionManager.getDataUser()
        if (userInfo != null) {
            val nuevoCodigo = userInfo.getIdCurso()
            // Actualizar el TextView con el nuevo código
            tvCode.text = nuevoCodigo
        }

        val btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                finish() // Esto cerrará la actividad actual y volverá a la actividad anterior
            }
        })
    }
}