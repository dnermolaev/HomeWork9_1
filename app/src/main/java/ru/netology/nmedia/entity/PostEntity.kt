package ru.netology.nmedia.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Attachment
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.enumeration.AttachmentType

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val likes: Int = 0,
    val shares: Int = 0,
    val visible: Boolean = true,
    val videoLink: String? = null,
    @Embedded
    var attachment: AttachmentEmbeddable?,
) {
    fun toDto() = Post(
        id,
        author,
        content,
        published,
        likes,
        likedByMe,
        shares,
        videoLink,
        authorAvatar,
        attachment?.toDto()
    )

    companion object {
        fun fromDto(dto: Post, visible: Boolean = true) =
            PostEntity(
                dto.id,
                dto.author,
                dto.authorAvatar,
                dto.content,
                dto.published,
                dto.likedByMe,
                dto.likes,
                dto.shares,
                visible,
                dto.videoLink,
                AttachmentEmbeddable.fromDto(dto.attachment)
            )

    }
}

data class AttachmentEmbeddable(
    var url: String,
    var type: AttachmentType,
) {
    fun toDto() = Attachment(url, type)

    companion object {
        fun fromDto(dto: Attachment?) = dto?.let {
            AttachmentEmbeddable(it.url, it.type)
        }
    }
}

fun List<PostEntity>.toDto(): List<Post> = map(PostEntity::toDto)
fun List<Post>.toEntity(visible: Boolean = true): List<PostEntity> =
    map { PostEntity.fromDto(it, visible) }