package com.smarTech.assignment.application.di

import com.smarTech.assignment.main.repository.db.di.ReposDatabaseModule
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(reposDatabase: ReposDatabaseModule)
}