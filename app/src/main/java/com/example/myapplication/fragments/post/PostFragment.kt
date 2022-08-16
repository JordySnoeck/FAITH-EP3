package com.example.myapplication.fragments.post

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.model.User
import com.example.myapplication.viewmodel.PostViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.custom_post.view.*
import kotlinx.android.synthetic.main.fragment_post.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class PostFragment : Fragment() {

    private lateinit var mPostViewModel : PostViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_post, container, false)

        //PostViewModel
        mPostViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        val sharedPref = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email","default value")


        Log.d("EMAILLL" , email.toString())

        if(email == null || email.toString() == "default value"){
            findNavController().navigate(R.id.homeFragment)

        }


        //RecyclerView
        val adapter = PostAdapter(email, mPostViewModel)
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



        if(email == "jordysnoeckk@hotmail.com") {
            view.floatingActionButton2.isVisible = false
        }

        if(email == "jordysnoeckk@hotmail.com"){
            mPostViewModel.posts.observe(viewLifecycleOwner, { post ->
                adapter.setData(post)
            })
        } else {
            mPostViewModel.getPostsByEmail(email.toString()).observe(viewLifecycleOwner, { post ->
                adapter.setData(post)
            })
        }


        view.floatingActionButton2.setOnClickListener{
            findNavController().navigate(R.id.action_postFragment_to_addPostFragment)
        }
        return view
    }

}