package com.example.myapplication.fragments.update

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
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
import kotlin.math.log


class CommentAdapter(vm: CommentViewModel, email : String?, username: String): RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {


    private var commentList = emptyList<Comment>()
    private var vm: CommentViewModel = vm
    private var username : String = username


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
        holder.itemView.newuser.text = currentComment.reactedTo
        holder.itemView.react.isVisible = true
        holder.itemView.openccomment.isVisible = false
        holder.itemView.button3.isVisible = false

        holder.itemView.button3.setOnClickListener{
            deleteComment(currentComment.commentId)
        }


        if(username == currentComment.username){
            holder.itemView.react.isVisible = false
            holder.itemView.openccomment.isVisible = true
            holder.itemView.button3.isVisible = true
        }

        holder.itemView.editcomment.setOnClickListener{
            editComment(holder.itemView.editText.text.toString(),currentComment)
            holder.itemView.editText.isVisible = false
            holder.itemView.commentText.isVisible = true
            holder.itemView.editcomment.isVisible = false
            holder.itemView.openccomment.isVisible = true
            holder.itemView.react.isVisible = true
            holder.itemView.button3.isVisible = true
        }

        holder.itemView.sendcomment.isVisible = false
        holder.itemView.enterComment.isVisible = false
        holder.itemView.editText.isVisible = false
        holder.itemView.editcomment.isVisible = false

        if(!currentComment.reaction){
            holder.itemView.reactedTo.isVisible = false
            holder.itemView.newuser.isVisible = false
        }

        holder.itemView.react.setOnClickListener{
            holder.itemView.sendcomment.isVisible = true
            holder.itemView.enterComment.isVisible = true
            holder.itemView.react.isVisible = false
            holder.itemView.openccomment.isVisible= false
            holder.itemView.button3.isVisible=false
        }

        holder.itemView.sendcomment.setOnClickListener{
            addComment(holder.itemView.enterComment.text.toString(),currentComment)
            holder.itemView.sendcomment.isVisible = false
            holder.itemView.enterComment.isVisible = false
            holder.itemView.react.isVisible = true
            holder.itemView.openccomment.isVisible= true
            holder.itemView.button3.isVisible=true
        }

        holder.itemView.openccomment.setOnClickListener{
            holder.itemView.editText.isVisible = true
            holder.itemView.commentText.isVisible = false
            holder.itemView.editcomment.isVisible = true
            holder.itemView.openccomment.isVisible = false
            holder.itemView.react.isVisible = false
            holder.itemView.button3.isVisible = false
            holder.itemView.editText.setText(currentComment.commentText)
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

    private fun editComment(editcom : String, com : Comment){
        val comment = Comment(com.commentId,com.postId,editcom,com.commentDate,com.reaction, com.reactedTo, com.userId,com.username)
        vm.updateComment(comment)

    }

    private fun addComment(editcom : String, com : Comment) {
        val comment = Comment(0,com.postId,editcom,com.commentDate,true, com.username,com.userId,username)
        vm.addComment(comment)

    }
}