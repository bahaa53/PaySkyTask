package com.rocca.payskytask.presentation.GithubRepositories.ui.holders


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rocca.payskytask.domain.enitities.jackwhartonRepositoriesResponse.GithubRepositoriesItem
import com.rocca.payskytask.R
import com.squareup.picasso.Picasso

class RepositoryItemVireHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_github_repository_layout, parent, false)) {
    private var mGithubRepoName: TextView? = null
    private var mGithubRepoDesc: TextView? = null
    private var mNumberOfWatchers: TextView? = null
    private var mRepoLanguage: TextView? = null
    private var mRepoOpenedIssues: TextView? = null
    private var mTagImageView: ImageView? = null

    init {
        mGithubRepoName = itemView.findViewById(R.id.tv_github_repo_name)
        mGithubRepoDesc = itemView.findViewById(R.id.tv_github_repo_description)
        mNumberOfWatchers = itemView.findViewById(R.id.tv_github_repo_watchers)
        mRepoLanguage = itemView.findViewById(R.id.tv_github_repo_programming_language)
        mRepoOpenedIssues = itemView.findViewById(R.id.tv_github_repo_opened_issues)
        mTagImageView = itemView.findViewById(R.id.iv_github_repo)
    }

    fun bindData(githubRepositoriesItem: GithubRepositoriesItem?) {
        mGithubRepoName?.text = githubRepositoriesItem?.fullName
        mGithubRepoDesc?.text = githubRepositoriesItem?.description
        mNumberOfWatchers?.text = githubRepositoriesItem?.watchersCount.toString()
        mRepoLanguage?.text = githubRepositoriesItem?.language
        mRepoOpenedIssues?.text = githubRepositoriesItem?.openIssues.toString()
        setPictureIntoUi(githubRepositoriesItem?.owner?.avatarUrl)
    }

    private fun setPictureIntoUi(photoURL: String?) {
        Picasso.get()
            .load(photoURL)
            .into(mTagImageView)
    }
}