package com.example.netpliks

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayoutStates.TAG
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.netpliks.databinding.EditmovieBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class AdminEdit: AppCompatActivity() {
    private lateinit var binding: EditmovieBinding
    private lateinit var database: DatabaseReference
    private lateinit var storageReference: StorageReference
    private var imageUri: Uri ?= null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                imageUri = uri
                binding.imgedit.setImageURI(uri)
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditmovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.uploadimage2.setOnClickListener {
            getContent.launch("image/*")
        }

        val title = binding.titlefield1
        val director = binding.directorfield1
        val writer = binding.writerfield1
        val rating = binding.ratingfield1
        val sinopsis = binding.sinopsisfield1
        val originalImageUrl = intent.getStringExtra("imgId")

        Glide.with(this)
            .load(originalImageUrl)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.imgedit)

        title.setText(intent.getStringExtra("title"))
        director.setText(intent.getStringExtra("director"))
        writer.setText(intent.getStringExtra("writer"))
        rating.setText(intent.getStringExtra("rating"))
        sinopsis.setText(intent.getStringExtra("sinopsis"))

        binding.editmovie.setOnClickListener {
            uploadData(imageUri)
        }

        // Add OnClickListener to the back button
        binding.closeImg1.setOnClickListener {
            val intent = Intent(this, HomepageAdmin::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun uploadData(imageUri: Uri? = null) {
        val updatedTitle = binding.titlefield1.text.toString()
        val updatedDirector = binding.directorfield1.text.toString()
        val updatedWriter = binding.writerfield1.text.toString()
        val updatedRating = binding.ratingfield1.text.toString()
        val updatedSinopsis = binding.sinopsisfield1.text.toString()

        database = FirebaseDatabase.getInstance().getReference("Film")

        if (imageUri != null) {


            // Generate a unique ID for the image


            // Upload image to Firebase Storage with the generated ID
            // Assuming you have a DatabaseReference reference initialized
            val newItemRef = database.push() // Generates a unique key
            val imageId = newItemRef.key // Retrieve the generated key
            Log.d("tarates", "$imageId")
            Log.d("tarates1", "${intent.getStringExtra("imgId")}")

            storageReference = FirebaseStorage.getInstance().getReference("images/$imageId")

            val uploadTask: UploadTask = storageReference.putFile(imageUri)

            uploadTask.addOnSuccessListener {
                // Image uploaded successfully, now get the download URL
                storageReference.downloadUrl.addOnSuccessListener { imageUrl ->
                    val item = Film(
                        updatedTitle,
                        updatedDirector,
                        updatedWriter,
                        updatedRating,
                        updatedSinopsis,
                        imageUrl.toString()
                    )

                    updataData(item, updatedTitle)

                            // Handle completion, e.g., show a success message
                            Toast.makeText(this, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show()

                            // Start HomeAdminActivity after successful update
                            val intent = Intent(this, HomepageAdmin::class.java)
                            startActivity(intent)

                            // Finish current activity
                            finish()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Image Upload Failed!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.d("testUri", "${imageUri}")
            Log.d("testUri", "${imageUri}")
            Log.d("testUri", "${imageUri}")

            // If no new image is selected, update the data without uploading a new image
            val imageId = Uri.parse(intent.getStringExtra("imgId")).lastPathSegment?.removePrefix("images/")

            val updatedList = mapOf(
                "title" to updatedTitle,
                "director" to updatedDirector,
                "writer" to updatedWriter,
                "rating" to updatedRating,
                "sinopsis" to updatedSinopsis
            )

            // Update the data with the new title
            database.child(imageId!!).updateChildren(updatedList)
                .addOnCompleteListener {
                    // Handle completion, e.g., show a success message
                    Toast.makeText(this, "Data Updated Successfully", Toast.LENGTH_SHORT).show()

                    // Start HomeAdminActivity after successful update
                    val intent = Intent(this, HomepageAdmin::class.java)
                    startActivity(intent)

                    // Finish current activity
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Updating Data Failed!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun updataData(item: Film, title: String) {
        val db = FirebaseFirestore.getInstance()

        // Step 1: Retrieve the document(s) that match the condition
        db.collection("movies")
            .whereEqualTo("judul", title)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        // Step 2: Update the retrieved document(s) with the new data
                        val docRef = db.collection("movies").document(document.id)

                        // You can update specific fields using a Map
                        val updates = mapOf(
                            "judul" to item.judul,
                            "director" to item.director,
                            "writer" to item.writer,
                            "rating" to item.rating,
                            "sinopsis" to item.sinopsis,
                            "imageUrl" to item.imageUrl,
                        )

                        docRef.update(updates)
                            .addOnSuccessListener {
                                // Step 3: Commit the changes back to Firestore
                                Log.d(TAG, "DocumentSnapshot successfully updated!")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error updating document", e)
                            }
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }

}