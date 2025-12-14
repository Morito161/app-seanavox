package com.example.seavox

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AbecedarioAdapter(
    private val itemList: List<AbecedarioItem>,
    private val onSpeakClicked: (String) -> Unit
) : RecyclerView.Adapter<AbecedarioAdapter.AbecedarioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbecedarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_abecedario, parent, false)
        return AbecedarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: AbecedarioViewHolder, position: Int) {
        val currentItem = itemList[position]

        // Usar Glide para cargar la imagen de forma eficiente
        Glide.with(holder.itemView.context)
            .load(currentItem.imageResId)
            .into(holder.imageView)

        holder.titleView.text = currentItem.title
        holder.descriptionView.text = currentItem.description

        // Acción para el botón de hablar
        holder.speakButton.setOnClickListener {
            val textToSpeak = "${currentItem.title}. ${currentItem.description}"
            onSpeakClicked(textToSpeak)
        }

        // Acción para el clic en toda la fila
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetalleLetraActivity::class.java).apply {
                putExtra("EXTRA_LETRA_ITEM", currentItem)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class AbecedarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewItem)
        val titleView: TextView = itemView.findViewById(R.id.textViewTitle)
        val descriptionView: TextView = itemView.findViewById(R.id.textViewDescription)
        val speakButton: ImageButton = itemView.findViewById(R.id.buttonSpeak)
    }
}
