package org.tensorflow.lite.examples.objectdetection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RecoverAccountActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_account)

        val editTextCorreoRecuperacion = findViewById<EditText>(R.id.editTextCorreoRecuperacion)
        val buttonEnviarRecuperacion = findViewById<Button>(R.id.buttonEnviarRecuperacion)

        buttonEnviarRecuperacion.setOnClickListener {
            val correoRecuperacion = editTextCorreoRecuperacion.text.toString()

            auth.sendPasswordResetEmail(correoRecuperacion)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Se ha enviado un correo de recuperación a $correoRecuperacion", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "No se pudo enviar el correo de recuperación", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        btnVolver.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                finish() // Esto cerrará la actividad actual y volverá a la actividad anterior
            }
        })
    }
}