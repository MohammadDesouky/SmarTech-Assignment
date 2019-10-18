package com.smarTech.assignment.main.repository

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.smarTech.assignment.main.repository.db.ReposDatabase
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class GitHubReposRepositoryTest {

    private lateinit var db: ReposDatabase
    private lateinit var repository: GitHubReposRepository
    private val LATCH_TIMEOUT_IN_SECONDS = 15L

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        db = ReposDatabase.create(context)
        db.clearAllTables()
        repository = GitHubReposRepository(db)
    }

    @Test
    fun testLoadingRepos() {
        var hasRepos = false
        val latch = CountDownLatch(1)
        repository.observableRepos.observeForever { repos ->
            if (repos?.isNotEmpty() == true) {
                hasRepos = true
                repository.observableRepos.removeObserver { this }
                latch.countDown()
            }
        }
        repository.invalidateData()
        latch.await(LATCH_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        assertTrue(hasRepos)
    }

    @After
    fun tearDown() {
        db.clearAllTables()
    }
}