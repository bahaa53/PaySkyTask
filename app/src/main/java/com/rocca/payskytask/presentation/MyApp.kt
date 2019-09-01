package com.rocca.payskytask.presentation

import android.app.Application
import com.rocca.payskytask.data.api.ApiClient
import com.rocca.payskytask.data.api.checkingConnectivity.ConnectivityInterceptor
import com.rocca.payskytask.data.api.checkingConnectivity.ConnectivityInterceptorImpl
import com.rocca.payskytask.data.database.PaySkyDatabase
import com.rocca.payskytask.data.repositories.GithubElementsRepositoriesImpl
import com.rocca.payskytask.domain.repository.GithubElementsRepositories
import com.rocca.payskytask.domain.usecase.GithubRepositoryUseCase
import com.rocca.payskytask.data.api.githubRepositoriesNetworkDataSource.GithubRepoBoundaryCallback
import com.rocca.payskytask.presentation.GithubRepositories.viewmodel.GithupRepositoriesViewModelFactory
import com.rocca.payskytask.util.PagingRequestHelper


import org.kodein.di.Kodein.Companion.lazy
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import java.util.concurrent.Executors

class MyApp : Application(), KodeinAware {

    override val kodein = lazy {
        import(androidXModule(this@MyApp))

        ////////////////////////////// Inject Database + its Daos //////////////////
        bind() from singleton { PaySkyDatabase(instance()) }
        bind() from provider { instance<PaySkyDatabase>().GithubRepositoriesDao() }
        ///////////////////////////////////////////////////////////////////////
        //////////Inject Api Interfaces To Access EndPoints /////////////////
        bind<ConnectivityInterceptor>() with provider { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiClient(instance()) }
        /////////////////////////////////////////////////////////////////
        ////////////Inject PagingRequestHelper /////////////////////////////////////
        bind() from provider { Executors.newSingleThreadExecutor() }
        bind() from provider { PagingRequestHelper(instance()) }
        ////////////////////////////////////////////////////////////////////////////
        ///////////Inject Github Boundary Callback////////////////////////////////////
        bind() from provider { GithubRepoBoundaryCallback(instance(), instance(),instance()) }

        ///////////////////////////////////Inject Github Repository //////////////////////////
        bind<GithubElementsRepositories>() with provider { GithubElementsRepositoriesImpl(instance(), instance()) }
        //////////////////////////////////////////////////////////////////////////
        /////////////////////////Inject Github repository Items USE CASE /////////////////////////////
        bind() from provider { GithubRepositoryUseCase(instance()) }
        /////////////////////////////////////////////////////////////////////////
        ///////////////Inject Tags Viewmodel Factory ////////////////////////////
        bind() from provider {
            GithupRepositoriesViewModelFactory(
                    instance()
            )
        }
        ////////////////////////////////////////////////////////////////////////

    }

    override fun onCreate() {
        super.onCreate()
    }
}