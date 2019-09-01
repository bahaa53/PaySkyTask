package com.rocca.payskytask.data.api.networkStatus

data class NetworkState(val state: Status, val msg: String) {

    companion object {
        val LOADED = NetworkState(Status.SUCCESS, "Success")
        val LOADING = NetworkState(Status.RUNNING, "Running")
    }
}