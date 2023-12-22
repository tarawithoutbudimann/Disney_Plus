package com.example.netpliks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netpliks.databinding.ActivityHomepageAdminBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
class HomepageAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityHomepageAdminBinding
    private lateinit var itemList: ArrayList<Film>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomepageAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.taskRV.layoutManager = LinearLayoutManager(this)
        itemList = arrayListOf()

        binding.addbtn.setOnClickListener {
            val intent = Intent(this, tambahFilm::class.java)
            startActivity(intent)
        }

        binding.sortImg.setOnClickListener {
            logoutAndNavigateToLogin()
        }

        val db = FirebaseFirestore.getInstance()
        val query: Query = db.collection("movies")
        query.addSnapshotListener { snapshots, e ->
            if (e != null) {
                Toast.makeText(this@HomepageAdmin, "Error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@addSnapshotListener
            }

            if (snapshots != null) {
                itemList.clear()

                for (document in snapshots) {
                    val movie = document.toObject(Film::class.java)
                    Log.e("r", movie.toString())
                    itemList.add(movie)
                }

                val itemAdapter = FilmAdapt(itemList)
                binding.taskRV.adapter = itemAdapter
                itemAdapter.notifyDataSetChanged()
            }
        }
    }

    // Fungsi untuk logout dan kembali ke halaman login (FirstFragment)
    private fun logoutAndNavigateToLogin() {
        // Hapus informasi login dari SharedPreferences
        val sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isLogIn", false).apply()

        // Kembali ke halaman login
        val intent = Intent(this, FirstFragment::class.java)
        startActivity(intent)
        finish() // Selesai activity saat ini
    }
}

//package com.example.netpliks
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.netpliks.databinding.ActivityHomepageAdminBinding
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter
//import com.firebase.ui.firestore.FirestoreRecyclerOptions
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.Query
//
//class HomepageAdmin : AppCompatActivity() {
//    private lateinit var binding: ActivityHomepageAdminBinding
////    private lateinit var itemAdapter: FilmAdapt
//    private lateinit var itemList: ArrayList<Film>
////    private lateinit var recyclerViewItem: RecyclerView
//    private lateinit var database: DatabaseReference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityHomepageAdminBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.taskRV.layoutManager= LinearLayoutManager(this)
////        recyclerViewItem.setHasFixedSize(true)
////        recyclerViewItem.layoutManager
//
//        itemList = arrayListOf()
//
//
//        // Assuming you have a button named addButton in your layout
//        binding.addbtn.setOnClickListener {
//            // Create an Intent to navigate to AddMovieActivity
//            val intent = Intent(this, tambahFilm::class.java)
//
//            // Start the AddMovieActivity
//            startActivity(intent)
//        }
//
//        // Mengambil data dari Firebase Firestore
//        val db = FirebaseFirestore.getInstance()
//        val query: Query = db.collection("movies")
//        query.addSnapshotListener { snapshots, e ->
//            if (e != null) {
//                Toast.makeText(this@HomepageAdmin, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//                return@addSnapshotListener
//            }
//
//            if (snapshots != null) {
//                // Bersihkan data sebelum menambahkan yang baru
//                itemList.clear()
//
//                // Ambil data dari snapshot dan tambahkan ke itemList
//                for (document in snapshots) {
//                    val movie = document.toObject(Film::class.java)
//                    Log.e("r",movie.toString())
//                    itemList.add(movie)
//                }
//
//                val itemAdapter = FilmAdapt(itemList)
//                binding.taskRV.adapter = itemAdapter
//                // Perbarui RecyclerView
//                itemAdapter.notifyDataSetChanged()
//            }
//        }
//    }
//
//    private fun getFirestoreRecyclerOptions(): FirestoreRecyclerOptions<Film> {
//        val query: Query = FirebaseFirestore.getInstance().collection("movies")
//        val options: FirestoreRecyclerOptions<Film> = FirestoreRecyclerOptions.Builder<Film>()
//            .setQuery(query, Film::class.java)
//            .build()
//
//        return options
//    }
//
//}
//
//
