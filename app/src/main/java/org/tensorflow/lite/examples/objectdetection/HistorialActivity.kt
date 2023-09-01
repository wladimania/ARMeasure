package org.tensorflow.lite.examples.objectdetection

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.objectdetection.util.LecturaAdapter
import org.tensorflow.lite.examples.objectdetection.util.Lecturas
import org.tensorflow.lite.examples.objectdetection.util.SessionManager


class HistorialActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        val btnVolver = findViewById<Button>(R.id.btnVolver)

        btnVolver.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                // Aquí puedes realizar cualquier acción que desees al hacer clic en el botón "Volver"
                finish() // Esto cerrará la actividad actual y volverá a la actividad anterior
            }
        })

        val currentUser = SessionManager.getCurrentUser()
        if (currentUser != null) {

            val clas = Lecturas()

//            clas.buscarRegistrosPorCondicion("idCurso", currentUser) { registrosList ->
            clas.listarTodosLosRegistros() { registrosList ->
//                for (registro in registrosList) {
//                    val nombre = registro["nombre"]
//                    val figura = registro["figura"]
//                    val fecha = registro["fecha"]
//                    val acerto = registro["acerto"]
//                }
                val registros = registrosList
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                val registroAdapter = LecturaAdapter(registros)
                recyclerView.adapter = registroAdapter
                recyclerView.layoutManager = LinearLayoutManager(this);
            }



        }



    }
}
