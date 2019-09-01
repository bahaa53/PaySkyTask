package com.rocca.payskytask.presentation.GithubRepositories.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rocca.payskytask.data.api.networkStatus.NetworkState
import com.rocca.payskytask.domain.enitities.jackwhartonRepositoriesResponse.GithubRepositoriesItem
import com.rocca.payskytask.domain.usecase.GithubRepositoryUseCase


class GithupRepositoriesViewModel(
    private val githubRepositoryUseCase: GithubRepositoryUseCase
) : ViewModel() {

    suspend fun getGithubRepositoryElements(): LiveData<PagedList<GithubRepositoriesItem?>> {
        val elmenusTagsresult = githubRepositoryUseCase.getGithubRepositoriesElements();
        return elmenusTagsresult;
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return githubRepositoryUseCase.getNetworkState()
    }
}
