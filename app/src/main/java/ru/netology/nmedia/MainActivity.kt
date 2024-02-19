package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.utils.AndroidUtils.focusAndShowKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
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
                viewModel.share(post.id)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id !== 0L) {
                with(binding.content) {
                    focusAndShowKeyboard()
                    setText(post.content)
                }
                with(binding.undoEdit){
                    visibility= View.VISIBLE
                }
                with(binding.editedContent) {
                    setText(post.content)
                }
            }

            binding.cancel.setOnClickListener {
                with(binding.editedContent) {
                    setText("")
                    viewModel.undoEdit ()
                    clearFocus()
                    AndroidUtils.hideKeyboard(this)
                    binding.list.smoothScrollToPosition(0)
                }
                with(binding.content){
                    setText("")
                    clearFocus()
                    AndroidUtils.hideKeyboard(this)
                    binding.list.smoothScrollToPosition(0)
                }
                with(binding.undoEdit){
                    visibility= View.GONE
                }
            }

            binding.save.setOnClickListener {
                with(binding.content ) {
                    if (text.isNullOrBlank()) {
                        Toast.makeText(
                            this@MainActivity,
                            context.getString(R.string.error_empty_content),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }

                    viewModel.changeContent(text.toString())
                    viewModel.save()

                    setText("")
                    clearFocus()
                    AndroidUtils.hideKeyboard(this)
                    binding.list.smoothScrollToPosition(0)
                }
                /*with(binding.editedContent) {
                    setText("")
                    clearFocus()
                    AndroidUtils.hideKeyboard(this)
                    binding.list.smoothScrollToPosition(0)
                }
                with(binding.undoEdit){
                    visibility= View.GONE
                }*/
            }
        }

    }
}
