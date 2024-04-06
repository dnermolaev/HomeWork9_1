package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter

import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )


        val adapter = PostsAdapter(object: PostInteractionListener (viewModel, requireContext()) {
            override fun onEdit(post: Post) {
                viewModel.edit(post) // <------
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { textArg = post.content })
            }
            override fun onPostOpen(post: Post) {
                findNavController().navigate(R.id.action_feedFragment_to_postFragment,
                    Bundle().apply { textArg = post.id.toString() })
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        /*val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
            if (result == null) {
                viewModel.undoEdit()
                return@registerForActivityResult
            }
            viewModel.changeContent(result)
            viewModel.save()
        }*/

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        /*viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id !== 0L) {
                editPostLauncher.launch(post.content)
            }
        }*/
        return binding.root
    }
}
