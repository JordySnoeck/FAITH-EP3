package com.example.myapplication.fragments.post

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.viewmodel.PostViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.custom_post.view.*
import kotlinx.android.synthetic.main.fragment_post.view.*

class PostFragment : Fragment() {

    private lateinit var mPostViewModel : PostViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_post, container, false)

        //RecyclerView
        val adapter = PostAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



        //PostViewModel
            mPostViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
            mPostViewModel.posts.observe(viewLifecycleOwner, Observer { post ->
                adapter.setData(post)
            })


        view.floatingActionButton2.setOnClickListener{
            findNavController().navigate(R.id.action_postFragment_to_addPostFragment)
        }
        return view
    }

}