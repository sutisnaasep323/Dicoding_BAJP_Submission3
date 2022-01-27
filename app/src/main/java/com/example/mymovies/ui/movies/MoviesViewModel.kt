package com.example.mymovies.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mymovies.data.MovieAppRepository
import com.example.mymovies.data.source.local.entity.MovieEntity
import com.example.mymovies.vo.Resource

class MoviesViewModel(private val movieAppRepository: MovieAppRepository) : ViewModel() {

    fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> =
        movieAppRepository.getAllMovies(sort)

}