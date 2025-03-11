package com.example.xmlprofileactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var imageAvatar: ImageView
    private lateinit var textUserName: TextView
    private lateinit var textNickName: TextView
    private lateinit var textFollowersCount: TextView
    private lateinit var textFollowingCount: TextView
    private lateinit var textPostsCount: TextView
    private lateinit var buttonSubscribe: Button
    private lateinit var buttonMessage: Button
    private lateinit var recyclerPosts: RecyclerView

    private var isSubscribed = false

    private val postAdapter by lazy {
        PostAdapter(
            onLikeClick = { post -> onPostLikeClicked(post) },
            onCommentClick = { post -> onPostCommentClicked(post) }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecycler()
        loadUserData()
        loadPostsStub()
    }

    private fun initViews() {
        imageAvatar = findViewById(R.id.imageAvatar)
        textUserName = findViewById(R.id.textUserName)
        textNickName = findViewById(R.id.textNickName)
        textFollowersCount = findViewById(R.id.textFollowersCount)
        textFollowingCount = findViewById(R.id.textFollowingCount)
        textPostsCount = findViewById(R.id.textPostsCount)
        buttonSubscribe = findViewById(R.id.buttonSubscribe)
        buttonMessage = findViewById(R.id.buttonMessage)
        recyclerPosts = findViewById(R.id.recyclerPosts)

        buttonSubscribe.setOnClickListener {
            onSubscribeClicked()
        }

        buttonMessage.setOnClickListener {
            Toast.makeText(this, "Написать сообщение", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecycler() {
        recyclerPosts.layoutManager = LinearLayoutManager(this)
        recyclerPosts.adapter = postAdapter
    }

    private fun loadUserData() {
        textUserName.text = "NovakWilson"
        textNickName.text = "@NW"
        textFollowersCount.text = "35"
        textFollowingCount.text = "1337"
        textPostsCount.text = "2"

        val avatarUrl = "https://drive.google.com/uc?export=download&id=1dzrHAQIV0X-cRkqhYEiGSBv43aqtjdDF"
        Glide.with(this)
            .load(avatarUrl)
            .circleCrop()
            .into(imageAvatar)
    }

    private fun loadPostsStub() {
        val stubPosts = listOf(
            Post(
                id = 1,
                content = "Пятничный дроп!",
                imageUrl = "https://drive.google.com/uc?export=download&id=1fXk_7Ggd4l3kN6vI3VJB4xR1emfXWhOS",
                likeCount = 100,
                commentCount = 322,
                isLiked = false
            ),
            Post(
                id = 2,
                content = "Второй пост три четыре пять",
                imageUrl = null,
                likeCount = 5,
                commentCount = 0,
                isLiked = false
            )
        )
        postAdapter.submitList(stubPosts)
    }

    private fun onSubscribeClicked() {
        val currentFollowers = textFollowersCount.text.toString().toInt()

        if (!isSubscribed) {
            isSubscribed = true
            textFollowersCount.text = (currentFollowers + 1).toString()
            buttonSubscribe.text = "Отписаться"
            Toast.makeText(this, "Подписка оформлена!", Toast.LENGTH_SHORT).show()
        } else {
            isSubscribed = false
            textFollowersCount.text = (currentFollowers - 1).toString()
            buttonSubscribe.text = "Подписаться"
            Toast.makeText(this, "Вы отписались!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onPostLikeClicked(post: Post) {
        val updatedPost = if (!post.isLiked) {
            post.copy(isLiked = true, likeCount = post.likeCount + 1)
        } else {
            post.copy(isLiked = false, likeCount = post.likeCount - 1)
        }
        updatePostInAdapter(updatedPost)
    }

    private fun onPostCommentClicked(post: Post) {
        val updatedPost = post.copy(commentCount = post.commentCount + 1)
        updatePostInAdapter(updatedPost)
    }

    private fun updatePostInAdapter(updated: Post) {
        val currentList = postAdapter.currentList.toMutableList()
        val index = currentList.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            currentList[index] = updated
            postAdapter.submitList(currentList)
        }
    }
}
