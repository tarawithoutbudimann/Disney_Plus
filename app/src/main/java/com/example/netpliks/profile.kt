package com.example.netpliks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class profile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Find the logout button by its ID
        val btnLogout: Button = view.findViewById(R.id.logout)

        // Set a click listener to handle the logout action
        btnLogout.setOnClickListener {
            logout()
        }

        return view
    }

    private fun logout() {
        // Perform logout actions here, such as signing out from authentication
        // For example, redirect to the FirstFragment after logout
        val intent = Intent(requireContext(), FirstFragment::class.java)
        startActivity(intent)
        requireActivity().finish()  // Close the current activity after logout
    }
}
