package com.example.missingyou

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countTextView: TextView = findViewById(R.id.countTextView)
        val countButton: Button = findViewById(R.id.countButton)

        countButton.setOnClickListener {
            count++
            countTextView.text = "Conteo: $count"
        }
    }
}
