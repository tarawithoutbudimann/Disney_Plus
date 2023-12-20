package com.example.netpliks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.netpliks.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
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
                val intentToSecondFragment =
                    Intent(requireContext(), BottomNav::class.java)
                intentToSecondFragment.putExtra(EXTRA_NAME, emailfield.text.toString())
                intentToSecondFragment.putExtra(EXTRA_PASS, passwordf.text.toString())
                startActivity(intentToSecondFragment)
            }
            register1.setOnClickListener{
                val resultIntent =
                    Intent(requireContext(), SecondFragment::class.java)
                startActivity(resultIntent)
            }
        }
    }
}
