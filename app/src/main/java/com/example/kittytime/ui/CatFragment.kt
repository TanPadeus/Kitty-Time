package com.example.kittytime.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.lifecycle.observe
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
    private val connectionWatcher = ConnectionWatcher.getInstance()
    private val baseURL = "https://api.thecatapi.com/v1/images/"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatBinding.inflate(inflater, container, false)
        observeConnectionStatus()
        createRetrofit()

        binding.requestCatButton.setOnClickListener {
            requestCatImage()
        }

        return binding.root
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
                if (response.body() != null) {
                    response.body()!!.forEach { cat ->
                        loadImage(binding.catImage, cat.imageUrl)
                    }
                }
            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                Timber.e("Response failure: $t")
            }
        })
    }

    private fun logCat(cat: Cat?) {
        Timber.d("Cat ID: ${cat?.id ?: "null"}")
        Timber.d("Cat URL: ${cat?.imageUrl ?: "null"}")
        Timber.d("Cat WIDTH: ${cat?.width ?: "null"}")
        Timber.d("Cat HEIGHT: ${cat?.height ?: "null"}")
    }

    private fun loadImage(view: ImageView, url: String?) {
        url?.let {
            val uri = url.toUri().buildUpon().scheme("https").build()
            Glide.with(view.context).load(uri).into(view)
        } ?: Timber.d("Missing url")
    }
}