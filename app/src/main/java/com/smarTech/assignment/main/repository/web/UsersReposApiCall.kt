package com.smarTech.assignment.main.repository.web

import com.smarTech.assignment.constants.AppConstants.PAGE_SIZE
import com.smarTech.assignment.main.model.GitHubRepo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersReposApiCall {

    @GET("/users/{user}/repos")
    fun getUserRepos(
        @Path("user") githubUserName: String = "JakeWharton",
        @Query("page") pageNumber: Int,
        @Query("per_page") itemsPerPage: Int = PAGE_SIZE
    ): Call<ArrayList<GitHubRepo>>

}