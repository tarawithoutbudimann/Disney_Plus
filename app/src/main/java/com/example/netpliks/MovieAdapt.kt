package com.example.netpliks

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot

class MovieAdapt(options: FirestoreRecyclerOptions<Film>) :
    FirestoreRecyclerAdapter<Film, MovieAdapt.FilmAdminViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmAdminViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return FilmAdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmAdminViewHolder, position: Int, model: Film) {
        holder.bind(model)
        holder.itemView.setOnClickListener {
            // Handle item click here
            val documentSnapshot: DocumentSnapshot = snapshots.getSnapshot(holder.adapterPosition)
            val movieId: String = documentSnapshot.id

            val intent = Intent(holder.itemView.context, AdminEdit::class.java)
            intent.putExtra("title", model.judul)
            intent.putExtra("director", model.director)
            intent.putExtra("writer", model.writer)
            intent.putExtra("rating", model.rating)
            intent.putExtra("sinopsis", model.sinopsis)
            intent.putExtra("image", model.imageUrl)
            intent.putExtra("movieId", movieId)

            holder.itemView.context.startActivity(intent)
        }
    }

    inner class FilmAdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.titlef)
        private val director: TextView = itemView.findViewById(R.id.directorf)
        private val writer: TextView = itemView.findViewById(R.id.writerf)
        private val rating: TextView = itemView.findViewById(R.id.ratingf)
        private val sinopsis: TextView = itemView.findViewById(R.id.sinopf)
        private val ivImage: ImageView = itemView.findViewById(R.id.gambarrr)

        fun bind(movie: Film) {
            title.text = movie.judul
            director.text = movie.director
            writer.text = movie.writer
            rating.text = movie.rating
            sinopsis.text = movie.sinopsis
            Glide.with(itemView)
                .load(movie.imageUrl)
                .error(R.drawable.ic_launcher_background)
                .into(ivImage)
        }
    }
}
