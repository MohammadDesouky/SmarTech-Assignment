package com.smarTech.assignment.main.repository.db.dao

import androidx.paging.DataSource
import androidx.room.*
import com.smarTech.assignment.main.model.GitHubRepo

@Dao
interface  ReposDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repos : List<GitHubRepo>)

    @Query("SELECT * FROM Repos")
    fun getAllRepos() : DataSource.Factory<Int, GitHubRepo>
}
