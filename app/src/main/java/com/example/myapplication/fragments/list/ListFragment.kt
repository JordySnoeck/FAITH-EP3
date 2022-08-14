package com.example.myapplication.fragments.list

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.myapplication.CredentialsManager
import com.example.myapplication.R
import com.example.myapplication.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*



class ListFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_list, container, false)

        val token = CredentialsManager.getAccessToken(requireContext())
        //RecyclerView
        val adapter = ListAdapter()
        val recyclerView= view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //userViewModel
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.users.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
//            Log.d("USER1", user[0].id.toString())
//            Log.d("USER1", user[0].email.toString())
//            Log.d("USER1", user[1].id.toString())
//            Log.d("USER2", user[1].email.toString())
//            Log.d("USER3", user[2].id.toString())
//            Log.d("USER3", user[2].email.toString())
        })

        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)

        if(token == null){
            findNavController().navigate(R.id.action_listFragment_to_homeFragment)
        }
        return view


    }

    private suspend fun getBitmap(): Bitmap {
        val loading : ImageLoader = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data("https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Apple_logo_black.svg/488px-Apple_logo_black.svg.png")
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_,_ ->
            mUserViewModel.deleteAllUser()
            Toast.makeText(requireContext(),
                "Successfully removed everything",
                Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton("No"){_, _ ->}
        builder.setTitle("Delete everything ?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }
}