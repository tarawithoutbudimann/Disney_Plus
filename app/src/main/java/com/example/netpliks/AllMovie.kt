package com.example.netpliks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.netpliks.databinding.ActivityAllMovieBinding

class AllMovie : AppCompatActivity() {
    private lateinit var binding: ActivityAllMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val film1 = findViewById<CardView>(R.id.film1)

        film1.setOnClickListener {
            val intent = Intent(this, Film1::class.java)
            startActivity(intent)
        }

        binding.back.setOnClickListener{
            startActivity(Intent(this@AllMovie, BottomNav::class.java))
        }

//        val username = intent.getStringExtra(FirstFragment.EXTRA_NAME)
//
//        username?.let {
//            val welcomeText = "Film Bioskop - Hello, $username!"
//            binding.filmbioskop.text = welcomeText
//        }
    }
}
