package com.example.myapplication.fragments.post

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.fragments.list.ListFragmentDirections
import com.example.myapplication.model.Post
import kotlinx.android.synthetic.main.custom_post.view.*

class PostAdapter: RecyclerView.Adapter<PostAdapter.MyViewHolder>() {


    private var postList = emptyList<Post>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder((LayoutInflater.from(parent.context).inflate(R.layout.custom_post,parent, false)))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPost = postList[position]
        holder.itemView.postName_txt.text = currentPost.postName
        holder.itemView.postText_txt.text = currentPost.postText
        Log.d("DATE", currentPost.date.toString())

        holder.itemView.postLayout.setOnClickListener{
            val action = PostFragmentDirections.actionPostFragmentToPostInfoFragment2(currentPost)
            holder.itemView.findNavController().navigate(action)
        }

        holder.itemView.edit_post.setOnClickListener{
            val action = PostFragmentDirections.actionPostFragmentToUpdatePostFragment(currentPost)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun setData(post: List<Post>){
        this.postList = post
        notifyDataSetChanged()
    }
}