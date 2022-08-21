package com.example.myapplication.fragments.post

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.fragments.update.CommentAdapter
import com.example.myapplication.model.Comment
import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import com.example.myapplication.viewmodel.CommentViewModel
import com.example.myapplication.viewmodel.PostViewModel
import com.example.myapplication.viewmodel.UserViewModel
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks
import kotlinx.android.synthetic.main.fragment_post_info.*
import kotlinx.android.synthetic.main.fragment_post_info.view.*
import kotlinx.android.synthetic.main.fragment_post_info.view.imagepost
import java.util.*

class PostInfoFragment : Fragment() {


    private val args by navArgs<PostInfoFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mPostViewModel: PostViewModel
    private lateinit var mCommentViewModel: CommentViewModel
    lateinit var user: User
    private lateinit var linkk: TextView
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

        if(!args.currentPost.link.isEmpty()) {
            linkk = view.textViewLink
            linkSetup()
        } else {

            view.textViewLink.isVisible = false
        }



        val sharedPref = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        email = sharedPref.getString("email","default value")
        mUserViewModel.getUserByEmail(email.toString()).observe(viewLifecycleOwner){
            user = User(it.id,it.email,it.profilePhoto,it.firstName,it.lastName,it.age)
        val adapter = CommentAdapter(mCommentViewModel, email, it.firstName.plus(" ").plus(it.lastName))
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

        val comment = Comment(0,postId,commentText,commentDate,false,"",user.id,user.firstName.plus(" ").plus(user.lastName))
        if(email == "jordysnoeckk@hotmail.com"){
            val updatedPost = Post(args.currentPost.id,args.currentPost.date,args.currentPost.user,args.currentPost.email,args.currentPost.image,args.currentPost.postText,args.currentPost.link,args.currentPost.readed,true,args.currentPost.postName,args.currentPost.favorite)
            mPostViewModel.updatePost(updatedPost)
        }
        mCommentViewModel.addComment(comment)

        Toast.makeText(requireContext(), "Succesfully added comment", Toast.LENGTH_SHORT).show()

        postComment.text.clear()

    }

    private fun linkSetup(){
        val hyperlink = args.currentPost.link

        val link = Link("hyperlink")
            .setTextColor(Color.BLUE)
            .setTextColorOfHighlightedLink(Color.CYAN)
            .setHighlightAlpha(.4f)
            .setUnderlined(true)
            .setBold(false)
            .setOnClickListener {
                Toast.makeText(requireContext(),"link clicked", Toast.LENGTH_SHORT).show()
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(hyperlink))
                startActivity(i)
            }
        linkk.applyLinks(link)


    }




}