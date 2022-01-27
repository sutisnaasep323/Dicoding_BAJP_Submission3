package com.example.mymovies.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mymovies.data.MovieAppRepository
import com.example.mymovies.data.source.local.entity.MovieEntity

class FavoriteViewModel(private val movieAppRepository: MovieAppRepository) : ViewModel() {

    fun getFavoriteMovies(sort: String): LiveData<PagedList<MovieEntity>> =
        movieAppRepository.getFavoriteMovies(sort)

    fun getFavoriteTvShows(sort: String): LiveData<PagedList<MovieEntity>> =
        movieAppRepository.getFavoriteTvShows(sort)

    fun setFavorite(movieEntity: MovieEntity) {
        val newState = !movieEntity.favorite
        movieAppRepository.setCourseFavorite(movieEntity, newState)
    }
}