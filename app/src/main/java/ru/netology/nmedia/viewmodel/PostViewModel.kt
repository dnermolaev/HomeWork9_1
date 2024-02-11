package ru.netology.nmedia.viewmodel

import PostRepositoryInMemoryImpl
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository

class PostViewModel : ViewModel() {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    fun likeById(id: Long) = repository.likeById(id)
    fun share (id: Long) = repository.shared(id)
}