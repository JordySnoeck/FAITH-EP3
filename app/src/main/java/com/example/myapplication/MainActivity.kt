package com.example.myapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.viewmodel.UserViewModel

import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent.getIntent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.myapplication.model.Client
import com.example.myapplication.model.User
import com.example.myapplication.repository.Repository
import com.example.myapplication.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainViewModel
    private lateinit var mUserViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.postFragment, R.id.listFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)


        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
//        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
//        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
//        viewModel.getUser()
//        viewModel.myResponse.observe(this, Observer { response ->
//            if(response.isSuccessful) {
//                Log.d("MAIN", response.body().toString())
//                lifecycleScope.launch {
//                    val user =
//                        User(
//                            0,
//                            response.body()!!.get(0)!!.email,
//                            getBitmap(),
//                            "Jordy",
//                            "Snoeck",
//                            25
//                        )
//                    val user2 =
//                        User(
//                            1,
//                            response.body()!!.get(1)!!.email,
//                            getBitmap(),
//                            "George",
//                            "Vandejungle",
//                            16
//                        )
//                    val user3 =
//                        User(
//                            2,
//                            response.body()!!.get(2)!!.email,
//                            getBitmap(),
//                            "Winny",
//                            "Depoe",
//                            16
//                        )
//                    mUserViewModel.addUser(user)
//                    mUserViewModel.addUser(user2)
//                    mUserViewModel.addUser(user3)
//                }
//            }
//        })
   }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private suspend fun getBitmap(): Bitmap {
        val loading : ImageLoader = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data("https://cdn.icon-icons.com/icons2/2643/PNG/512/male_boy_person_people_avatar_icon_159358.png")
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.clear()
    }

}