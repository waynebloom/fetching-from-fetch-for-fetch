package com.waynebloom.fetchingfromfetch.network.domain.usecase

import com.waynebloom.fetchingfromfetch.network.data.source.FetchApi
import com.waynebloom.fetchingfromfetch.network.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetData @Inject constructor(
    private val fetchApi: FetchApi
) {
    operator fun invoke(): Flow<Map<Int, List<Product>>> = flow {
        emit(fetchApi.getData())
    }.map { response ->
        val body = response.body() ?: return@map mapOf()

        body
            // filter first so that we don't waste resources on invalid data
            .filter { !it.name.isNullOrEmpty() }

            // sorting next so we don't need to sort each slice
            .sortedBy {
                it.name!!.split(' ').last().toInt()
            }

            // group the list by ID while transforming to domain model
            .groupBy(
                keySelector = { it.listID },
                valueTransform = { Product(it.id, it.name!!) }
            )

            // sort the groups by their keys
            .toSortedMap()
    }
}
