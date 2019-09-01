package com.rocca.payskytask.data.api.githubRepositoriesNetworkDataSource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.rocca.payskytask.data.api.ApiInterface
import com.rocca.payskytask.data.api.checkingConnectivity.NoConnectivityException
import com.rocca.payskytask.data.api.networkStatus.NetworkState
import com.rocca.payskytask.data.api.networkStatus.Status
import com.rocca.payskytask.data.database.dao.GithubRepositoriesDao
import com.rocca.payskytask.domain.enitities.jackwhartonRepositoriesResponse.GithubRepositoriesItem
import com.rocca.payskytask.util.PagingRequestHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

private const val PAGE_SIZE = 15
private const val TAG_TITLE = "Error Fetching Repos"

class GithubRepoBoundaryCallback(private val githubRepositoriesDao: GithubRepositoriesDao,
                                 private val apiInterface: ApiInterface,
                                 private val helper: PagingRequestHelper) :
        PagedList.BoundaryCallback<GithubRepositoriesItem>() {

    private var page = 1
    private var faileMsg: String? = null
    private val networkState = MutableLiveData<NetworkState>()


    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        fetchDataFromNetwork(PagingRequestHelper.RequestType.INITIAL)
    }

    override fun onItemAtEndLoaded(itemAtEnd: GithubRepositoriesItem) {
        super.onItemAtEndLoaded(itemAtEnd)
        fetchDataFromNetwork(PagingRequestHelper.RequestType.AFTER)

    }

    private fun fetchDataFromNetwork(requestType: PagingRequestHelper.RequestType) {
        helper.runIfNotRunning(requestType) { helperCallback ->
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    networkState.postValue(NetworkState.LOADING)
                    val fetchGithubRepos = apiInterface.getGithubRepos(PAGE_SIZE, page = page)
                            .await()
                    if (fetchGithubRepos.isSuccessful) {
                        page = page + 1
                        githubRepositoriesDao.upsertGithubRepositories(fetchGithubRepos.body())
                        networkState.postValue(NetworkState.LOADED)
                        helperCallback.recordSuccess()
                    } else {
                        networkState.postValue(NetworkState(Status.FAILED, fetchGithubRepos.message()?.toString()
                                ?: "An Error Occured"))
                        helperCallback.recordSuccess()
                    }
                } catch (e: NoConnectivityException) {
                    faileMsg = "No Internet Connection"
                    handleFailureAction(e, e.message.toString(), helperCallback)
                } catch (e: Exception) {
                    handleFailureAction(e, e.message.toString(), helperCallback)
                }
            }
        }
    }

    private fun handleFailureAction(e: Exception,
                                    logMsg: String,
                                    helperCallback: PagingRequestHelper.Request.Callback) {
        Log.e(TAG_TITLE, e.message.toString(), e)
        networkState.postValue(NetworkState(Status.FAILED, logMsg?.capitalize()
                ?: "An Error Occured"))
        helperCallback.recordFailure(e)
    }

    public fun getNetworkState(): LiveData<NetworkState> {
        return networkState
    }

}