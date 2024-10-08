package ru.netology.nmedia.dto

import ru.netology.nmedia.enumeration.AttachmentType

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 0,
    var likedByMe: Boolean = false,
    val shares: Int = 0,
    val videoLink: String? = null,
    val authorAvatar: String,
    val attachment: Attachment? = null,
)

data class Attachment(
    val url: String,
    val type: AttachmentType,

    )