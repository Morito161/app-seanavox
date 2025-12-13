package com.seavox

import android.os.Bundle
import android.widget.LinearLayout
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
            mostrarMensaje("Abecedario seleccionado")
        }
    }

    private fun mostrarMensaje(txt: String) {
        android.widget.Toast.makeText(this, txt, android.widget.Toast.LENGTH_SHORT).show()
    }
}
