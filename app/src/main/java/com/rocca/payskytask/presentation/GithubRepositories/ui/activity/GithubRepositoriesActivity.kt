package com.rocca.payskytask.presentation.GithubRepositories.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rocca.payskytask.data.api.networkStatus.Status
import com.rocca.payskytask.presentation.GithubRepositories.ui.adapter.GithubRepositoriesAdapter
import com.rocca.payskytask.presentation.GithubRepositories.viewmodel.GithupRepositoriesViewModel
import com.rocca.payskytask.presentation.GithubRepositories.viewmodel.GithupRepositoriesViewModelFactory
import com.rocca.payskytask.R
import kotlinx.android.synthetic.main.activity_github_repository_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext

class GithubRepositoriesActivity : AppCompatActivity(), KodeinAware, CoroutineScope {

    /////////////////////Inject View Model ////////////////////////////////
    override val kodein by closestKodein()
    private val viewModelFactory: GithupRepositoriesViewModelFactory by instance()
    private lateinit var viewModel: GithupRepositoriesViewModel
    private val githubRepositoriesAdapter = GithubRepositoriesAdapter()

    //////////////////////Generating Courtine Scope ///////////////////////////
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private lateinit var job: Job

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_repository_layout)
        job = Job()
        linearLayoutManager = LinearLayoutManager(this@GithubRepositoriesActivity)
        setupLayout()
        setupViewModel()
        getGithubRepositories()
        observeForNetworkStatus()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(GithupRepositoriesViewModel::class.java)
    }

    private fun setupLayout() {

        /////////////////////Setup Recycler View For Tags Layout ///////////////////////////////////////////////////
        recyclerview_github_repositories.apply {
            layoutManager = linearLayoutManager
            adapter = githubRepositoriesAdapter
        }
    }


    private fun getGithubRepositories() = launch {
        viewModel.getGithubRepositoryElements().observe(this@GithubRepositoriesActivity, Observer {
            githubRepositoriesAdapter.submitList(it)
        })
    }

    private fun observeForNetworkStatus() {
        viewModel.getNetworkState().observe(this@GithubRepositoriesActivity, Observer {
            githubRepositoriesAdapter.setNetworkState(it)
            if (it.state == Status.FAILED) {
                displaySnack(parent_constraint_layout, it.msg)
            }
        })
    }

    private fun displaySnack(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()

    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}