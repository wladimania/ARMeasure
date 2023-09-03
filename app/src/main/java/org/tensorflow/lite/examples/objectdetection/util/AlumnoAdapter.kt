package org.tensorflow.lite.examples.objectdetection.util

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.tensorflow.lite.examples.objectdetection.HistorialActivity
import org.tensorflow.lite.examples.objectdetection.R

class AlumnoAdapter(private val registros: List<Map<String, Any>>) :
    RecyclerView.Adapter<AlumnoAdapter.RegistroViewHolder>() {
    lateinit var contextParent: Context

    class RegistroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.cardViewAlumno)
        val nombreAlumno: TextView = itemView.findViewById(R.id.nombreAlumno)
        val correoAlumno: TextView = itemView.findViewById(R.id.correoAlumno)
        val totalIntentos: TextView = itemView.findViewById(R.id.totalIntentos)
        val respuestasCorrectas: TextView = itemView.findViewById(R.id.respuestasCorrectas)
        val respuestasIncorrectas: TextView = itemView.findViewById(R.id.respuestasIncorrectas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_alumno, parent, false)
        this.contextParent = parent.context
        return RegistroViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
        val registro = registros[position]
        if (registro["id"].toString() != SessionManager.getCurrentUser()?.uid) {
            holder.nombreAlumno.text = registro["nombre"].toString()
            holder.correoAlumno.text = registro["correo"].toString()

            Log.d(
                "APP_MACHONA",
                "IDS" + registro["id"].toString() + " - " + registro["idCurso"].toString()
            )
            calcularTotales(holder, registro["id"].toString(), registro["idCurso"].toString())

            holder.card.setOnClickListener {
                val intent = Intent(this.contextParent, HistorialActivity::class.java)
                intent.putExtra("id", registro["id"].toString())
                ContextCompat.startActivity(this.contextParent, intent, null)
            }
        }
    }

    override fun getItemCount(): Int {
        return registros.size
    }

    fun calcularTotales(holder: RegistroViewHolder, idJugador: String, idCurso: String) {
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
            holder.totalIntentos.text = "Total de intentos: $totalIntentos"
            holder.respuestasCorrectas.text = "Respuestas correctas: $numAciertos"
            holder.respuestasIncorrectas.text = "Respuestas incorrectas: $numFallos"
        }
    }


}
