package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.domain.AuthTokenSecureFile
import com.example.myapplication.domain.SecureFileHandle
import com.example.myapplication.model.User
import com.example.myapplication.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*



class ProfileFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    lateinit var user: User
    var email: String? = null
    private val args by navArgs<ProfileFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val repository = Repository()
//        val viewModelFactory = MainViewModelFactory(repository)
//        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
//        viewModel.getUserByEmail("jordysnoeckk@hotmail.com")
//        viewModel.myResponse2.observe(requireActivity(), Observer { response ->
//            if(response.isSuccessful){
//                Log.d("MAIN", response.body().toString())
//            }
//        })

        val sharedPref = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email","default value")
        Log.d("DICK", email.toString())
        if(email == null || email.toString() == "default value"){
            findNavController().navigate(R.id.homeFragment)
        }

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

            mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
            mUserViewModel.getUserByEmail(email.toString()).observe(viewLifecycleOwner){
                user = User(it.id,it.email,it.profilePhoto,it.firstName,it.lastName,it.age)
                view.profilePic.setImageBitmap(it.profilePhoto)
                view.firstName_txt.setText(it.firstName)
                view.lastName_txt.setText(it.lastName)
                view.age_txt.setText(it.age.toString())
            }

        view.edit_btn.setOnClickListener{
            editUser()
        }

        return view
    }


    private fun editUser(){
        val action = ProfileFragmentDirections.actionProfileFragmentToUpdateProfile(user)
        view?.findNavController()?.navigate(action)
    }


}