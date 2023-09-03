package org.tensorflow.lite.examples.objectdetection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.tensorflow.lite.examples.objectdetection.util.SessionManager
import org.tensorflow.lite.examples.objectdetection.util.UsuariosDAO

class modificarcodigo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificarcodigo)


        val btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                finish() // Esto cerrará la actividad actual y volverá a la actividad anterior
            }
        })

        val txtCodigo: EditText = findViewById(R.id.etCode)
        val btnGuardarCambios: Button = findViewById(R.id.btnGuardarCambios)
        btnGuardarCambios.setOnClickListener {
            val userDao = UsuariosDAO()
            val user = SessionManager.getCurrentUser()
            val userInfo = SessionManager.getDataUser()
            if (userInfo != null && user != null) {
                val userData = hashMapOf(
                    "nombre" to userInfo.getNombre(),
                    "correo" to userInfo.getCorreo(),
                    "esAlumno" to userInfo.getEsAlumno(),
                    "idCurso" to txtCodigo.text.toString()
                )
                // actualiza en session
                SessionManager.getDataUser()?.setIdCurso(txtCodigo.text.toString())
                userDao.actualizarRegistro(user.uid, userData,
                    onComplete = {
                        println("Guardado exitoso")
                    },
                    onError = { excepcion ->
                        println("Error durante el guardado: $excepcion")
                    }
                )
            }
        }
    }
}