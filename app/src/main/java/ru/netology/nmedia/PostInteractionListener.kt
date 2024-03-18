package ru.netology.nmedia

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

open class PostInteractionListener (private val viewModel: PostViewModel, private val context: Context) : OnInteractionListener {

    override fun onEdit(post: Post) {
        viewModel.edit(post)
    }

    override fun onLike(post: Post) {
        viewModel.likeById(post.id)
    }

    override fun onRemove(post: Post) {
        viewModel.removeById(post.id)
    }

    override fun onShare(post: Post) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, post.content)
            type = "text/plain"
        }

        val shareIntent =
            Intent.createChooser(intent, context.getString(R.string.chooser_share_post))
        context.startActivity(shareIntent)
    }

    override fun playVideo(post: Post) {
        val webpage: Uri = Uri.parse(post.videoLink)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        context.startActivity(intent)
    }
}