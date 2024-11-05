package com.example.missingyou

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var count = 0
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

    private fun incrementCount() {
        count++
        countTextView.text = "Conteo: $count"
        saveCount()
    }

    private fun saveCount() {
        val sharedPreferences = getSharedPreferences("CountPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("count", count)
            putString("lastDate", getCurrentDate())
            apply()
        }
    }

    private fun loadCount() {
        val sharedPreferences = getSharedPreferences("CountPrefs", Context.MODE_PRIVATE)
        count = sharedPreferences.getInt("count", 0)
        countTextView.text = "Conteo: $count"
    }

    private fun resetCountIfNewDay() {
        val sharedPreferences = getSharedPreferences("CountPrefs", Context.MODE_PRIVATE)
        val lastDate = sharedPreferences.getString("lastDate", "")

        if (lastDate != getCurrentDate()) {
            count = 0
            countTextView.text = "Conteo: $count"
            saveCount() // Guardar el conteo reseteado y la fecha actual
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun showError(message: String?) {
        countTextView.text = message ?: "Error desconocido"
    }
}
