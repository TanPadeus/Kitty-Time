package com.example.kittytime.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kittytime.R
import com.example.kittytime.databinding.FragmentStartBinding

class StartFragment: Fragment() {
    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)

        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_catFragment)
        }

        return binding.root
    }
}