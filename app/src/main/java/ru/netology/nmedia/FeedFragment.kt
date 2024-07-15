package ru.netology.nmedia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding


import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    private val urls = listOf("netology.jpg", "sber.jpg", "tcs.jpg", "404.png")
    private var index = 0

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )


        val adapter = PostsAdapter(object : PostInteractionListener(viewModel, requireContext()) {
            override fun onEdit(post: Post) {
                viewModel.edit(post) // <------
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { textArg = post.content })
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onPostOpen(post: Post) {
                findNavController().navigate(R.id.action_feedFragment_to_postFragment,
                    Bundle().apply { textArg = post.id.toString() })
            }
        })
        binding.list.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.posts)
            binding.emptyText.isVisible = state.empty
        }



        binding.newPosts.setOnClickListener {
            viewModel.loadHiddenPosts()

        }

        viewModel.newerCount.observe(viewLifecycleOwner) { state ->
            binding.newPosts.isVisible

            Log.d("FeedFragment", "Newer count: $")

        }

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0){
                    binding.list.smoothScrollToPosition(0)
                }
            }

        })

        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state.loading
            binding.swiperefresh.isRefreshing = state.refreshing
            if (state.error) {
                Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading) { viewModel.loadPosts() }
                    .show()
            }
        }



        /*binding.retryButton.setOnClickListener {
            viewModel.loadPosts()
        }*/

        binding.swiperefresh.setOnRefreshListener {
            viewModel.refreshPosts()
            //binding.swiperefresh.isRefreshing = false
        }

        val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
            if (result == null) {
                viewModel.undoEdit()
                return@registerForActivityResult
            }
            viewModel.changeContent(result)
            viewModel.save()
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id !== 0L) {
                editPostLauncher.launch(post.content)
            }
        }

        return binding.root

    }
}

