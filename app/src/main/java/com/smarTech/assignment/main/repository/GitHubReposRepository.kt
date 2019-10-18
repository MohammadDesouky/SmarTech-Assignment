package com.smarTech.assignment.main.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.smarTech.assignment.constants.AppConstants
import com.smarTech.assignment.main.model.GitHubRepo
import com.smarTech.assignment.main.repository.db.GitHubReposBoundaryCallBack
import com.smarTech.assignment.main.repository.db.ReposDatabase
import com.smarTech.assignment.main.repository.web.UsersReposApiCall
import com.smarTech.assignment.utils.RetrofitCreator
import java.util.concurrent.Executors


class GitHubReposRepository (private val db: ReposDatabase) {

    private val api = RetrofitCreator.new<UsersReposApiCall>()
    private val callback = GitHubReposBoundaryCallBack(db, api)
    val observableRepos: LiveData<PagedList<GitHubRepo>> = db.reposDao().getAllRepos().toLiveData(
        pageSize = AppConstants.PAGE_SIZE,
        boundaryCallback = callback,
        fetchExecutor = Executors.newSingleThreadExecutor()
    )
    val observableHasError = callback.hasError as LiveData<Boolean>
    val observableIsLoading = callback.isLoading as LiveData<Boolean>

    fun invalidateData() {
        db.clearAllTables()
    }

    fun retryLoadingRepos() {
        callback.retryLoadingRepos()
    }
}
