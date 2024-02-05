package ru.netology.nmedia.viewmodel

import PostRepositoryInMemoryImpl
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository

class PostViewModel : ViewModel() {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like() = repository.like()
    fun share () = repository.shared()
}