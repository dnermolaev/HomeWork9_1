package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Count
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val count = Count ()

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content

                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )

                likesCount.text =
                    if (post.likedByMe) {
                        count.convertAmount(1+post.likes)
                    } else {
                        count.convertAmount(post.likes)
                    }


                shareCount.text= count.convertAmount(post.shares)

                like.setOnClickListener {
                    viewModel.like()
                }

                share.setOnClickListener {
                    viewModel.share()
                }

            }

        }
    }
}