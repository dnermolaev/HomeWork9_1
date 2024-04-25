package ru.netology.nmedia.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.utils.SingleLiveEvent
import java.io.IOException
import kotlin.concurrent.thread

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    published = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    // упрощённый вариант
    /*private val repository: PostRepository = PostRepositoryFileImpl(application)*/

    private val repository: PostRepository = PostRepositoryImpl()

    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }

    fun loadPosts() {
        _data.postValue(FeedModel(loading = true))
        repository.getAllAsync(object : PostRepository.Callback<List<Post>> {
            override fun onSuccess(posts: List<Post>) {
                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun save() {
        edited.value?.let {
            repository.save(it, object : PostRepository.Callback<Post> {
                override fun onSuccess(posts: Post) {
                    _postCreated.postValue(Unit)
                }
                override fun onError(e: Exception) {
                    _data.postValue(FeedModel(error = true))
                }
            })

        }
        edited.value = empty
    }

    fun removeById(id: Long) {
        val old = _data.value?.posts.orEmpty()
        val callBack = object : PostRepository.Callback<Unit> {
            override fun onSuccess(data: Unit) {
                val postsUpdated = _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .filter { it.id != id }
                    )
                )
            }
            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        }
        try {
            repository.removeById(id, callBack)
        } catch (e: IOException) {
            _data.postValue(_data.value?.copy(posts = old))
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun undoEdit() {
        edited.value = empty
    }

    fun likeById(id: Long) {
        val callBack = object : PostRepository.Callback<Post> {
            override fun onSuccess(data: Post) {
                val postsUpdated = _data.value?.posts.orEmpty().map {
                    if (it.id == data.id) {
                        data
                    } else {
                        it
                    }
                }
                /*_data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty().map {
                        if (it.id != post.id) it else post
                    }*/
                _data.postValue(FeedModel(posts = postsUpdated, empty = postsUpdated.isEmpty()))
            }
            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        }

        val likedByMe = _data.value?.posts?.find { it.id == id }?.likedByMe
        val post = if (likedByMe == true) repository.unlikeById(id, callBack)
        else repository.likeById(id, callBack)
    }

    fun share(id: Long) = repository.shared(id)


    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }


}