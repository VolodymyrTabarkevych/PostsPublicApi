package ua.tabarkevych.postspublicapi.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import ua.tabarkevych.postspublicapi.databinding.FragmentPostsBinding
import ua.tabarkevych.postspublicapi.extensions.findAdapterOrNull
import ua.tabarkevych.postspublicapi.model.Post
import ua.tabarkevych.postspublicapi.ui.base.BaseFragment
import ua.tabarkevych.postspublicapi.ui.posts.adapter.PostItemDecorator
import ua.tabarkevych.postspublicapi.ui.posts.adapter.PostsAdapter
import ua.tabarkevych.postspublicapi.ui.posts.adapter.PostsFooterAdapter

@AndroidEntryPoint
class PostsFragment : BaseFragment<FragmentPostsBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentPostsBinding::inflate

    private val viewModel: PostsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        subscribeObservers()
    }

    private fun initRecycler() {
        binding.recyclerPosts.let {
            it.addOnScrollListener(scrollListener)
            it.addItemDecoration(PostItemDecorator())
            it.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            it.adapter = ConcatAdapter()
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.PostsLoaded<List<Post>> -> {
                    showLoading(false)
                    showNextPostsLoading(false)
                    showPosts(dataState.data.toMutableList())
                }
                is DataState.IsLastPage -> {
                    isLastPage = dataState.isLastPage
                }
                is DataState.Loading -> {
                    showLoading(true)
                }
                is DataState.NextPostsLoading -> {
                    showNextPostsLoading(true)
                }
            }
        })
    }

    private fun showPosts(posts: MutableList<Post>) {
        with(binding) {
            if (posts.isNotEmpty()) {
                layoutOffline.linearMain.visibility = View.GONE
                recyclerPosts.visibility = View.VISIBLE
                val concatAdapter: ConcatAdapter = recyclerPosts.adapter as ConcatAdapter
                val postsAdapter: PostsAdapter? =
                    concatAdapter.findAdapterOrNull(PostsAdapter::class.java)
                postsAdapter?.insertPosts(posts) ?: run {
                    concatAdapter.addAdapter(0, PostsAdapter(posts))
                }
            } else {
                recyclerPosts.visibility = View.GONE
                layoutOffline.linearMain.visibility = View.VISIBLE
            }
        }
    }

    var isLoading = false
    var isLastPage = false
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (!isLoading && !isLastPage) {
                val offset = recyclerView.adapter!!.itemCount
                val lastVisibleItemPosition: Int =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                if (lastVisibleItemPosition >= offset - 4) {
                    viewModel.getPosts()
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        isLoading = show
        binding.progressPosts.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showNextPostsLoading(show: Boolean) {
        isLoading = show
        val concatAdapter: ConcatAdapter = binding.recyclerPosts.adapter as ConcatAdapter
        val postsFooterAdapter: PostsFooterAdapter? =
            concatAdapter.findAdapterOrNull(PostsFooterAdapter::class.java)
        postsFooterAdapter?.showLoading(show) ?: run {
            val footerAdapter = PostsFooterAdapter()
            concatAdapter.addAdapter(footerAdapter)
            footerAdapter.showLoading(show)
        }
    }
}