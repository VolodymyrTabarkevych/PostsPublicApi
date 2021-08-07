package ua.tabarkevych.publicapiapp.ui.posts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.tabarkevych.publicapiapp.databinding.ItemPostsFooterBinding

class PostsFooterAdapter : RecyclerView.Adapter<PostsFooterAdapter.PostsFooterViewHolder>() {
    private var showLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsFooterViewHolder {
        return PostsFooterViewHolder(
            ItemPostsFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostsFooterViewHolder, position: Int) {
        holder.binding.progressNextPosts.visibility = if (showLoading) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return 1
    }

    fun showLoading(show: Boolean) {
        showLoading = show
        notifyItemChanged(0)
    }

    class PostsFooterViewHolder(val binding: ItemPostsFooterBinding) :
        RecyclerView.ViewHolder(binding.root)
}