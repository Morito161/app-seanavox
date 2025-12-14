package com.example.seavox

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DiccionarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diccionario)

        val catFamilia = findViewById<LinearLayout>(R.id.catFamilia)
        val catComida = findViewById<LinearLayout>(R.id.catComida)
        val catCosas  = findViewById<LinearLayout>(R.id.catCosas)
        val catAbecedario = findViewById<LinearLayout>(R.id.catAbecedario)

        catFamilia.setOnClickListener {
            mostrarMensaje("Familia seleccionada")
        }

        catComida.setOnClickListener {
            mostrarMensaje("Comida seleccionada")
        }

        catCosas.setOnClickListener {
            mostrarMensaje("Cosas seleccionada")
        }

        catAbecedario.setOnClickListener {
            val intent = Intent(this, AbecedarioActivity::class.java)
            startActivity(intent)
        }
    }

    private fun mostrarMensaje(txt: String) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
    }
}
