package com.example.seavox

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class AbecedarioActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var isTtsReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abecedario)

        // Inicializar TextToSpeech
        tts = TextToSpeech(this, this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewAbecedario)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Optimización: si el tamaño de los items no cambia, esto mejora el rendimiento.
        recyclerView.setHasFixedSize(true)

        val itemList = listOf(
            AbecedarioItem(R.drawable.letra_a, "Letra A", "Con la mano cerrada se muestran la uñas y se estira el dedo pulgar hacia un lado. La palma mira al frente."),
            AbecedarioItem(R.drawable.letra_b, "Letra B", "Los dedos indice, medio, anular, y meñique se estiran bien unidos y el pulgar se dobla hacia la palma, la cual mira al frente."),
            AbecedarioItem(R.drawable.letra_c, "Letra C", "Los dedos indice, medio, anular, y meñique se mantiene bien unidos y en posiciòn concava el pulgar tambièn se pone en esa posiciò. La palma mira a un lado."),
            AbecedarioItem(R.drawable.letra_d, "Letra D", "Los dedos medio, anular, meñique y pulgar se unen por las puntas y el dedo indice se estira. La palma mira hacia al frente."),
            AbecedarioItem(R.drawable.letra_e, "Letra E", "Se doblan los dedos completamentye y se muestran las uñas. La palma mira al frente."),
            AbecedarioItem(R.drawable.letra_f, "Letra F", "Con la mano abierta y los dedos bien unidos se dobla el indice hasta que su parte lateral toque la yema del pulgar, La palma mira a un lado."),
            AbecedarioItem(R.drawable.letra_g_volteada, "Letra G", "Se cierra la mano y los dedos indice y pulgar se estiran y unidos se extienden el dedo pulgar señalando hacia arriba. La palma mira hacia usted."),
            AbecedarioItem(R.drawable.letra_h, "Letra H", "Con la mano cerradada y los dedos indice y medio bien estirados, se extiende el dedo pulgar señalando hacia arriba. La palma mira hacia usted."),
            AbecedarioItem(R.drawable.letra_i, "Letra I", "Con la mano cerrada el dedo meñique se estira señalando hacia arriba. La palma se pone de lado."),
            AbecedarioItem(R.drawable.letra_j, "Letra J", "Con la mano cerrada el dedo meñique bien estirado señalando hacia arriba y la palma a un lado dibuja una j en el aire."),
            AbecedarioItem(R.drawable.letra_k, "Letra K", "Se cierra la mano con los dedos indice, medio y pulgar estirados. La yema del pulgar se pone entre el indice y el medio. Se mueve la muñeca hacia arriba."),
            AbecedarioItem(R.drawable.letra_l, "Letra L", "Con la mano cerrada y los dedos indice y pulgar estirados, se forma una l. la palma mira al frente."),
            AbecedarioItem(R.drawable.letra_m, "Letra M", "Con la mano cerrada, se ponen los dedos indice, medio y anular sobre el pulgar."),
            AbecedarioItem(R.drawable.letra_n, "Letra N", "Con la mano cerrada, se ponen los dedos indice y medio sobre el pulgar."),
            AbecedarioItem(R.drawable.letra_enie, "Letra Ñ", "Con la mano cerrada , se ponen los dedos indice y medio sobre el pulgar. Se mueve la muñeca a los dedos."),
            AbecedarioItem(R.drawable.letra_o, "Letra O", "Con la mano se forma una letra o. Todos los dedos se tocan por las puntas."),
            AbecedarioItem(R.drawable.letra_p, "Letra P", "Con la mano cerrada y los dedos indice, medio y pulgar estirados, se pone la yema del pulgar entre el indice y el medio."),
            AbecedarioItem(R.drawable.letra_q, "Letra Q", "con la mano cerrada se ponen los dedos indice y pulgar en posiciòn de garra. La palma mira hacia abajo y se mueve la muñeca hacia los lados."),
            AbecedarioItem(R.drawable.letra_r, "Letra R", "Con la mano cerrada se estiran y entrelazan los dedos indice y medio. La palma mira al frente."),
            AbecedarioItem(R.drawable.letra_s, "Letra S", "Con la mano cerrada se pone el pulgar sobre los dedos. La palma mira al frente."),
            AbecedarioItem(R.drawable.letra_t, "Letra T", "Con la mano cerrada el pulgar se pone entre el indice y el medio. La palma mira al frente."),
            AbecedarioItem(R.drawable.letra_u, "Letra U", "Con la mano cerrada se estiran los dedos indice y medio unidos. La palma mira al frente."),
            AbecedarioItem(R.drawable.letra_v, "Letra V", "Con la mano cerrada se estiran los dedos indice y medio separados. La palma mira al frente."),
            AbecedarioItem(R.drawable.letra_w, "Letra W", "Con la mano cerrada se estitran los dedos indice, medio y anular separados. La palma mira al frente."),
            AbecedarioItem(R.drawable.letra_x, "Letra X", "Con la mano cerrada el indice y el pulgar en posiciòn de garra y la palma dirigida a un lado,  se realiza un movimiento al frente y de regreso."),
            AbecedarioItem(R.drawable.letra_y, "Letra Y", "Con la mano cerrada se estira el meñique y el pulgar. La palma mira hacia usted."),
            AbecedarioItem(R.drawable.letra_z, "Letra Z", "Con la mano cerrada el dedo indice estirado y la palma al frente, se dibuja una letra z en el aire."),
            // Imágenes extra añadidas
            AbecedarioItem(R.drawable.no_se_letra, "Pez", "Con la mano abierta y los dedos unidos, se reliza un movimiento ondulatorio hacia adelante."),
            AbecedarioItem(R.drawable.img_8885, "Almeja", "Con las dos manos se unen las puntas de los dedos indice y pulgar formando una especie de pinza. Las palmas miran hacia usted."),
            AbecedarioItem(R.drawable.img_8886, "Bùho", "Se unen por la punta los indices y pulgares de cada mano y se colocan alrededor de los ojos como si fueran unos binoculares."),
            AbecedarioItem(R.drawable.img_8889, "Gato", "Se coloca la mano abierta en posiciòn de garra sobre la mejilla, y se recorre hacia atràs."),
            AbecedarioItem(R.drawable.img_8890, "Perro", "Los dedos indice medio y pulgar se tocan con las yemas y se realiza un movimiento de chasquido, simulando llamar a un perro."),
            AbecedarioItem(R.drawable.img_8892, "Aguila", "SE coloca el indice y el pulgar en posiciòn de garra sobre la boca, tocándola con el dorso de la mano y se hace un movimiento hacia adelante y atrás."),
            AbecedarioItem(R.drawable.img_8893, "Matrimonio", "Se unen las manos por las palmas. Luego se abre la mano, y se mueve en ondas hacia abajo, hasta unir los dedos en capullo sobre la palma de la otra mano."),
            AbecedarioItem(R.drawable.img_8895, "Familia", "Se hace una letra f sobre el dorso del antebrazo tocando con la parte lateral del meñique  y se mueve en linea recta a la muñeca."),
            AbecedarioItem(R.drawable.img_8896, "Familia", "Se hace una letra f sobre el dorso del antebrazo tocando con la parte lateral del meñique  y se mueve en linea recta a la muñeca."),
            AbecedarioItem(R.drawable.img_8897, "Madre", "Se coloca una letra m sobre el dorso de la otra mano tocándo con la punta de los dedos."),
            AbecedarioItem(R.drawable.img_8899, "Padre", "Se coloca una letra p sobre el dorso de la otra mano.")
        )

        // Pasamos la función para hablar al adaptador
        val adapter = AbecedarioAdapter(itemList) { text ->
            speakOut(text)
        }
        recyclerView.adapter = adapter
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale("es", "ES"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "El lenguaje especificado no está soportado.")
            } else {
                isTtsReady = true
            }
        } else {
            Log.e("TTS", "Falló la inicialización del motor de Texto-a-Voz.")
        }
    }

    private fun speakOut(text: String) {
        if (isTtsReady) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        } else {
            Log.e("TTS", "El motor de TTS no está listo.")
        }
    }

    override fun onDestroy() {
        // Liberar recursos del motor de TTS
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}
