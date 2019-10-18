package com.smarTech.assignment.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.smarTech.assignment.main.model.GitHubRepo
import com.smarTech.assignment.main.repository.GitHubReposRepository
import com.smarTech.assignment.main.repository.db.ReposDatabase
import com.smarTech.assignment.main.repository.db.di.DaggerReposDatabaseComponent
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {

    @Inject
    lateinit var db: ReposDatabase
    var repository: GitHubReposRepository
    var observableRepos: LiveData<PagedList<GitHubRepo>>
    var observableHasError: LiveData<Boolean>
    var observableIsLoading: LiveData<Boolean>

    init {
        DaggerReposDatabaseComponent.create().inject(this)
        repository = GitHubReposRepository(db)
        observableRepos = repository.observableRepos
        observableHasError = repository.observableHasError
        observableIsLoading = repository.observableIsLoading
    }

    fun clearCache() {
        repository.invalidateData()
    }

    fun retryLoadingRepos() {
        repository.retryLoadingRepos()
    }

}