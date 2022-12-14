package com.example.myapplication.fragments.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.myapplication.R
import com.example.myapplication.fragments.profile.UpdateProfile
import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import com.example.myapplication.viewmodel.PostViewModel
import com.example.myapplication.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add_post.*
import kotlinx.android.synthetic.main.fragment_add_post.imagepost
import kotlinx.android.synthetic.main.fragment_add_post.view.*
import kotlinx.coroutines.launch
import java.util.*

class AddPostFragment : Fragment() {

    private lateinit var mPostViewModel: PostViewModel
    var email: String? = null
    var user: User? = null
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var imageView: ImageView


    companion object{
        val IMAGE_REQUEST_CODE = 100
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_post, container, false)
        imageView = view.imagepost
        mPostViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        view.button2.setOnClickListener{
            insertDataToDatabase()
        }
        val sharedPref = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email","default value")

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mPostViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        mUserViewModel.getUserByEmail(email.toString()).observe(viewLifecycleOwner){
            user = User(it.id,it.email,it.profilePhoto,it.firstName,it.lastName,it.age)
        }



        view.addimagepost.setOnClickListener{
            pickImageGallery()
        }

        return view
    }


    private fun insertDataToDatabase() {
        val date = Date()
        val username = user?.firstName.plus(" ").plus(user?.lastName)
        val postText = add_Text.text.toString()
        val image = imagepost
        val link = add_Link.text.toString()

        if(inputCheck(postText)){
            if(image.drawable != null) {
                val conImage = (image.drawable as BitmapDrawable).bitmap
                val post = Post(0,date,username,user!!.email,conImage,postText,link,false, false,"")
                mPostViewModel.addPost(post)
            } else {
                lifecycleScope.launch {
                    val post = Post(0,date,username,user!!.email,getBitmap(),postText,link,false , false , "")
                    mPostViewModel.addPost(post)
                }
            }
            Toast.makeText(requireContext(), "Succesfully added post", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addPostFragment_to_postFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(postText: String): Boolean{

        return !( TextUtils.isEmpty(postText))
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