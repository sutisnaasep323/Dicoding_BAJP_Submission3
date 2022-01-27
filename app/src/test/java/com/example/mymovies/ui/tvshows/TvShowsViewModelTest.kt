package com.example.mytvShow.ui.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.mymovies.data.MovieAppRepository
import com.example.mymovies.data.source.local.entity.MovieEntity
import com.example.mymovies.ui.tvshows.TvShowsViewModel
import com.example.mymovies.utils.DataDummy
import com.example.mymovies.utils.SortUtils
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
class TvShowsViewModelTest{
    private lateinit var viewModel: TvShowsViewModel
    private val sort = SortUtils.RANDOM

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieAppRepository: MovieAppRepository

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Before
    fun setUp() {
        viewModel = TvShowsViewModel(movieAppRepository)
    }

    @Test
    fun getMovies() {
        val dummyTvShows = Resource.success(pagedList)
        Mockito.`when`(dummyTvShows.data?.size).thenReturn(3)
        val tvShow = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        tvShow.value = dummyTvShows

        Mockito.`when`(movieAppRepository.getAllTvShows(sort)).thenReturn(tvShow)
        val tvShowsEntities = viewModel.getTvShows(sort).value?.data
        verify(movieAppRepository).getAllTvShows(sort)
        assertNotNull(tvShowsEntities)
        assertEquals(3, tvShowsEntities?.size)

        viewModel.getTvShows(sort).observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }
}