package com.example.workout

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.startButton)
        btn.setOnClickListener {
            val intent = Intent(this@MainActivity, chooseProgram::class.java)
            intent.putExtra("Identifier1", 50);
            startActivity(intent)
        }

    }
}
