package com.example.assignment.api

import com.example.assignment.models.pixabayitem.PixabayItem
import com.example.assignment.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaApi {

    @GET("https://pixabay.com/api/")
    suspend fun getPixaImg(
        @Query("key")
        api_key:String=API_KEY,
        @Query("q")
        query: String = "sun",
    @Query("per_page")
    no:Int=60
        ):Response<PixabayItem>
}