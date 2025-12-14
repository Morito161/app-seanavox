package com.example.seavox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al botón del XML
        val btnInicio: Button = findViewById(R.id.btnInicio)

        // Cuando se presiona "Inicio", abrir la pantalla de cámara
        btnInicio.setOnClickListener {
            val intent = Intent(this, CamaraActivity::class.java)
            startActivity(intent)
        }
    }
}
