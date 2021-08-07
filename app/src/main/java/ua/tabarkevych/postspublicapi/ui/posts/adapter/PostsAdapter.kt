package ua.tabarkevych.postspublicapi.ui.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.tabarkevych.postspublicapi.model.Post
import ua.tabarkevych.publicapiapp.databinding.ItemPostBinding
import ua.tabarkevych.publicapiapp.model.Post

class PostsAdapter(
    private val posts: MutableList<Post>
) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post: Post = posts[position]
        with(holder.binding) {
            textTitle.text = post.title
            textBody.text = post.body
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun insertPosts(newPosts: MutableList<Post>) {
        val oldSize: Int = posts.size
        newPosts.removeAll(posts)
        posts.addAll(newPosts)
        notifyItemRangeInserted(oldSize, newPosts.size)
    }

    class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)
}