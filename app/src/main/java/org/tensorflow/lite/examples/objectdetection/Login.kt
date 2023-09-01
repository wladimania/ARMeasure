package org.tensorflow.lite.examples.objectdetection

import android.app.Application;
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp;
import org.tensorflow.lite.examples.objectdetection.util.SessionManager

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

            // Sign in the user
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // The user is signed in
                        Log.d("LOGIN-FIREBASE", "Sign in successful!")
                        val user = auth.currentUser
                        SessionManager.setCurrentUser(user)
                        startActivity(Intent(this, MenuActivity::class.java))
                    } else {
                        // The sign in failed
                        Log.d("LOGIN-FIREBASE", "Sign in failed: " + task.exception.toString())
                    }
                }
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
