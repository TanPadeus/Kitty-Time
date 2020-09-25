package com.example.kittytime.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kittytime.interfaces.CatService
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

class CatViewModel(context: Context) {
    private val _retrofit: Retrofit
    private val _baseURL: String = "https://api.thecatapi.com/v1/images/"

    private val _connectionWatcher: ConnectionWatcher = ConnectionWatcher.getInstance()
    val isInternetAvailable: LiveData<Boolean> = _connectionWatcher.isInternetAvailable

    private val _receivedURL = MutableLiveData<String>()
    val receivedURL: LiveData<String> = _receivedURL

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        _retrofit = Retrofit.Builder()
            .baseUrl(_baseURL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerNetworkCallback(NetworkRequest.Builder().build(), _connectionWatcher)
    }

    fun requestCatImage() {
        _retrofit.create(CatService::class.java).also { service ->
            service.getCats().enqueue(object: Callback<List<Cat>> {
                override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                    Timber.d("Request successful")
                    response.body()?.let { cats ->
                        _receivedURL.postValue(cats[0].url)
                    } ?: Timber.d("Response body is null")
                }

                override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                    Timber.e("Request failure: $t")
                }
            })
        }
    }
}