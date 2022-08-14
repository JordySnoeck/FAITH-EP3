package com.example.myapplication.fragments.update

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Comment
import kotlinx.android.synthetic.main.custom_comment.view.*


class CommentAdapter: RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {


    private var commentList = emptyList<Comment>()

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
        holder.itemView.commentId.text = currentComment.commentId.toString()
        holder.itemView.commentText.text = currentComment.commentText
    }

    fun setData(comment : List<Comment>){
        this.commentList = comment
        notifyDataSetChanged()

    }
}