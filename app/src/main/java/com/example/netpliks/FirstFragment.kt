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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var auth : FirebaseAuth

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PASS = "extra_pass"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        with(binding) {
            login.setOnClickListener {
                val username = usernamefield.text.toString()
                val password = passwordf.text.toString()
                loginUser(username, password)
            }

            register1.setOnClickListener {
                val resultIntent = Intent(requireContext(), SecondFragment::class.java)
                startActivity(resultIntent)
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            // Jika username atau password kosong, tampilkan pesan toast.
            Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Login user menggunakan Firebase Authentication
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login berhasil
                    if (auth.currentUser!!.email == "syahadmin@gmail.com"){
                        val intentToHomepage = Intent(requireContext(), HomepageAdmin::class.java)
                        startActivity(intentToHomepage)
                    } else {
                        val intentToHomepage = Intent(requireContext(), BottomNav::class.java)
                        startActivity(intentToHomepage)
                    }

                } else {
                    // Login gagal, tampilkan pesan kesalahan
                    Toast.makeText(
                        requireContext(),
                        "Username or password doesn't match. Try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
