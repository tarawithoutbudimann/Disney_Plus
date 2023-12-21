package com.example.netpliks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.netpliks.databinding.ActivityHomepageAdminBinding

class HomepageAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityHomepageAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Assuming you have a button named addButton in your layout
        binding.addbtn.setOnClickListener {
            // Create an Intent to navigate to AddMovieActivity
            val intent = Intent(this, AddMovie::class.java)

            // Start the AddMovieActivity
            startActivity(intent)
        }
    }
}
