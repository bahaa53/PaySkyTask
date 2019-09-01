package com.rocca.payskytask.data.database.dao


import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rocca.payskytask.domain.enitities.jackwhartonRepositoriesResponse.GithubRepositoriesItem


@Dao
interface GithubRepositoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun upsertGithubRepositories(githubRepos: MutableList<GithubRepositoriesItem?>?)

    @Query("select * from github_repositories")
    abstract fun getSavedGithubRepository(): DataSource.Factory<Integer, GithubRepositoriesItem>
}