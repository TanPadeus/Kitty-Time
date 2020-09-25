package com.example.kittytime.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.kittytime.R
import com.example.kittytime.databinding.FragmentStartBinding
import com.example.kittytime.utils.ConnectionWatcher

class StartFragment: Fragment() {
    private lateinit var binding: FragmentStartBinding
    private lateinit var connectionWatcher: ConnectionWatcher

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        setupConnectionWatcher()

        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_catFragment)
        }

        return binding.root
    }

    private fun setupConnectionWatcher() {
        connectionWatcher = ConnectionWatcher.getInstance()
        registerConnectionWatcher()
        observeConnectionStatus()
    }

    private fun registerConnectionWatcher() {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerNetworkCallback(NetworkRequest.Builder().build(), connectionWatcher)
    }

    private fun observeConnectionStatus() {
        connectionWatcher.isAvailable.observe(viewLifecycleOwner, Observer {
            binding.startButton.isEnabled = it
        })
    }
}