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
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.kittytime.R
import com.example.kittytime.databinding.FragmentCatBinding
import com.example.kittytime.interfaces.RetrofitService
import com.example.kittytime.models.Cat
import com.example.kittytime.utils.ConnectionWatcher
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

class CatFragment: Fragment() {
    private lateinit var binding: FragmentCatBinding
    private lateinit var retrofit: Retrofit
    private lateinit var connectionWatcher: ConnectionWatcher
    private val baseURL = "https://api.thecatapi.com/v1/images/"
    private val retrievedUrl = MutableLiveData<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatBinding.inflate(inflater, container, false)
        setupConnectionWatcher()
        observeUrl()
        createRetrofit()

        binding.requestCatButton.setOnClickListener {
            requestCatImage()
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
            if (!it) showConnectionLostDialog()
        })
    }

    private fun observeUrl() {
        retrievedUrl.observe(viewLifecycleOwner, Observer {
            loadImage(binding.catImage, it)
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
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseURL)
            .build()
    }

    private fun requestCatImage() {
        val service: RetrofitService = retrofit.create(RetrofitService::class.java)
        val call = service.getCat()
        call.enqueue(object: Callback<List<Cat>> {
            override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                Timber.d("Response success: retrieved ${response.body()?.size ?: "null"} cat(s).")
                if (response.body() != null) retrievedUrl.postValue(response.body()!![0].url)
            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                Timber.e("Response failure: $t")
            }
        })
    }

    private fun loadImage(view: ImageView, url: String?) {
        url?.let {
            val uri = it.toUri().buildUpon().scheme("https").build()
            Glide.with(view.context).load(uri).into(view)
        } ?: Timber.d("Missing url")
    }
}