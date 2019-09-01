package com.rocca.payskytask.presentation.GithubRepositories.ui.callback


import androidx.recyclerview.widget.DiffUtil
import com.rocca.payskytask.domain.enitities.jackwhartonRepositoriesResponse.GithubRepositoriesItem

class GithubRepoDiffCallback : DiffUtil.ItemCallback<GithubRepositoriesItem>() {

    override fun areItemsTheSame(oldItem: GithubRepositoriesItem, newItem: GithubRepositoriesItem): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun getChangePayload(oldItem: GithubRepositoriesItem, newItem: GithubRepositoriesItem): Any? {
        return super.getChangePayload(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: GithubRepositoriesItem, newItem: GithubRepositoriesItem): Boolean {
        return oldItem == newItem
    }
}