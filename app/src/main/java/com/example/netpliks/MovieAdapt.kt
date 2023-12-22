package com.example.netpliks

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class MovieAdapt(private val movieList: ArrayList<Film>) : RecyclerView.Adapter<MovieAdapt.FilmAdminViewHolder>() {

    inner class FilmAdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.titlef)
        private val director: TextView = itemView.findViewById(R.id.directorf)
        private val writer: TextView = itemView.findViewById(R.id.writerf)
        private val rating: TextView = itemView.findViewById(R.id.ratingf)
        private val sinopsis: TextView = itemView.findViewById(R.id.sinopf)
        private val ivImage: ImageView = itemView.findViewById(R.id.gambarrr)

        fun bind(movie: Film) {
            title.text = movie.title
            director.text = movie.director
            writer.text = movie.writer
            rating.text = movie.rating
            sinopsis.text = movie.sinopsis
            Glide.with(itemView)
                .load(movie.image)
                .error(R.drawable.ic_launcher_background)
                .into(ivImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmAdminViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.listmovie, parent, false)
        return FilmAdminViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FilmAdminViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, AdminEdit::class.java).apply {
                putExtra("judul", movie.title)
                putExtra("writer", movie.writer)
                putExtra("rating", movie.rating)
                putExtra("director", movie.director)
                putExtra("sinopsis", movie.sinopsis)
                putExtra("image", movie.image)
                // Tambahkan data lain yang ingin kamu kirim
            }
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount() = movieList.size
}
