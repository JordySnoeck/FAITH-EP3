package com.example.myapplication

import android.app.AlertDialog
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
import com.example.myapplication.fragments.update.UpdatePostFragmentArgs
import com.example.myapplication.model.Post
import com.example.myapplication.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.fragment_update_post.*
import kotlinx.android.synthetic.main.fragment_update_post.view.*

class PostInfoFragment : Fragment() {


    private val args by navArgs<PostInfoFragmentArgs>()

    private lateinit var mPostViewModel: PostViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_post_info, container, false)

        mPostViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        view.update_PostName.setText(args.currentPost.postName)
        view.update_Text.setText(args.currentPost.postText)
        view.update_User.setText(args.currentPost.user)

        return view
    }

}