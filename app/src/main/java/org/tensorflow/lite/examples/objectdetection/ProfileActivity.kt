package org.tensorflow.lite.examples.objectdetection

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.objectdetection.util.LecturasDAO
import org.tensorflow.lite.examples.objectdetection.util.SessionManager

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

         val usernameText = findViewById<TextView>(R.id.usernameText)
         val emailText = findViewById<TextView>(R.id.emailText)


        // Supongamos que aquí obtienes el nuevo código
        val userInfo = SessionManager.getDataUser()
        val userFir = SessionManager.getCurrentUser()
        if (userInfo != null && userFir != null) {
            val nuevoCodigo = userInfo.getIdCurso()
            // Actualizar el TextView con el nuevo código
            usernameText.text = userInfo.getNombre()
            emailText.text = userInfo.getCorreo()
            calcularTotales(userFir.uid, userInfo.getIdCurso())
        }

        val btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                finish() // Esto cerrará la actividad actual y volverá a la actividad anterior
            }
        })


        // Inicializa tus vistas y configura sus atributos aquí si es necesario
        // Ejemplo:
        // val profileImage = findViewById<ImageView>(R.id.profileImage)

        // val editProfileButton = findViewById<Button>(R.id.editProfileButton)

        // Configura tus vistas y maneja eventos aquí si es necesario
        // Ejemplo:
        // usernameText.text = "Nombre de Usuario"
        // emailText.text = "correo@example.com"
        // editProfileButton.setOnClickListener {
        //     // Código para manejar el clic en el botón "Editar Perfil"
        // }
    }

    fun calcularTotales(idJugador: String, idCurso: String) {
        val clas = LecturasDAO()
        clas.buscarRegistrosPor2Condicion(
            "idJugador",
            idJugador,
            "idCurso",
            idCurso
        ) { registrosList ->
            val totalIntentos = registrosList.size
            var numAciertos = 0
            var numFallos = 0
            registrosList.forEach { registro ->
                if (registro["acerto"] == "Si") {
                    numAciertos++;
                } else  if (registro["acerto"] == "No") {
                    numFallos++;
                }
            };
            findViewById<TextView>(R.id.totalIntentos).text = "Total de intentos: $totalIntentos"
            findViewById<TextView>(R.id.respuestasCorrectas).text = "Respuestas correctas: $numAciertos"
            findViewById<TextView>(R.id.respuestasIncorrectas).text = "Respuestas incorrectas: $numFallos"
        }
    }
}
