package com.example.kittytime.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kittytime.R
import com.example.kittytime.application.KittyApp
import com.example.kittytime.databinding.FragmentStartBinding
import com.example.kittytime.viewmodels.StartViewModel
import javax.inject.Inject

class StartFragment: Fragment() {
    private lateinit var binding: FragmentStartBinding

    @Inject
    lateinit var startViewModel: StartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        observeConnectionStatus()

        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_catFragment)
        }

        return binding.root
    }

    private fun observeConnectionStatus() {
        startViewModel.isInternetAvailable.observe(viewLifecycleOwner) {
            binding.startButton.isEnabled = it
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as KittyApp).appComponent.inject(this)
    }
}