package com.smarTech.assignment.main.repository.db

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.smarTech.assignment.main.model.GitHubRepo
import com.smarTech.assignment.main.repository.web.UsersReposApiCall
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitHubReposBoundaryCallBack(
    val db: ReposDatabase,
    private val api: UsersReposApiCall
) :
    PagedList.BoundaryCallback<GitHubRepo>() {

    private var lastLoadedPage = 1
    private var lastItemAtEnd: GitHubRepo? = null
    val isLoading = MutableLiveData<Boolean>()
    val hasError = MutableLiveData<Boolean>()

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        loadRepos(1)
    }

    override fun onItemAtEndLoaded(itemAtEnd: GitHubRepo) {
        super.onItemAtEndLoaded(itemAtEnd)
        lastItemAtEnd = itemAtEnd
        lastLoadedPage += 1
        loadRepos(lastLoadedPage)
    }

    private fun loadRepos(pageNumber: Int) {
        api.getUserRepos(pageNumber = pageNumber).enqueue(getApiCallback())
    }

    private fun getApiCallback(): Callback<ArrayList<GitHubRepo>> {
        isLoading.postValue(true)
        return object : Callback<ArrayList<GitHubRepo>> {

            override fun onResponse(
                call: Call<ArrayList<GitHubRepo>>,
                response: Response<ArrayList<GitHubRepo>>
            ) {
                isLoading.postValue(false)
                if (response.isSuccessful) {
                    saveResponse(response.body())
                    hasError.postValue(false)
                } else {
                    hasError.postValue(true)
                }
            }

            override fun onFailure(call: Call<ArrayList<GitHubRepo>>, t: Throwable) {
                isLoading.postValue(false)
                hasError.postValue(true)
            }
        }
    }

    private fun saveResponse(apiResponse: ArrayList<GitHubRepo>?) {
        doAsync {
            apiResponse?.let { repos ->
                if (repos.isNotEmpty()) {
                    db.reposDao().insert(repos)
                }
            }
        }
    }

    fun retryLoadingRepos() {
        lastItemAtEnd?.let { itemAtEnd -> onItemAtEndLoaded(itemAtEnd) }
    }
}

