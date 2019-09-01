package com.rocca.payskytask.domain.repository


import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.rocca.payskytask.data.api.networkStatus.NetworkState
import com.rocca.payskytask.domain.enitities.jackwhartonRepositoriesResponse.GithubRepositoriesItem


interface GithubElementsRepositories {
    suspend fun getGithubRepositoriesElements(): LiveData<PagedList<GithubRepositoriesItem?>>

    fun getNetworkState(): LiveData<NetworkState>
}