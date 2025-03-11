package com.example.xmlprofileactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter(
    private val onLikeClick: (Post) -> Unit = {},
    private val onCommentClick: (Post) -> Unit = {}
) : ListAdapter<Post, PostAdapter.PostViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)

        holder.textPostContent.text = post.content

        if (!post.imageUrl.isNullOrEmpty()) {
            holder.imagePost.visibility = View.VISIBLE
            Glide.with(holder.itemView)
                .load(post.imageUrl)
                .into(holder.imagePost)
        } else {
            holder.imagePost.visibility = View.GONE
        }

        holder.textLikeCount.text = post.likeCount.toString()
        holder.buttonLike.setOnClickListener { onLikeClick(post) }

        holder.textCommentCount.text = post.commentCount.toString()
        holder.buttonComment.setOnClickListener { onCommentClick(post) }
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textPostContent: TextView = itemView.findViewById(R.id.textPostContent)
        val imagePost: ImageView = itemView.findViewById(R.id.imagePost)
        val buttonLike: ImageView = itemView.findViewById(R.id.buttonLike)
        val textLikeCount: TextView = itemView.findViewById(R.id.textLikeCount)
        val buttonComment: ImageView = itemView.findViewById(R.id.buttonComment)
        val textCommentCount: TextView = itemView.findViewById(R.id.textCommentCount)
    }

    private class DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
}
