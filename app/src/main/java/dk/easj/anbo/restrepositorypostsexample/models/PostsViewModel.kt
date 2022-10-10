package dk.easj.anbo.restrepositorypostsexample.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dk.easj.anbo.restrepositorypostsexample.repository.PostsRepository

class PostsViewModel : ViewModel() {
    private val repository: PostsRepository = PostsRepository()
    val postsLiveData: LiveData<List<Post>?> = repository.postsLiveData
    val errorMessage: LiveData<String> = repository.errorMessageLiveData

    fun get(position: Int): Post? {
        return postsLiveData.value?.get(position)
    }

    fun add(post: Post) {
       repository.addPost(post)
    }

}