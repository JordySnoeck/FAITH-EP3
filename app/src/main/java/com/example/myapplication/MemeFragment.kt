package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myapplication.databinding.FragmentMemeBinding


class MemeFragment : Fragment() {

    var memeUrl : String ?= null
    private var _binding: FragmentMemeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMemeBinding.inflate(inflater, container, false)

        loadMeme()

        binding.next.setOnClickListener { loadMeme() }
        val sharedPref = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email","default value")

        Log.d("ACTIVITY", activity.toString())

        Log.d("MEME", email.toString())
        Log.d("MEME", "kekekekek")

        Log.d("MEME", email.toString())


        if(email == null || email.toString() == "default value"){
            findNavController().navigate(R.id.homeFragment)
        }
        val view = binding.root

        return view
    }

    fun loadMeme(){

        val loading : ProgressBar = binding.progress
        loading.visibility = View.VISIBLE

        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://meme-api.herokuapp.com/gimme"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener {
                memeUrl = it.getString("url")
                val memeImage : ImageView = binding.image

                Glide.with(this).load(memeUrl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loading.visibility = View.VISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loading.visibility = View.GONE
                        return false
                    }

                }).into(memeImage)
            },
            Response.ErrorListener {

            }
        )
        queue.add(jsonObjectRequest)
    }

}