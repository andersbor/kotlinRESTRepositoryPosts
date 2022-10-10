package dk.easj.anbo.restrepositorypostsexample.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import dk.easj.anbo.restrepositorypostsexample.models.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class PostsRepository {
    private val url = "https://jsonplaceholder.typicode.com/"
    private val messageService: MessageService
    val postsLiveData: MutableLiveData<List<Post>?> = MutableLiveData<List<Post>?>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        messageService = build.create(MessageService::class.java)
        getPosts()
    }

    private fun getPosts() {
        val call: Call<List<Post>> = messageService.getPosts()

        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    postsLiveData.postValue(response.body())
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                postsLiveData.postValue(null)
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun addPost(post: Post) {
        Log.d("APPLE", "repository addPost")
        messageService.addPost(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", response.body().toString())
                    getPosts()
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                postsLiveData.postValue(null)
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

}