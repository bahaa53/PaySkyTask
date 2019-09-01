package com.rocca.payskytask.data.api

import com.rocca.payskytask.domain.enitities.jackwhartonRepositoriesResponse.GithubRepositoriesItem
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("JakeWharton/repos")
    fun getGithubRepos(
            @Query("per_page") loadSize: Int = 15,
            @Query(value = "page") page: Int = 1
    ):  Deferred<Response<MutableList<GithubRepositoriesItem?>>>

}