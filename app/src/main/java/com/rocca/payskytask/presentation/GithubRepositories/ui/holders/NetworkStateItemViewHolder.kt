package com.rocca.payskytask.presentation.GithubRepositories.ui.holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.rocca.payskytask.data.api.networkStatus.NetworkState
import com.rocca.payskytask.data.api.networkStatus.Status
import com.rocca.payskytask.R


class NetworkStateItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.network_state_item, parent, false)) {

    private var progressBar: ProgressBar? = null

    init {
        progressBar = itemView.findViewById(R.id.progress_bar)
    }

    fun bindView(networkState: NetworkState?) {
        if (networkState != null && networkState?.state === Status.RUNNING) {
            progressBar?.setVisibility(View.VISIBLE)
        } else {
            progressBar?.setVisibility(View.GONE)
        }
    }
}