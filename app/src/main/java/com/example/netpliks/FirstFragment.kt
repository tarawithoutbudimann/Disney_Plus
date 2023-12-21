package com.example.netpliks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.netpliks.databinding.FragmentFirstBinding
import com.google.firebase.firestore.FirebaseFirestore

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollectionsRef = firestore.collection("Users")

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PASS = "extra_pass"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            login.setOnClickListener {
                val username = usernamefield.text.toString()
                val password = passwordf.text.toString()
                loginUser(username, password)
            }
            register1.setOnClickListener {
                val resultIntent =
                    Intent(requireContext(), SecondFragment::class.java)
                startActivity(resultIntent)
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            // Jika username atau password kosong, tampilkan pesan toast.
            Toast.makeText(requireContext(), "Please fill the field", Toast.LENGTH_SHORT).show()
            return
        }

//        usersCollectionsRef
//            .whereEqualTo("name", username)
//            .whereEqualTo("password", password)
//            .get()
//            .addOnSuccessListener { documents ->
//                if (!documents.isEmpty) {
//                    // User credentials are valid, navigate to the next fragment or perform desired action.
//                    val intentToSecondFragment =
//                        Intent(requireContext(), BottomNav::class.java)
//                    startActivity(intentToSecondFragment)
//                } else {
//                    // User credentials are invalid, show an error message or take appropriate action.
//                    Toast.makeText(
//                        requireContext(),
//                        "Username or password doesn't match. Try again.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//            .addOnFailureListener { exception ->
//                // Handle failures such as network errors or Firestore exceptions.
//                Log.e("Login", "Error during login", exception)
//            }
//    }
        usersCollectionsRef
            .whereEqualTo("name", username)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userDoc = documents.documents[0]
                    val userType = userDoc.getString("userType")

                    // Check if the user is admin1
                    if (username == "admin1") {
                        // If the user is admin1, direct to HomepageAdmin
                        val intentToAdminHome =
                            Intent(requireContext(), HomepageAdmin::class.java)
                        startActivity(intentToAdminHome)
                    } else {
                        // For other users, direct to Homepage
                        val intentToHomepage =
                            Intent(requireContext(), BottomNav::class.java)
                        startActivity(intentToHomepage)
                    }
                } else {
                    // User credentials are invalid, show an error message or take appropriate action.
                    Toast.makeText(
                        requireContext(),
                        "Username or password doesn't match. Try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener { exception ->
                // Handle failures such as network errors or Firestore exceptions.
                Log.e("Login", "Error during login", exception)
            }
    }
}
