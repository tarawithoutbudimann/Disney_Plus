package com.example.netpliks

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.netpliks.databinding.FragmentSecondBinding
import com.google.firebase.firestore.FirebaseFirestore

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollectionsRef = firestore.collection("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            registerbutton.setOnClickListener {
                val name = unnamef.text.toString()
                val email = emailregist.text.toString()
                val phone = phone.text.toString()
                val password = password.text.toString()
                val newuser = Users(name= name, email = email, phone = phone, password = password)
                addUsers(newuser)
                val intentToSecondFragment =
                    Intent(requireContext(), BottomNav::class.java)
                startActivity(intentToSecondFragment)
            }
            login10.setOnClickListener {
                val resultIntent =
                    Intent(requireContext(), FirstFragment::class.java)
                startActivity(resultIntent)
            }
        }
    }
    private fun addUsers(users: Users){
        usersCollectionsRef.add(users)
            .addOnSuccessListener { documentReference ->
                // You don't need to set the data again, as it is already added with the document reference
                val createUsersId = documentReference.id
                // If you want to update the user ID in the Users object, you can do it here
                users.id = createUsersId
                documentReference.set(users)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding document", e)
            }
    }
}