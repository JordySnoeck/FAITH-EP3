package com.example.myapplication.fragments.post

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Post
import com.example.myapplication.viewmodel.CommentViewModel
import com.example.myapplication.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.custom_post.view.*



class PostAdapter(email: String?, vm : PostViewModel): RecyclerView.Adapter<PostAdapter.MyViewHolder>() {


    private var postList = emptyList<Post>()
    private val mContext: Context? = null
    private val email = email
    private var vm: PostViewModel = vm


    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder((LayoutInflater.from(parent.context).inflate(R.layout.custom_post,parent, false)))
        mContext = parent.context
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPost = postList[position]
        holder.itemView.postText_txt.text = currentPost.postText
        if (currentPost.favorite){
            holder.itemView.textView8.setText("Liked")
        } else {
            holder.itemView.textView8.setText("")
        }
        holder.itemView.postName_txt.text = currentPost.user




        if(email == "jordysnoeckk@hotmail.com"){
            holder.itemView.edit_post.isVisible = false
            if(currentPost.readed) {
                holder.itemView.readed.setText("Read")
            } else {
                holder.itemView.readed.setText("Unreaded")
            }
            if(currentPost.answered){
                holder.itemView.answered.setText("Answered")
            } else {
                holder.itemView.answered.setText("No answers")
            }
        }

        holder.itemView.postLayout.setOnClickListener{
            Log.d("EMAIL READED ", email.toString())
            if(email == "jordysnoeckk@hotmail.com"){
                val updatedPost = Post(currentPost.id,currentPost.date,currentPost.user,currentPost.email,currentPost.image,currentPost.postText,currentPost.link,true,currentPost.answered,currentPost.postName,currentPost.favorite)
                vm.updatePost(updatedPost)
            }
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