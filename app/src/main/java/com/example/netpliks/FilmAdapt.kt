package com.example.netpliks

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.netpliks.databinding.ListmovieBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class FilmAdapt(private val Filmlist: ArrayList<Film>) :
    RecyclerView.Adapter<FilmAdapt.itemMovieViewHolder>() {

    private lateinit var database: DatabaseReference

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmAdapt.itemMovieViewHolder {
        val binding =
            ListmovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return itemMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmAdapt.itemMovieViewHolder, position: Int) {
        val currentItem = Filmlist[position]

        // Set data using the bind method
        holder.bind(currentItem)

        // Set click listener for the edit button
        holder.binding.editbutton.setOnClickListener {
            // Get the context from the holder
            val context: Context = holder.itemView.context

            // Create an Intent to navigate to EditActivity
            val intent = Intent(context, AdminEdit::class.java)

            // Pass the movie details to EditActivity using Intent extras

            intent.putExtra("title", currentItem.judul)
            intent.putExtra("director", currentItem.director)
            intent.putExtra("writer", currentItem.writer)
            intent.putExtra("rating", currentItem.rating)
            intent.putExtra("sinopsis", currentItem.sinopsis)
            intent.putExtra("imgId", currentItem.imageUrl)

            // Start the EditActivity
            context.startActivity(intent)
        }

        holder.binding.delbutton.setOnClickListener{
            val firestore = FirebaseFirestore.getInstance()
            val budgetCollectionRef = firestore.collection("movies")

            budgetCollectionRef.document(currentItem.judul).delete()
                .addOnFailureListener {
                    Log.d("MainActivity", "Error deleting budget: ", it)
                }

        }
    }

    override fun getItemCount(): Int {
        return Filmlist.size
    }

    inner class itemMovieViewHolder(val binding: ListmovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Film) {
            with(binding) {
                // Set judul
                titlef.text = data.judul
                directorf.text = data.director
                writerf.text = data.writer
                ratingf.text = data.rating
                sinopf.text = data.sinopsis
                Glide.with(itemView.context).load(data.imageUrl).into(binding.gambarrr)
            }
        }
    }
}

//package com.example.netpliks
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.netpliks.databinding.ListmovieBinding
//
//class FilmAdapt (private val Filmlist:ArrayList<Film>): RecyclerView.Adapter<FilmAdapt.itemMovieViewHolder>(){
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): FilmAdapt.itemMovieViewHolder {
//        val binding =
//            ListmovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return itemMovieViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: FilmAdapt.itemMovieViewHolder, position: Int) {
//        holder.bind(Filmlist[position])
//    }
//
//    override fun getItemCount(): Int {
//        return Filmlist.size
//    }
//
//    inner class itemMovieViewHolder(private val binding: ListmovieBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(data: Film) {
//            with(binding) {
//                // Set judul
//                titlef.text = data.judul
//                directorf.text = data.director
//                writerf.text = data.writer
//                ratingf.text = data.rating
//                sinopf.text = data.sinopsis
//                Glide.with(itemView.context).load(data.imageUrl).into(binding.gambarrr)
//            }
//        }
//
//        }
//}