package com.example.netpliks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.netpliks.databinding.FragmentSecondBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()

        // Setup Spinner for User Type
        val userTypeSpinner: Spinner = binding.userTypeSpinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.user_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            userTypeSpinner.adapter = adapter
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            registerbutton.setOnClickListener {
                val name = unnamef.text.toString()
                val email = emailregist.text.toString()
                val phone = phone.text.toString()
                val password = password.text.toString()

                if (name.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank()) {
                    showErrorMessage("Please fill all the fields")
                } else {
                    // Get selected user type from the Spinner
                    val userTypeSpinner: Spinner = binding.userTypeSpinner
                    val selectedUserType = userTypeSpinner.selectedItem.toString()

                    // Register user with Firebase Authentication
                    registerUserWithEmailPassword(email, password) { user: FirebaseUser? ->
                        if (user != null) {
                            // User registered successfully, now save additional user data to Firestore
                            val newUser = Users(name = name, email = email, phone = phone, userType = selectedUserType)
                            saveUserToFirestore(newUser)

                            // Navigate to Home
                            navigateToHome()
                        } else {
                            showErrorMessage("Error during registration. Please try again.")
                        }
                    }
                }
            }

            login10.setOnClickListener {
                val resultIntent = Intent(requireContext(), FirstFragment::class.java)
                startActivity(resultIntent)
            }
        }
    }

    private fun registerUserWithEmailPassword(email: String, password: String, callback: (user: FirebaseUser?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    callback.invoke(user)
                } else {
                    callback.invoke(null)
                }
            }
    }

    private fun saveUserToFirestore(newUser: Users) {
        firestore.collection("Users")
            .document(auth.currentUser?.uid ?: "")
            .set(newUser)
            .addOnFailureListener { e ->
                showErrorMessage("Error saving user data to Firestore. Please try again.")
            }
    }

    private fun navigateToHome() {
        val intentToHome = Intent(requireContext(), BottomNav::class.java)
        startActivity(intentToHome)
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
