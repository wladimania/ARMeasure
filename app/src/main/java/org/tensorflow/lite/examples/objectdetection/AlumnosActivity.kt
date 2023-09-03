package org.tensorflow.lite.examples.objectdetection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.objectdetection.util.AlumnoAdapter
import org.tensorflow.lite.examples.objectdetection.util.LecturaAdapter
import org.tensorflow.lite.examples.objectdetection.util.LecturasDAO
import org.tensorflow.lite.examples.objectdetection.util.UsuariosDAO

class AlumnosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alumnos)

        val btnVolver = findViewById<Button>(R.id.btnVolver)

        btnVolver.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                // Aquí puedes realizar cualquier acción que desees al hacer clic en el botón "Volver"
                finish() // Esto cerrará la actividad actual y volverá a la actividad anterior
            }
        })

        val id = intent.getStringExtra("id")
        if (id != null) {
            Log.d("APP_MACHONA", "idCurso: " + id)
            val clas = UsuariosDAO()
            clas.buscarRegistrosPorCondicion("idCurso", id) { registrosList ->
                val registros = registrosList
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                val registroAdapter = AlumnoAdapter(registros)
                recyclerView.adapter = registroAdapter
                recyclerView.layoutManager = LinearLayoutManager(this);
            }

        }
    }
}