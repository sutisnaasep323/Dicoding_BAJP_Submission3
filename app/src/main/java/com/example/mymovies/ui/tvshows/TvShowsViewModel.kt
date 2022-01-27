package com.example.mymovies.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mymovies.data.MovieAppRepository
import com.example.mymovies.data.source.local.entity.MovieEntity
import com.example.mymovies.vo.Resource

class TvShowsViewModel(private val movieAppRepository: MovieAppRepository) : ViewModel() {

    fun getTvShows(sort: String): LiveData<Resource<PagedList<MovieEntity>>> =
        movieAppRepository.getAllTvShows(sort)

}