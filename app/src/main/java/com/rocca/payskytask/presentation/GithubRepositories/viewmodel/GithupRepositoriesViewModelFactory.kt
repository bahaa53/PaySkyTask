package com.rocca.payskytask.presentation.GithubRepositories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rocca.payskytask.domain.usecase.GithubRepositoryUseCase

class GithupRepositoriesViewModelFactory(
    private val githubRepositoryUseCase: GithubRepositoryUseCase
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GithupRepositoriesViewModel::class.java)) {
            return GithupRepositoriesViewModel(
                githubRepositoryUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}