package com.example.seavox

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.seavox.databinding.ActivityDetalleLetraBinding

class DetalleLetraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleLetraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleLetraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el objeto AbecedarioItem
        val letraItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_LETRA_ITEM", AbecedarioItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<AbecedarioItem>("EXTRA_LETRA_ITEM")
        }

        // Mostrar los datos en la vista
        letraItem?.let { item ->
            Glide.with(this)
                .load(item.imageResId)
                .into(binding.imageViewDetail)
            
            binding.textViewTitleDetail.text = item.title
            binding.textViewDescriptionDetail.text = item.description
        }
    }
}
