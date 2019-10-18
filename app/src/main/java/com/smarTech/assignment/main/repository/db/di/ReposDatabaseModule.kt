package com.smarTech.assignment.main.repository.db.di

import android.app.Application
import com.smarTech.assignment.application.ReposApplication
import com.smarTech.assignment.main.repository.db.ReposDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Scope
import javax.inject.Singleton


@Module
class ReposDatabaseModule {

    @Inject
    lateinit var application: Application

    init {
        ReposApplication.appComponent.inject(this)
    }

    @Provides
    fun providesReposDB(): ReposDatabase {
        return ReposDatabase.create(application)
    }
}