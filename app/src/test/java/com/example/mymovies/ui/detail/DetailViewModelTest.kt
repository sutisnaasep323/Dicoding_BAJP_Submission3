package com.example.mymovies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.mymovies.data.MovieAppRepository
import com.example.mymovies.data.source.local.entity.MovieEntity
import com.example.mymovies.utils.DataDummy
import com.example.mymovies.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var detailViewModel: DetailViewModel

    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val movieId = dummyMovie.id
    private val dummyTvShow = DataDummy.generateDummyTvShows()[0]
    private val tvShowId = dummyTvShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieAppRepository: MovieAppRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<MovieEntity>>

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(movieAppRepository)
        detailViewModel.selectedMovieId(movieId)
        detailViewModel.selectedTvShowId(tvShowId)
    }

    @Test
    fun getMovieDetail() {
        val movieDetail = Resource.success(dummyMovie)
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = movieDetail
        Mockito.`when`(movieAppRepository.getMovieById(movieId)).thenReturn(movie)
        detailViewModel.movieDetail.observeForever(movieObserver)
        Mockito.verify(movieObserver).onChanged(movieDetail)
    }

    @Test
    fun getTvShowDetail() {
        val tvShowDetail = Resource.success(dummyTvShow)
        val tvShow = MutableLiveData<Resource<MovieEntity>>()
        tvShow.value = tvShowDetail
        Mockito.`when`(movieAppRepository.getTvShowById(tvShowId)).thenReturn(tvShow)
        detailViewModel.tvShowDetail.observeForever(tvShowObserver)
        Mockito.verify(tvShowObserver).onChanged(tvShowDetail)
    }

}