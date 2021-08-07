package ua.tabarkevych.publicapiapp.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ua.tabarkevych.publicapiapp.model.Post
import ua.tabarkevych.publicapiapp.repository.PostsRepository
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Post>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<Post>>>
        get() = _dataState

    var postsPage: Int = 1

    init {
        getPosts()
    }

    fun getPosts() {
        viewModelScope.launch {
            postsRepository.getPosts(postsPage)
                .onEach { dataState ->
                    if (dataState == DataState.IncreasePage) {
                        postsPage++
                    } else {
                        _dataState.postValue(dataState)
                    }
                }.launchIn(viewModelScope)
        }
    }
}

sealed class DataState<out R> {
    data class PostsLoaded<out T>(val data: T) : DataState<T>()
    data class IsLastPage(val isLastPage: Boolean) : DataState<Nothing>()
    object IncreasePage : DataState<Nothing>()
    object LoadError : DataState<Nothing>()
    object Loading : DataState<Nothing>()
    object NextPostsLoading : DataState<Nothing>()
}