package com.example.netpliks

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Film1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film1)

        // Mengatur onClickListener untuk ImageView dengan id backtoall
        findViewById<ImageView>(R.id.backtoall).setOnClickListener {
            // Membuat Intent untuk berpindah ke AllMovieActivity
            val intent = Intent(this, AllMovie::class.java)

            // Memulai aktivitas baru
            startActivity(intent)
        }
    }
}

