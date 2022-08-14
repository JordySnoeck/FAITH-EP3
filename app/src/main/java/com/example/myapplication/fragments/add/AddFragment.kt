package com.example.myapplication.fragments.add

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.myapplication.R
import com.example.myapplication.model.User
import com.example.myapplication.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.coroutines.launch

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

//        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
//
//        view.add_btn.setOnClickListener{
//            insertDataToDatabase()
//        }
//
        return view
//    }
//
//
//    private fun insertDataToDatabase() {
//        val firstName = addFirstName_et.text.toString()
//        val lastName = addLastName_et.text.toString()
//        val age = addAge_et.text
//
//
//        if(inputCheck(firstName,lastName,age)) {
//            lifecycleScope.launch {
//                val user =
//                    User(0, getBitmap(), firstName, lastName, Integer.parseInt(age.toString()))
//                mUserViewModel.addUser(user)
//            }
//            Toast.makeText(requireContext(), "succesfully added", Toast.LENGTH_LONG).show()
//            //navigate back
//            view?.findNavController()?.navigate(R.id.action_addFragment_to_listFragment)
//        } else {
//            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    private suspend fun getBitmap(): Bitmap {
//        val loading : ImageLoader = ImageLoader(requireContext())
//        val request = ImageRequest.Builder(requireContext())
//            .data("https://cdn.icon-icons.com/icons2/2643/PNG/512/male_boy_person_people_avatar_icon_159358.png")
//            .build()
//
//        val result = (loading.execute(request) as SuccessResult).drawable
//        return (result as BitmapDrawable).bitmap
//    }
//
//
//    private fun inputCheck(firstName: String , lastName: String, age: Editable): Boolean{
//        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
//    }

    }

}