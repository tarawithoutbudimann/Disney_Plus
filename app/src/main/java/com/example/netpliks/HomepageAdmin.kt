package com.example.netpliks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netpliks.databinding.ActivityHomepageAdminBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomepageAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityHomepageAdminBinding
    private lateinit var itemAdapter: MovieAdapt
    private lateinit var itemList: ArrayList<Film>
    private lateinit var recyclerViewItem: RecyclerView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewItem = binding.taskRV
        recyclerViewItem.setHasFixedSize(true)
        recyclerViewItem.layoutManager = LinearLayoutManager(this)

        itemList = arrayListOf()
        itemAdapter = MovieAdapt(itemList)
        recyclerViewItem.adapter = itemAdapter

        // Assuming you have a button named addButton in your layout
        binding.addbtn.setOnClickListener {
            // Create an Intent to navigate to AddMovieActivity
            val intent = Intent(this, tambahFilm::class.java)

            // Start the AddMovieActivity
            startActivity(intent)
        }

        // Mengambil data dari Firebase Firestore
        database = FirebaseDatabase.getInstance().getReference("movies")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Bersihkan data sebelum menambahkan yang baru
                itemList.clear()

                // Ambil data dari snapshot dan tambahkan ke itemList
                for (dataSnapshot in snapshot.children) {
                    val movie = dataSnapshot.getValue(Film::class.java)
                    movie?.let {
                        itemList.add(it)
                    }
                }

                // Perbarui RecyclerView
                itemAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Toast.makeText(this@HomepageAdmin, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

