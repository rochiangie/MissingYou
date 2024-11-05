package com.example.missingyou

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    internal var count = 0
    private lateinit var countTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countTextView = findViewById(R.id.countTextView)
        val countButton: Button = findViewById(R.id.countButton)

        // Cargar el conteo guardado
        loadCount()

        // Configurar botón para incrementar el conteo
        countButton.setOnClickListener {
            try {
                incrementCount()
            } catch (e: Exception) {
                showError(e.message)
            }
        }

        // Verificar si se necesita reiniciar el conteo
        resetCountIfNewDay()
    }

    internal fun incrementCount() {  // Changed to internal for testing access
        count++
        runOnUiThread {  // Asegúrate de actualizar en el hilo principal
            countTextView.text = "Conteo: $count"
        }
        saveCount()
    }

    internal fun saveCount() {  // Changed to internal for testing access
        val sharedPreferences = getSharedPreferences("CountPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("count", count)
            putString("lastDate", getCurrentDate())
            apply()
        }
    }

    internal fun loadCount() {  // Changed to internal for testing access
        val sharedPreferences = getSharedPreferences("CountPrefs", Context.MODE_PRIVATE)
        count = sharedPreferences.getInt("count", 0)
        runOnUiThread {  // Asegúrate de actualizar en el hilo principal
            countTextView.text = "Conteo: $count"
        }
    }

    internal fun resetCountIfNewDay() {  // Changed to internal for testing access
        val sharedPreferences = getSharedPreferences("CountPrefs", Context.MODE_PRIVATE)
        val lastDate = sharedPreferences.getString("lastDate", "")

        if (lastDate != getCurrentDate()) {
            count = 0
            runOnUiThread {  // Asegúrate de actualizar en el hilo principal
                countTextView.text = "Conteo: $count"
            }
            saveCount() // Guardar el conteo reseteado y la fecha actual
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun showError(message: String?) {
        runOnUiThread {  // Asegúrate de actualizar en el hilo principal
            countTextView.text = message ?: "Error desconocido"
        }
    }
}
