package com.example.myapplication.fragments.update

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.myapplication.R
import com.example.myapplication.fragments.profile.UpdateProfile
import com.example.myapplication.model.Post
import com.example.myapplication.viewmodel.CommentViewModel
import com.example.myapplication.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.fragment_update_post.*
import kotlinx.android.synthetic.main.fragment_update_post.view.*
import kotlinx.android.synthetic.main.fragment_update_post.view.button2
import kotlinx.coroutines.launch


class UpdatePostFragment : Fragment() {

    private val args by navArgs<UpdatePostFragmentArgs>()

    private lateinit var mPostViewModel: PostViewModel
    private lateinit var mCommentViewModel: CommentViewModel
    private lateinit var imageView: ImageView
    var email: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_post, container, false)

        mPostViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        mCommentViewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        imageView = view.updateimage

        val sharedPref = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        email = sharedPref.getString("email","default value")

        val adapter = CommentAdapter(mCommentViewModel, email, "")
        val recyclerView= view.commentSection2
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mCommentViewModel.getCommentsByPostId(args.currentPost.id).observe(viewLifecycleOwner,
            { comment ->
                adapter.setData(comment)
            })


        view.update_Text.setText(args.currentPost.postText)
        view.updateimage.setImageBitmap(args.currentPost.image)
        view.editLink.setText(args.currentPost.link)

        view.btn_del_post.setOnClickListener{
            deletePost()
        }

        view.button2.setOnClickListener{
            updatePost()
        }

        view.like_post.setOnClickListener{
        likePost()
        }

        view.updateimagepost.setOnClickListener{
            pickImageGallery()
        }


        if(args.currentPost.favorite){
            view.like_post.setText("Liked")
        } else {
            view.like_post.setText("Like")
        }

        return view
    }

    private fun likePost() {
        if(!args.currentPost.favorite) {
            val updatedPost = Post(
                args.currentPost.id,
                args.currentPost.date,
                args.currentPost.user,
                args.currentPost.email,
                args.currentPost.image,
                args.currentPost.postText,
                args.currentPost.link,
                args.currentPost.readed,
                args.currentPost.answered,
                args.currentPost.postName,
                true
            )
            mPostViewModel.updatePost(updatedPost)
            view?.like_post?.setText("Liked")
        } else {
            val updatedPost = Post(
                args.currentPost.id,
                args.currentPost.date,
                args.currentPost.user,
                args.currentPost.email,
                args.currentPost.image,
                args.currentPost.postText,
                args.currentPost.link,
                args.currentPost.readed,
                args.currentPost.answered,
                args.currentPost.postName,
                false
            )
            mPostViewModel.updatePost(updatedPost)
            view?.like_post?.setText("Like")
        }
    }

    private fun updatePost(){
        val postText = update_Text.text.toString()
        val image = updateimage
        val link = editLink.text.toString()

        if(inputCheck(postText)){
            if(image.drawable != null) {
                val conImage = (image.drawable as BitmapDrawable).bitmap
                val updatedPost = Post(args.currentPost.id,args.currentPost.date,args.currentPost.user,args.currentPost.email,conImage,postText,link,args.currentPost.readed,args.currentPost.answered,args.currentPost.postName,args.currentPost.favorite)
                mPostViewModel.updatePost(updatedPost)
            } else {
                lifecycleScope.launch {
                    val updatedPost = Post(args.currentPost.id,args.currentPost.date,args.currentPost.user,args.currentPost.email,getBitmap(),postText,link,args.currentPost.readed,args.currentPost.answered,args.currentPost.postName,args.currentPost.favorite)
                    mPostViewModel.updatePost(updatedPost)
                }
            }
            Toast.makeText(requireContext(),"Updated Succesfully", Toast.LENGTH_SHORT).show()
            //navigate Back
            findNavController().navigate(R.id.action_updatePostFragment_to_postFragment)
        } else {
            Toast.makeText(requireContext(),"please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck( postText: String): Boolean{
        return !(TextUtils.isEmpty(postText))
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

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent, UpdateProfile.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == UpdateProfile.IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            imageView.setImageURI(data?.data)
        }
    }

    private suspend fun getBitmap(): Bitmap {
        val loading : ImageLoader = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data("https://simg.nicepng.com/png/small/103-1035208_mpls-make-white-outline-white-square-transparent-outline.png")
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

}