package com.example.kittytime.ui

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import com.example.kittytime.R
import com.example.kittytime.databinding.FragmentCatBinding
import com.example.kittytime.utils.ConnectionWatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatFragment: Fragment() {
    private lateinit var binding: FragmentCatBinding
    private lateinit var retrofit: Retrofit
    private val connectionWatcher = ConnectionWatcher()
    private val baseURL = "https://api.thecatapi.com/v1/images/search"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatBinding.inflate(inflater, container, false)
        registerConnectionWatcher()
        observeConnectionStatus()
        createRetrofit()
        return binding.root
    }

    private fun registerConnectionWatcher() {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerNetworkCallback(NetworkRequest.Builder().build(), connectionWatcher)
    }

    private fun observeConnectionStatus() {
        connectionWatcher.isAvailable.observe(viewLifecycleOwner, { isAvailable ->
            if (!isAvailable) showConnectionLostDialog()
        })
    }

    private fun showConnectionLostDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.fcat_alert_title_text)
            .setMessage(R.string.fcat_alert_message_text)
            .setNeutralButton(R.string.fcat_alert_button_text) { _, _ ->
                requireActivity().onBackPressed()
            }
            .show()
    }

    private fun createRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}