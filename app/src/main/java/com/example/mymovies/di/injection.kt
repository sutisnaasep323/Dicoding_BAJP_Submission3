package com.example.mymovies.di

import android.content.Context
import com.example.mymovies.data.MovieAppRepository
import com.example.mymovies.data.source.local.LocalDataSource
import com.example.mymovies.data.source.local.room.MovieDatabase
import com.example.mymovies.data.source.remote.RemoteDataSource
import com.example.mymovies.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): MovieAppRepository {

        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieAppRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}