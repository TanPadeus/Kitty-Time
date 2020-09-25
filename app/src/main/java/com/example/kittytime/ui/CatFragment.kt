package com.example.kittytime.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.kittytime.R
import com.example.kittytime.application.KittyApp
import com.example.kittytime.databinding.FragmentCatBinding
import com.example.kittytime.viewmodels.CatViewModel
import timber.log.Timber
import javax.inject.Inject

class CatFragment: Fragment() {
    private lateinit var binding: FragmentCatBinding

    @Inject
    lateinit var catViewModel: CatViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatBinding.inflate(inflater, container, false)
        observeConnectionStatus()
        observeURL()

        binding.requestCatButton.setOnClickListener {
            catViewModel.requestCatImage()
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as KittyApp).appComponent.inject(this)
    }

    private fun observeConnectionStatus() {catViewModel.isInternetAvailable.observe(viewLifecycleOwner) {
            if (!it) showConnectionLostDialog()
        }
    }

    private fun observeURL() {
        catViewModel.receivedURL.observe(viewLifecycleOwner) {
            loadImage(binding.catImage, it)
        }
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

    private fun loadImage(view: ImageView, url: String?) {
        url?.let {
            val uri = it.toUri().buildUpon().scheme("https").build()
            Glide.with(view.context).load(uri).into(view)
        } ?: Timber.d("Missing url")
    }
}