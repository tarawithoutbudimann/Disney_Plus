package com.example.netpliks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.netpliks.databinding.FragmentHomepageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HomepageFragment : Fragment() {
    private lateinit var binding: FragmentHomepageBinding
    private lateinit var viewPager: ViewPager2
    private val images = listOf(
        R.drawable.slider1,
        R.drawable.slider2,
        R.drawable.slider3,
        R.drawable.slider4,
        R.drawable.slider5
    )

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.viewPager
        val imageSliderAdapter = ImageSliderAdapter(images)
        viewPager.adapter = imageSliderAdapter

        // Set auto-scroll with a 3-second delay between slides
        viewPager.postDelayed({
            viewPager.currentItem = (viewPager.currentItem + 1) % images.size
        }, 3000)

        // Check if a user is already authenticated
        val currentUser: FirebaseUser? = auth.currentUser

        // If a user is authenticated, display a welcome message
        currentUser?.let {
            binding.welcomeMessage.text = "Hello, ${it.displayName}!"
        }

        binding.selengkapnya.setOnClickListener {
            // If a user is not authenticated, direct to login activity
            if (currentUser == null) {
                val intent = Intent(requireContext(), FirstFragment::class.java)
                startActivity(intent)
            } else {
                // If a user is authenticated, direct to AllMovie activity
                val intent = Intent(requireContext(), AllMovie::class.java)
                startActivity(intent)
            }
        }
    }
}
