package com.smarTech.assignment.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.smarTech.assignment.main.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.content.IntentFilter
import android.view.View.*
import com.smarTech.assignment.R
import com.smarTech.assignment.broadcastReceivers.ConnectionState
import com.smarTech.assignment.broadcastReceivers.ConnectionStateBroadCastReceiver
import com.google.android.material.snackbar.Snackbar
import com.smarTech.assignment.main.model.GitHubRepo
import com.smarTech.assignment.main.repository.db.ReposDatabase
import com.smarTech.assignment.main.view.reposRecycler.GitHubReposPagedAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Thread.sleep


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: GitHubReposPagedAdapter
    private var isFirstLoad = true
    private val connectionStateReceiver = ConnectionStateBroadCastReceiver()
    private lateinit var offlineSnackBar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = getViewModel()
        updateReposAdapter()
        observeReposChanges()
        observeLoadingErrors()
        observeLoading()
        observeConnectionStateChanges()
        registerConnectionStateReceiver()
        createSnackBar()
        refreshConnectionState()
    }

    private fun observeLoading() {
        viewModel.observableIsLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                showReposLoader()
            } else {
                hideReposLoader()
            }
        })
    }

    private fun observeLoadingErrors() {
        viewModel.observableHasError.observe(this, Observer { hasError ->
            if (hasError) {
                showLoadingError()
            } else {
                hideLoadingError()
            }
        })
    }

    private fun createSnackBar() {
        offlineSnackBar =
            Snackbar.make(
                coordinatorLayout,
                getString(R.string.no_up_to_date),
                Snackbar.LENGTH_INDEFINITE
            )
    }

    override fun onStart() {
        super.onStart()
        doAsync {
            sleep(5000)
            connectionStateReceiver.refreshState(this@MainActivity)
        }
    }

    private fun observeConnectionStateChanges() {
        ConnectionStateBroadCastReceiver.connectionState.observe(this, Observer { connectionState ->
            if (connectionState == ConnectionState.Offline) {
                onGoingOffline()
            } else {
                onBeingOnline()
            }
        })
    }

    private fun onBeingOnline() {
        if (offlineSnackBar.isShown) {
            offlineSnackBar.dismiss()
        }
        if (!alreadyHaveData()) {
            isFirstLoad = true
            showReposLoader()
            showRepos()
        }
    }

    private fun retryLoadingRepos() {
        viewModel.retryLoadingRepos()
    }

    private fun onGoingOffline() {
        if (alreadyHaveData()) {
            showOfflineSnackBar()
        } else {
            showOfflineMessage()
        }
    }

    private fun showOfflineMessage() {
        reposRecyclerView?.visibility = GONE
        extrasContainer?.visibility = VISIBLE
        offlineMessageContainer?.visibility = VISIBLE
        progressBar.visibility = GONE
        retryContainer.visibility = GONE
    }

    private fun showOfflineSnackBar() {
        offlineSnackBar.show()
    }

    private fun alreadyHaveData(): Boolean {
        return adapter.itemCount > 0
    }

    private fun registerConnectionStateReceiver() {
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(connectionStateReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connectionStateReceiver)
    }

    private fun refreshConnectionState() {
        doAsync {
            sleep(1000)
            uiThread {
                connectionStateReceiver.refreshState(this@MainActivity)
            }
        }
    }

    private fun observeReposChanges() {
        viewModel.observableRepos.observe(this, Observer { reposPagedList ->
            if (reposPagedList != null) {
                adapter.submitList(reposPagedList)
                try {
                    if (isFirstLoad) {
                        showRepos()
                        isFirstLoad = false
                    }
                } catch (ignored: Exception) {
                }
            } else {
                showLoadingError()
            }
        })
    }

    private fun updateReposAdapter() {
        adapter = GitHubReposPagedAdapter()
        reposRecyclerView.adapter = adapter
    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this,ViewModelProvider.NewInstanceFactory()).get(MainActivityViewModel::class.java)
    }

    private fun showReposLoader() {
        offlineMessageContainer?.visibility = GONE
        extrasContainer?.visibility = VISIBLE
        progressBar.visibility = VISIBLE
        retryContainer?.visibility = GONE
    }

    private fun hideReposLoader() {
        offlineMessageContainer?.visibility = GONE
        extrasContainer?.visibility = GONE
        progressBar.visibility = GONE
        retryContainer?.visibility = GONE
    }

    private fun showLoadingError() {
        offlineMessageContainer?.visibility = GONE
        progressBar?.visibility = GONE
        retryContainer?.visibility = VISIBLE
        extrasContainer?.visibility = VISIBLE
        retryButton?.setOnClickListener {
            retryLoadingRepos()
        }
    }


    private fun hideLoadingError() {
        retryContainer?.visibility = GONE
        extrasContainer?.visibility = GONE
    }

    private fun showRepos() {
        reposRecyclerView?.visibility = VISIBLE
    }
}