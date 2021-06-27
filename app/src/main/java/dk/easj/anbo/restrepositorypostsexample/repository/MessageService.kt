package dk.easj.anbo.restrepositorypostsexample.repository

import dk.easj.anbo.restrepositorypostsexample.models.Post
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MessageService {
    @GET("/posts")
    fun getPosts( /*@Query("id") String id*/): Call<List<Post>>

    @POST("/posts")
    fun addPost(@Body post: Post): Call<Post>
}