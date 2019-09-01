package com.rocca.payskytask.data.repositories


import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rocca.payskytask.data.api.networkStatus.NetworkState
import com.rocca.payskytask.data.database.dao.GithubRepositoriesDao
import com.rocca.payskytask.domain.enitities.jackwhartonRepositoriesResponse.GithubRepositoriesItem
import com.rocca.payskytask.domain.repository.GithubElementsRepositories
import com.rocca.payskytask.data.api.githubRepositoriesNetworkDataSource.GithubRepoBoundaryCallback

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val PAGE_SIZE = 15

class GithubElementsRepositoriesImpl(
        private val githubRepositoriesDao: GithubRepositoriesDao,
        private val boundaryCallback: GithubRepoBoundaryCallback
) : GithubElementsRepositories {

    lateinit var githubRepositoriesList: LiveData<PagedList<GithubRepositoriesItem?>>

    override suspend fun getGithubRepositoriesElements(): LiveData<PagedList<GithubRepositoriesItem?>> {
        return withContext(Dispatchers.IO) {
            githubRepositoriesList =
                    LivePagedListBuilder(githubRepositoriesDao.getSavedGithubRepository(), getPagedListConfiguration())
                            .setBoundaryCallback(boundaryCallback)
                            .build()


            return@withContext githubRepositoriesList
        }
    }

    private fun getPagedListConfiguration(): PagedList.Config {
        return PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build()
    }

    override fun getNetworkState(): LiveData<NetworkState> {
        return boundaryCallback.getNetworkState()
    }
}