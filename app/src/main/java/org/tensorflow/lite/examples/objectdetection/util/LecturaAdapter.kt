package org.tensorflow.lite.examples.objectdetection.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.objectdetection.R

class LecturaAdapter(private val registros: List<Map<String, Any>>) :
    RecyclerView.Adapter<LecturaAdapter.RegistroViewHolder>() {

    class RegistroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_lectura, parent, false)
        return RegistroViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
        val registro = registros[position]
        val itemView = holder.itemView

        val card = itemView.findViewById<CardView>(R.id.cardLectura)

        val textNombre = itemView.findViewById<TextView>(R.id.textNombre)
        val textFigura = itemView.findViewById<TextView>(R.id.textFigura)
        val textFecha = itemView.findViewById<TextView>(R.id.textFecha)
        val textAcerto = itemView.findViewById<TextView>(R.id.textAcerto)
        holder.itemView.apply {
            textNombre.text =  "Nombre: " + registro["nombre"].toString()
            textFigura.text = "Figura: " + registro["figura"].toString()
            textFecha.text = "Fecha: " + registro["fecha"].toString()
            textAcerto.text = "Acertó:" + registro["acerto"].toString()
            if (registro["acerto"] == "Si") {
                textAcerto.text = "Acertó: Si"
                card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.btn_success
                    )
                ) // Cambia el color de fondo a verde
            } else if (registro["acerto"] == "No") {
                textAcerto.text = "Acertó: No"
                card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.btn_warning
                    )
                ) // Cambia el color de fondo a rojo
            }
        }


    }

    override fun getItemCount(): Int {
        return registros.size
    }
}