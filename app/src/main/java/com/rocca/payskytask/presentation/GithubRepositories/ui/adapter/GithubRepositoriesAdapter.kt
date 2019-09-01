package com.rocca.payskytask.presentation.GithubRepositories.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rocca.payskytask.data.api.networkStatus.NetworkState
import com.rocca.payskytask.domain.enitities.jackwhartonRepositoriesResponse.GithubRepositoriesItem
import com.rocca.payskytask.presentation.GithubRepositories.ui.callback.GithubRepoDiffCallback
import com.rocca.payskytask.presentation.GithubRepositories.ui.holders.NetworkStateItemViewHolder
import com.rocca.payskytask.presentation.GithubRepositories.ui.holders.RepositoryItemVireHolder
import com.rocca.payskytask.R




class GithubRepositoriesAdapter :
        PagedListAdapter<GithubRepositoriesItem, RecyclerView.ViewHolder>(GithubRepoDiffCallback()) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == R.layout.item_github_repository_layout) {
            return RepositoryItemVireHolder(inflater, parent)
        } else if (viewType == R.layout.network_state_item) {
            return NetworkStateItemViewHolder(inflater, parent)
        } else run({ throw IllegalArgumentException("unknown view type") })


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_github_repository_layout -> {
                val githubRepositoryItem = getItem(position)
                (holder as RepositoryItemVireHolder).bindData(githubRepositoryItem)
            }
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindView(networkState)
        }
    }

    private fun hasExtraRow(): Boolean {
        return if (networkState != null && networkState !== NetworkState.LOADED) {
            true
        } else {
            false
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val previousExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.item_github_repository_layout
        }
    }
}

