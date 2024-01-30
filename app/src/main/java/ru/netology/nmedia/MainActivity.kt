package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Count
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = true,
            likes = 999,
            shares = 1049
        )

        val count = Count ()
        with(binding) {
            avatar.setImageResource(R.drawable.ic_netology_48dp) // Почему кнопка views отображается сразу, а аватар нужно отдельно добавлять
            author.text = post.author
            published.text = post.published
            content.text = post.content

            if (post.likedByMe) {
                like?.setImageResource(R.drawable.ic_liked_24)
            }

            likesCount.setText(
                if (post.likedByMe) {
                    post.likes++
                    count.convertAmount(post.likes)
                }
                else {
                    count.convertAmount(post.likes)}
            )

            shareCount.text = count.convertAmount(post.shares)


            like.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                likesCount?.setText(
                    if (post.likedByMe) {
                        post.likes++
                        count.convertAmount(post.likes)
                    } else {post.likes--
                        count.convertAmount(post.likes)}
                )
            }

            share.setOnClickListener {
                post.shares++
                shareCount?.text = count.convertAmount(post.shares)}

        }

    }
}