package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.tensorflow.lite.examples.objectdetection.util.AccesoDAO
import org.tensorflow.lite.examples.objectdetection.util.UsuariosDAO

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Set up the login button
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            // Get the email and password from the UI
            val email = findViewById<EditText>(R.id.username).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Por favor, ingresa tu correo electrónico y contraseña",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            // Sign in the user
            /*auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // The user is signed in
                        Log.d("LOGIN-FIREBASE", "Sign in successful!")
                        val user = auth.currentUser
                        SessionManager.setCurrentUser(user)
                        Log.d("APP_MACHONA", "user name: " + user?.displayName)
                        //user?.let { SessionManager.setCurrentUser(it) }
                        startActivity(Intent(this, MenuActivity::class.java))
                    } else {
                        // The sign in failed
                        Log.d("LOGIN-FIREBASE", "Sign in failed: " + task.exception.toString())

                        // Show a toast
                        Toast.makeText(this, "Error: " + task.exception!!.message, Toast.LENGTH_LONG).show()
                    }
                }
            */
            val authManager = UsuariosDAO()
            authManager.iniciarSesion(
                email,
                password,
                object : UsuariosDAO.OnSignInCompleteCallback {
                    override fun onSignInSuccess(user: FirebaseUser?) {
                        // registrar el acceso
                        var accesoD = AccesoDAO()
                        if (user != null) {
                            accesoD.guardarRegistro(user.uid,
                                onComplete = {
                                    println("Guardado exitoso")
                                }, onError = { excepcion ->
                                    println("Error durante el guardado: $excepcion")
                                })
                        }
                        // El inicio de sesión fue exitoso, puedes navegar a la siguiente pantalla o realizar acciones adicionales
                        Toast.makeText(this@Login, "Inicio de sesión exitoso", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this@Login, MenuActivity::class.java))
                    }

                    override fun onSignInFailure(errorMessage: String?) {
                        // Hubo un error durante el inicio de sesión, muestra un mensaje de error o realiza acciones de manejo de errores
                        Toast.makeText(
                            this@Login,
                            "Error durante el inicio de sesión: $errorMessage",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }

        val btnRecuperarClave = findViewById<Button>(R.id.btnRecuperarClave)
        val btnRegistrarUsuario = findViewById<Button>(R.id.btnRegistrarUsuario)
        btnRecuperarClave.setOnClickListener {
            startActivity(Intent(this, RecoverAccountActivity::class.java))
        }
        btnRegistrarUsuario.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }
}
