package org.tensorflow.lite.examples.objectdetection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.tensorflow.lite.examples.objectdetection.data.model.LoggedInUser
import org.tensorflow.lite.examples.objectdetection.util.SessionManager

class RegistroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        auth = FirebaseAuth.getInstance()
        val buttonRegistrarse = findViewById<Button>(R.id.buttonRegistrarse)

        buttonRegistrarse.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.editTextNombre).text.toString()
            val correo = findViewById<EditText>(R.id.editTextCorreo).text.toString()
            val contrasena = findViewById<EditText>(R.id.editTextContrasena).text.toString()
            val esAlumno = if (findViewById<RadioButton>(R.id.radioButtonAlumno).isChecked) "Alumno" else "Docente"
            registrarEnFirebase(nombre, correo, contrasena, esAlumno)
        }

        val btnVolver = findViewById<Button>(R.id.btnVolver)

        btnVolver.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                // Aquí puedes realizar cualquier acción que desees al hacer clic en el botón "Volver"
                finish() // Esto cerrará la actividad actual y volverá a la actividad anterior
            }
        })
    }

    private fun registrarEnFirebase(nombre: String, correo: String, contrasena: String, esAlumno: String) {
        auth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    // Aquí puedes agregar más información sobre el usuario a Firestore si es necesario
                    val uid = user?.uid ?: ""

                    // Crear un mapa con los datos adicionales del usuario
                    val userData = hashMapOf(
                        "nombre" to nombre,
                        "correo" to correo,
                        "esAlumno" to esAlumno
                    )

                    // Guardar los datos en Firestore
                    val db = FirebaseFirestore.getInstance()
                    db.collection("usuarios").document(uid)
                        .set(userData)
                        .addOnSuccessListener {
                            // Registro exitoso
                            SessionManager.setCurrentUser(user)
                            Log.d("APP_MACHONA", "registro bien")
                        }
                        .addOnFailureListener {
                            // Manejar el error al guardar los datos
                            Log.d("APP_MACHONA", "registro mal")
                        }
                } else {
                    // Manejar el error de registro
                }
            }
    }
}