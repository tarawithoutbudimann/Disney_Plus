package com.example.netpliks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.netpliks.databinding.FragmentHomepageBinding
import java.util.Timer
import java.util.TimerTask

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

        val timer = Timer()
        timer.scheduleAtFixedRate(ImageSliderTimer(images.size), 2000, 3000)

        // Mendapatkan nama pengguna dari Arguments
        val username = arguments?.getString(FirstFragment.EXTRA_NAME)

        // Menampilkan pesan selamat datang
        username?.let {
            binding.welcomeMessage.text = "Hello, $username!"
        }

        binding.selengkapnya.setOnClickListener {
            // Menggunakan requireActivity() untuk mendapatkan Activity yang terkait dengan Fragment
            val intent = Intent(requireContext(), AllMovie::class.java)
            startActivity(intent)
        }
    }

    private inner class ImageSliderTimer(val numPages: Int) : TimerTask() {
        override fun run() {
            requireActivity().runOnUiThread {
                viewPager.currentItem = (viewPager.currentItem + 1) % numPages
            }
        }
    }
}
