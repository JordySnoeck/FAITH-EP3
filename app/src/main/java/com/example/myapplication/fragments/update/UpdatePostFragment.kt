package com.example.myapplication.fragments.update

import android.app.AlertDialog
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.fragments.list.ListAdapter
import com.example.myapplication.model.Comment
import com.example.myapplication.model.Post
import com.example.myapplication.viewmodel.CommentViewModel
import com.example.myapplication.viewmodel.PostViewModel
import com.example.myapplication.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add_post.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_update_post.*
import kotlinx.android.synthetic.main.fragment_update_post.view.*
import kotlinx.android.synthetic.main.fragment_update_profile.*
import java.util.*


class UpdatePostFragment : Fragment() {

    private val args by navArgs<UpdatePostFragmentArgs>()

    private lateinit var mPostViewModel: PostViewModel
    private lateinit var mCommentViewModel: CommentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_post, container, false)

        mPostViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        mCommentViewModel = ViewModelProvider(this).get(CommentViewModel::class.java)

        val adapter = CommentAdapter()
        val recyclerView= view.commentSection2
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mCommentViewModel.comments.observe(viewLifecycleOwner, androidx.lifecycle.Observer { comment ->
            adapter.setData(comment)
        })

        view.update_PostName.setText(args.currentPost.postName)
        view.update_Text.setText(args.currentPost.postText)
        //view.update_User.setText(args.currentPost.user)

        view.btn_del_post.setOnClickListener{
            deletePost()
        }

        view.button2.setOnClickListener{
            updatePost()
        }

        view.like_post.setOnClickListener{
        likePost()
        }

        return view
    }

    private fun likePost() {

    }

    private fun updatePost(){
        val postName = update_PostName.text.toString()
        val postText = update_Text.text.toString()
        //val user = update_User.text.toString()
        val image = edit_profilePic
        val conImage= (image.drawable as BitmapDrawable).bitmap


        if(inputCheck(postName,postText)){
            //Create Post Object
            val updatedPost = Post(args.currentPost.id,args.currentPost.date,args.currentPost.user,args.currentPost.email,conImage,postText,postName)
            // Update current post
            mPostViewModel.updatePost(updatedPost)
            Toast.makeText(requireContext(),"Updated Succesfully", Toast.LENGTH_SHORT).show()
            //navigate Back
            findNavController().navigate(R.id.action_updatePostFragment_to_postFragment)
        } else {
            Toast.makeText(requireContext(),"please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck( postText: String, postName: String): Boolean{
        return !(TextUtils.isEmpty(postText) && TextUtils.isEmpty(postName))
    }

    private fun deletePost() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_,_ ->
            mPostViewModel.deletePost(args.currentPost)
            Toast.makeText(requireContext(),
                "Successfully removed",
                Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_updatePostFragment_to_postFragment)

        }
        builder.setNegativeButton("No"){_, _ ->}
        builder.setTitle("Delete this?")
        builder.setMessage("Are you sure you want to delete ?")
        builder.create().show()
    }

}