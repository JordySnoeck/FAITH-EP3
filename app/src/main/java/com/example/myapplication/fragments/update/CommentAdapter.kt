package com.example.myapplication.fragments.update

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Comment
import com.example.myapplication.viewmodel.CommentViewModel
import com.example.myapplication.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.custom_comment.view.*
import kotlinx.android.synthetic.main.custom_post.view.*


class CommentAdapter(vm: CommentViewModel): RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {


    private var commentList = emptyList<Comment>()
    private var vm: CommentViewModel = vm


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder((LayoutInflater.from(parent.context).inflate(R.layout.custom_comment, parent, false))
        )
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentComment = commentList[position]
        holder.itemView.commentUser.text = currentComment.username
        holder.itemView.commentText.text = currentComment.commentText

        holder.itemView.button3.setOnClickListener{
            deleteComment(currentComment.commentId)
        }
    }

    fun setData(comment : List<Comment>){
        this.commentList = comment
        notifyDataSetChanged()

    }

        private fun deleteComment(commentId: Int) {
            vm.deleteComment(commentId)
            notifyDataSetChanged()

    }
}