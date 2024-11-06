package com.example.missingyou

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    internal var count = 0
    private lateinit var countTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializamos SharedPreferences
        sharedPreferences = getSharedPreferences("CountPrefs", Context.MODE_PRIVATE)

        countTextView = findViewById(R.id.countTextView)
        val countButton: Button = findViewById(R.id.countButton)

        // Cargar el conteo guardado
        try {
            loadCount()
        } catch (e: Exception) {
            showError("Error al cargar el conteo: ${e.message}")
        }

        // Configurar botón para incrementar el conteo
        countButton.setOnClickListener {
            try {
                incrementCount()
            } catch (e: Exception) {
                showError("Error al incrementar el conteo: ${e.message}")
            }
        }

        // Verificar si se necesita reiniciar el conteo
        try {
            resetCountIfNewDay()
        } catch (e: Exception) {
            showError("Error al verificar el nuevo día: ${e.message}")
        }
    }

    internal fun incrementCount() {
        try {
            // Incrementamos el conteo
            count++

            // Actualizamos la interfaz en el hilo principal
            runOnUiThread {
                countTextView.text = "Conteo: $count"
            }

            // Guardamos el nuevo conteo
            saveCount()

        } catch (e: Exception) {
            throw Exception("Error en incrementCount: ${e.message}")
        }
    }

    internal fun saveCount() {
        try {
            // Guardamos el conteo y la fecha actual en SharedPreferences
            with(sharedPreferences.edit()) {
                putInt("count", count)
                putString("lastDate", getCurrentDate())
                apply()
            }
        } catch (e: Exception) {
            throw Exception("Error al guardar el conteo: ${e.message}")
        }
    }

    internal fun loadCount() {
        try {
            // Cargar el conteo guardado desde SharedPreferences
            count = sharedPreferences.getInt("count", 0)

            // Actualizamos la interfaz con el valor cargado
            runOnUiThread {
                countTextView.text = "Conteo: $count"
            }

        } catch (e: Exception) {
            throw Exception("Error al cargar el conteo desde SharedPreferences: ${e.message}")
        }
    }

    internal fun resetCountIfNewDay() {
        try {
            val lastDate = sharedPreferences.getString("lastDate", "")
            val currentDate = getCurrentDate()

            // Comprobamos si es un nuevo día
            if (lastDate != currentDate) {
                // Reiniciamos el conteo si es un nuevo día
                count = 0

                // Actualizamos la interfaz
                runOnUiThread {
                    countTextView.text = "Conteo: $count"
                }

                // Guardamos la fecha actual y el conteo
                saveCount()
            }

        } catch (e: Exception) {
            throw Exception("Error al reiniciar el conteo por nuevo día: ${e.message}")
        }
    }

    internal fun getCurrentDate(): String {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateFormat.format(Date())
        } catch (e: Exception) {
            throw Exception("Error al obtener la fecha actual: ${e.message}")
        }
    }

    internal fun showError(message: String?) {
        // Muestra un mensaje de error en la interfaz
        runOnUiThread {
            countTextView.text = "Conteo: Error: ${message ?: "desconocido"}"
            // Añadimos un log para verificar si se llama correctamente
            Log.e("MainActivity", "Error: ${message ?: "desconocido"}")  // Verifica si este log aparece
        }
    }


}
