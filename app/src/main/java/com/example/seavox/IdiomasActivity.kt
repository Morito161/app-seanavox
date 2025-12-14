package com.example.seavox

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class IdiomasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idiomas)

        val catEspañol = findViewById<LinearLayout>(R.id.catEspañol)
        val catIngles = findViewById<LinearLayout>(R.id.catIngles)
        val catFrances = findViewById<LinearLayout>(R.id.catFrances)

        catEspañol.setOnClickListener { cambiarIdioma("es") }
        catIngles.setOnClickListener { cambiarIdioma("en") }
        catFrances.setOnClickListener { cambiarIdioma("fr") }
    }

    private fun cambiarIdioma(codigo: String) {
        val locale = Locale(codigo)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        // Reinicia CamaraActivity para aplicar el idioma
        val intent = Intent(this, CamaraActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
