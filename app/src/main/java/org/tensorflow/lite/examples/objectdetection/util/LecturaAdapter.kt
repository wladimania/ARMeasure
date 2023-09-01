package org.tensorflow.lite.examples.objectdetection.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import org.tensorflow.lite.examples.objectdetection.R

class LecturaAdapter(private val registros: List<Map<String, Any>>) :
    RecyclerView.Adapter<LecturaAdapter.RegistroViewHolder>() {

    class RegistroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false)
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
            if (registro["acerto"] == "si") {
                textAcerto.text = "Acertó: Si"
                card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.colorBotonVolver
                    )
                ) // Cambia el color de fondo a verde
            } else if (registro["acerto"] == "no") {
                textAcerto.text = "Acertó: No"
                card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.colorOrange
                    )
                ) // Cambia el color de fondo a rojo
            }
        }


    }

    override fun getItemCount(): Int {
        return registros.size
    }
}