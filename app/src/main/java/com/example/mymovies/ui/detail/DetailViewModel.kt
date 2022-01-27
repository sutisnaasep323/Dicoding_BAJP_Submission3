package com.example.mymovies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mymovies.data.MovieAppRepository
import com.example.mymovies.data.source.local.entity.MovieEntity
import com.example.mymovies.vo.Resource

class DetailViewModel(private val movieAppRepository: MovieAppRepository) : ViewModel() {

    private val movieId = MutableLiveData<Int>()
    private val tvShowId = MutableLiveData<Int>()

    fun selectedMovieId(movieId: Int) {
        this.movieId.value = movieId
    }

    fun selectedTvShowId(tvShowId: Int) {
        this.tvShowId.value = tvShowId
    }

    var movieDetail: LiveData<Resource<MovieEntity>> =
        Transformations.switchMap(movieId) { mMovieId ->
            movieAppRepository.getMovieById(mMovieId)
        }

    var tvShowDetail: LiveData<Resource<MovieEntity>> =
        Transformations.switchMap(tvShowId) { mTvShowId ->
            movieAppRepository.getTvShowById(mTvShowId)
        }

    fun setFavoriteMovie() {
        val movieResource = movieDetail.value?.data
        if (movieResource != null) {
            val newState = !movieResource.favorite
            movieAppRepository.setCourseFavorite(movieResource, newState)
        }
    }

    fun setFavoriteTvShow() {
        val tvShowResource = tvShowDetail.value?.data
        if (tvShowResource != null) {
            val newState = !tvShowResource.favorite
            movieAppRepository.setCourseFavorite(tvShowResource, newState)
        }
    }
}