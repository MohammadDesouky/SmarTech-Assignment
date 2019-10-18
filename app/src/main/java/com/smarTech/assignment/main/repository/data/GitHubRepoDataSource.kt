package com.smarTech.assignment.main.repository.data

import androidx.paging.PageKeyedDataSource
import com.smarTech.assignment.main.model.GitHubRepo
import com.smarTech.assignment.main.repository.web.UsersReposApiCall
import com.smarTech.assignment.utils.RetrofitCreator
import java.io.IOException

class GitHubRepoDataSource : PageKeyedDataSource<Int, GitHubRepo>() {

    private val api = RetrofitCreator.new<UsersReposApiCall>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GitHubRepo>
    ) {
        try {
            val response = api.getUserRepos(pageNumber = 1).execute()
            val items = response.body() ?: arrayListOf()
            callback.onResult(items, 0, 2)
        } catch (ioException: IOException) {
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GitHubRepo>) {
        try {
            val response = api.getUserRepos(pageNumber = params.key).execute()
            val items = response.body() ?: arrayListOf()
            callback.onResult(items, params.key + 1)
        } catch (ioException: IOException) {
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GitHubRepo>) {

    }
}