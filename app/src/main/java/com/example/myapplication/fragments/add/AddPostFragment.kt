package com.example.myapplication.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.model.Post
import com.example.myapplication.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.fragment_add_post.*
import kotlinx.android.synthetic.main.fragment_add_post.view.*
import java.util.*

class AddPostFragment : Fragment() {

    private lateinit var mPostViewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_post, container, false)

        mPostViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        view.button2.setOnClickListener{
            insertDataToDatabase()
        }

        return view
    }


    private fun insertDataToDatabase() {
        val date = Date()
        val user = add_User.text.toString()
        val postText = add_Text.text.toString()
        val postName = add_PostName.text.toString()

        if(inputCheck(user,postText,postName)){
            val post = Post(0,date,user,postText,postName)

            mPostViewModel.addPost(post)
            Toast.makeText(requireContext(), "Succesfully added post", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_addPostFragment_to_postFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all the fields", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(user : String, postText: String, postName: String): Boolean{
        return !(TextUtils.isEmpty(user) && TextUtils.isEmpty(postText) && TextUtils.isEmpty(postName))
    }

}