package com.smarTech.assignment.application

import android.app.Application
import com.smarTech.assignment.application.di.AppComponent
import com.smarTech.assignment.application.di.AppModule
import com.smarTech.assignment.application.di.DaggerAppComponent

class ReposApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }
}