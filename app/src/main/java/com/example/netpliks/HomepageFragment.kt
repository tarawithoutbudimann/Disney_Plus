package com.example.netpliks

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

        // Using Handler and postDelayed for periodic task instead of Timer
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                viewPager.currentItem = (viewPager.currentItem + 1) % images.size
                handler.postDelayed(this, 3000)
            }
        }, 2000)

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

}
