package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.fragments.update.CommentAdapter
import com.example.myapplication.fragments.update.UpdatePostFragmentArgs
import com.example.myapplication.model.Comment
import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import com.example.myapplication.viewmodel.CommentViewModel
import com.example.myapplication.viewmodel.PostViewModel
import com.example.myapplication.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.custom_comment.view.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_post_info.*
import kotlinx.android.synthetic.main.fragment_post_info.view.*
import kotlinx.android.synthetic.main.fragment_update_post.*
import kotlinx.android.synthetic.main.fragment_update_post.view.*
import java.util.*

class PostInfoFragment : Fragment() {


    private val args by navArgs<PostInfoFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mPostViewModel: PostViewModel
    private lateinit var mCommentViewModel: CommentViewModel
    lateinit var user: User
    var email: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_post_info, container, false)

        mPostViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        mCommentViewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val sharedPref = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        email = sharedPref.getString("email","default value")
        mUserViewModel.getUserByEmail(email.toString()).observe(viewLifecycleOwner){
            user = User(it.id,it.email,it.profilePhoto,it.firstName,it.lastName,it.age)
        val adapter = CommentAdapter(mCommentViewModel, email)
        val recyclerView= view.commentSection
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mCommentViewModel.getCommentsByPostId(args.currentPost.id).observe(viewLifecycleOwner,
            { comment ->
                adapter.setData(comment)
            })
        }

        view.post_user.setText(args.currentPost.user)
        view.post_text.setText(args.currentPost.postText)
        view.imagepost.setImageBitmap(args.currentPost.image)

        view.add_comment.setOnClickListener{
            addComment()
        }


        return view
    }

    private fun addComment(){
        val postId = args.currentPost.id
        val commentText = postComment.text.toString()
        val commentDate =  Date()

        val comment = Comment(0,postId,commentText,commentDate,user.id,user.firstName.plus(" ").plus(user.lastName))
        if(email == "jordysnoeckk@hotmail.com"){
            val updatedPost = Post(args.currentPost.id,args.currentPost.date,args.currentPost.user,args.currentPost.email,args.currentPost.image,args.currentPost.postText,args.currentPost.readed,true,args.currentPost.postName,args.currentPost.favorite)
            mPostViewModel.updatePost(updatedPost)
        }
        mCommentViewModel.addComment(comment)

        Toast.makeText(requireContext(), "Succesfully added comment", Toast.LENGTH_SHORT).show()

        postComment.text.clear()

    }




}