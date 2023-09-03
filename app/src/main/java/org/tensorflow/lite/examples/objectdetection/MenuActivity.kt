package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.widget.Button
import androidx.room.Room
import org.tensorflow.lite.examples.objectdetection.util.SessionManager

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.menu)

        val userInfo = SessionManager.getDataUser()
        if (userInfo != null) {
            Log.d("APP_MACHONA", "tipousuario" + userInfo.getEsAlumno())
            val esAlumno = userInfo.getEsAlumno() == "Alumno"

            val modificarCodigoButton = findViewById<Button>(R.id.modificarCodigo)
            val verCodigoButton = findViewById<Button>(R.id.verCodigo)
            if (esAlumno) {
                modificarCodigoButton.visibility = View.GONE
                verCodigoButton.visibility = View.VISIBLE
            }
            if (!esAlumno) {
                modificarCodigoButton.visibility = View.GONE
                verCodigoButton.visibility = View.VISIBLE
            }
        }
    }

    fun onStartARMeasureClick(view: View) {
        // Código para manejar el clic en el botón "Empezar ARMeasure"
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun onHistoryClick(view: View) {
        startActivity(Intent(this, HistorialActivity::class.java))
    }

    fun onSettingsClick(view: View) {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    fun onProfileClick(view: View) {
        startActivity(Intent(this, ProfileActivity::class.java))
    }
    fun onManageCodeClick(view: View) {
        startActivity(Intent(this, modificarcodigo::class.java))
    }
    fun onViewCodeClick(view: View) {
        startActivity(Intent(this, vercodigo::class.java))
    }
}
