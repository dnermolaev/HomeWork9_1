package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.Media
import ru.netology.nmedia.dto.MediaUpload
import ru.netology.nmedia.dto.Post

interface PostRepository {
    suspend fun showAll()
    val data: Flow<List<Post>>
    suspend fun getAll()
    fun getNewerCount(): Flow<Int>

    suspend fun likeById(id: Long)
    suspend fun shared(id: Long)
    suspend fun removeById(id: Long)
    suspend fun save(post: Post)
    suspend fun unlikeById(id: Long)
    suspend fun saveWithAttachment(post: Post, upload: MediaUpload)
    suspend fun upload(upload: MediaUpload): Media

}