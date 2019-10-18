package com.smarTech.assignment.main.repository.db.di

import com.smarTech.assignment.main.viewModel.MainActivityViewModel
import dagger.Component

@Component(modules = [ReposDatabaseModule::class])
interface ReposDatabaseComponent {

    fun inject(mainActivityViewModel: MainActivityViewModel)
}