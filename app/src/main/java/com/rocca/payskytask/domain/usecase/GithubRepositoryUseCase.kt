package com.rocca.payskytask.domain.usecase


import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.rocca.payskytask.data.api.networkStatus.NetworkState

import com.rocca.payskytask.domain.enitities.jackwhartonRepositoriesResponse.GithubRepositoriesItem
import com.rocca.payskytask.domain.repository.GithubElementsRepositories


class GithubRepositoryUseCase(private val githubElementsRepositories: GithubElementsRepositories) {

    suspend fun getGithubRepositoriesElements(): LiveData<PagedList<GithubRepositoriesItem?>> {
        return githubElementsRepositories.getGithubRepositoriesElements()
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return githubElementsRepositories.getNetworkState()
    }
}