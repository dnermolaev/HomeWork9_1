package ru.netology.nmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class PostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentPostBinding.inflate(
            inflater,
            container,
            false
        )

        val viewHolder =
            PostViewHolder(view.postLayout, object :PostInteractionListener(viewModel, requireContext()) {
                override fun onEdit(post: Post) {
                    viewModel.edit(post)
                    findNavController().navigate(R.id.action_postFragment_to_newPostFragment2,
                        Bundle().apply { textArg = post.content })
                }
            })

        val id = arguments?.textArg?.toLong()?:-1

        viewModel.data.observe(viewLifecycleOwner){posts ->
            val post = posts.find{it.id == id}?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(post)

        }
        return view.root
    }
}
