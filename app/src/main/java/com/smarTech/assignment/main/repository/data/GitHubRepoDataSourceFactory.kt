package com.smarTech.assignment.main.repository.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.smarTech.assignment.main.model.GitHubRepo

class GitHubRepoDataSourceFactory : DataSource.Factory<Int, GitHubRepo>() {

    val sourceLiveData = MutableLiveData<GitHubRepoDataSource>()
    lateinit var latestSource: GitHubRepoDataSource

    override fun create(): DataSource<Int, GitHubRepo> {
        latestSource = GitHubRepoDataSource()
        sourceLiveData.postValue(latestSource)
        return latestSource
    }

}