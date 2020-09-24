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
import com.example.kittytime.interfaces.RetrofitInterface
import com.example.kittytime.models.Cat
import com.example.kittytime.utils.ConnectionWatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class CatFragment: Fragment() {
    private lateinit var binding: FragmentCatBinding
    private lateinit var retrofit: Retrofit
    private val connectionWatcher = ConnectionWatcher()
    private val baseURL = "https://api.thecatapi.com/v1/images/search/"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatBinding.inflate(inflater, container, false)
        registerConnectionWatcher()
        observeConnectionStatus()
        createRetrofit()
        initializeApiClient()
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

    private fun initializeApiClient() {
        val service = retrofit.create(RetrofitInterface::class.java)
        val call = service.getCat()
        call.enqueue(object: Callback<Cat> {
            override fun onResponse(call: Call<Cat>, response: Response<Cat>) {
                Timber.d("Response success: ${response.body()}")
            }

            override fun onFailure(call: Call<Cat>, t: Throwable) {
                Timber.e("Response failure: $t")
            }
        })
    }
}