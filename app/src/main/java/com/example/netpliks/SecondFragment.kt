package com.example.netpliks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.netpliks.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            registerbutton.setOnClickListener {
                val intentToSecondFragment =
                    Intent(requireContext(), FirstFragment::class.java)
                startActivity(intentToSecondFragment)
            }
            login10.setOnClickListener {
                val resultIntent =
                    Intent(requireContext(), FirstFragment::class.java)
                startActivity(resultIntent)
            }
        }
    }
}