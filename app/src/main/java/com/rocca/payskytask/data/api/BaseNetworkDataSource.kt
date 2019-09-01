package com.rocca.payskytask.data.api

import retrofit2.Response

interface BaseNetworkDataSource {
    suspend fun <T : Any> isValidRespons(baseResponse: Response<T>) : Boolean
}