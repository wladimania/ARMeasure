package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.View
import androidx.room.Room

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
        startActivity(Intent(this, HistorialActivity::class.java))
    }

    fun onSettingsClick(view: View) {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    fun onProfileClick(view: View) {
        startActivity(Intent(this, ProfileActivity::class.java))
    }
}
