package com.waynebloom.fetchingfromfetch.network.data.source

import com.waynebloom.fetchingfromfetch.network.data.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface FetchApi {

    @GET("hiring.json")
    suspend fun getData(): Response<List<Product>>
}