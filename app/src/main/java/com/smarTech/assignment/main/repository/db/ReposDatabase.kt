package com.smarTech.assignment.main.repository.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.smarTech.assignment.application.ReposApplication
import com.smarTech.assignment.main.model.GitHubRepo
import com.smarTech.assignment.main.repository.db.dao.ReposDao
import javax.inject.Inject

@Database(
    entities = [GitHubRepo::class],
    version = 1,
    exportSchema = false
)
abstract class ReposDatabase : RoomDatabase() {

    companion object {

        private const val DB_NAME = "JackRepos.db"

        fun create(application: Application): ReposDatabase {
            return Room.databaseBuilder(application, ReposDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun reposDao(): ReposDao
}