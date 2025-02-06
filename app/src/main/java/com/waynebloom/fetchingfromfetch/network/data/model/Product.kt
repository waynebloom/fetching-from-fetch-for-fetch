package com.waynebloom.fetchingfromfetch.network.data.model

import com.squareup.moshi.Json

data class Product(
    val id: Int,

    @Json(name = "listId")
    val listID: Int,
    val name: String?,
)