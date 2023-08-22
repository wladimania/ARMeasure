package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.View

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
    }

    fun onStartARMeasureClick(view: View) {
        // Código para manejar el clic en el botón "Empezar ARMeasure"
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun onHistoryClick(view: View) {
        // Código para manejar el clic en el botón "Historial"
    }

    fun onSettingsClick(view: View) {
        // Código para manejar el clic en el botón "Configuración"
    }

    fun onProfileClick(view: View) {
        // Código para manejar el clic en el botón "Perfil"
    }
}
