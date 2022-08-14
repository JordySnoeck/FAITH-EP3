package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.myapplication.R
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.auth0.android.result.Credentials
import com.example.myapplication.domain.AuthTokenSecureFile
import com.example.myapplication.domain.SecureFileHandle
import com.example.myapplication.model.User
import com.example.myapplication.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update_profile.*
import kotlinx.android.synthetic.main.fragment_update_profile.view.*
import java.util.*
import android.os.Environment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


class UpdateProfile : Fragment() {

    private val args by navArgs<UpdateProfileArgs>()

    private lateinit var mUserViewModel: UserViewModel

    private lateinit var button: Button
    private lateinit var imageView: ImageView

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_update_profile, container, false)

        button = view.edit_img
        imageView = view.edit_profilePic

        button.setOnClickListener{
            pickImageGallery()
        }

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.edit_firstName_txt.setText(args.currentUser.firstName)
        view.edit_lastName_txt.setText(args.currentUser.lastName)
        view.edit_age_txt.setText(args.currentUser.age.toString())
        view.edit_profilePic.setImageBitmap(args.currentUser.profilePhoto)

        view.save_btn.setOnClickListener{
            updateUser()
        }


        return view
    }


    private fun updateUser(){

        val firstname = edit_firstName_txt.text.toString()
        val lastname = edit_lastName_txt.text.toString()
        val age = Integer.parseInt(edit_age_txt.text.toString())
        val image = edit_profilePic
        val conImage= (image.drawable as BitmapDrawable).bitmap

        Log.d("EMAILLLLLCURRENTUSER", args.currentUser.email)

        if(inputCheck(firstname,lastname,edit_age_txt.text)){
            lifecycleScope.launch {
                Log.d("TAG", "TEST3")
                //Create User Object
                val updatedUser = User(
                    args.currentUser.id,
                    args.currentUser.email,
                    conImage,
                    firstname,
                    lastname,
                    age
                )
                // Update current user
                mUserViewModel.updateUser(updatedUser)
                Log.d("TAG", "TEST4")
                Toast.makeText(requireContext(),"Updated Succesfully", Toast.LENGTH_SHORT).show()
                //navigate Back
                val action = UpdateProfileDirections.actionUpdateProfileToProfileFragment(updatedUser)
                view?.findNavController()?.navigate(action)
            }

        } else {
            Toast.makeText(requireContext(),"please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }


    private fun inputCheck(firstname: String, lastname: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstname) && TextUtils.isEmpty(lastname) && age.isEmpty())

    }

    private fun pickImageGallery() {
        Log.d("IMAGE1","IMAGE1")
        val intent = Intent(Intent.ACTION_PICK)
        Log.d("IMAGE2","IMAGE2")
        intent.type="image/*"
        Log.d("IMAGE3","IMAGE3")
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
        Log.d("IMAGE5", intent.data.toString())
        Log.d("IMAGE4","IMAGE4")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("IMAGE5","IMAGE5")
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("IMAGE5","IMAGE5")
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d("IMAGE5","IMAGE5")
            imageView.setImageURI(data?.data)

        }
        Log.d("IMAGE5","IMAGE5")
    }

}