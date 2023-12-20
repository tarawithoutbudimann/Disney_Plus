package com.example.netpliks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.netpliks.databinding.BottomnavBinding

class BottomNav : AppCompatActivity() {
    private lateinit var binding: BottomnavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menghubungkan layout activity_main.xml dengan kelas MainActivity
        binding = BottomnavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menampilkan fragment Homepage pada awal aplikasi
        replaceFragment(HomepageFragment())

        // Menetapkan pemantau (listener) untuk item yang dipilih pada bottom navigation
        binding.bottomnav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.Homepage -> replaceFragment(HomepageFragment()) // Jika memilih item dengan ID 'all', ganti fragment dengan AllMovie
                R.id.Bookmarkk -> replaceFragment(Bookmark()) // Jika memilih item dengan ID 'detailss', ganti fragment dengan Details
                R.id.Profile -> replaceFragment(profile()) // Jika memilih item dengan ID 'paymentt', ganti fragment dengan Payment
                else ->{} // Jika memilih item lain, tidak lakukan apa-apa
            }
            true // Mengembalikan nilai 'true' untuk menandakan bahwa pemilihan item telah ditangani dengan benar
        }
    }

    // Fungsi untuk mengganti fragment yang ditampilkan
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout, fragment) // Ganti fragment di dalam framelayout dengan fragment yang baru
        fragmentTransaction.commit() // Terapkan perubahan fragment
    }
}
