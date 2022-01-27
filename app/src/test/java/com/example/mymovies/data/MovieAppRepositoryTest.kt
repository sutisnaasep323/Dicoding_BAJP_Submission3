package com.example.mymovies.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mymovies.data.source.local.LocalDataSource
import com.example.mymovies.data.source.local.entity.MovieEntity
import com.example.mymovies.data.source.remote.RemoteDataSource
import com.example.mymovies.ui.utils.PagedListUtil
import com.example.mymovies.utils.AppExecutors
import com.example.mymovies.utils.DataDummy
import com.example.mymovies.utils.LiveDataTestUtil
import com.example.mymovies.utils.SortUtils
import com.example.mymovies.vo.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MovieAppRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)

    private val movieAppRepository = FakeMovieRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.generateRemoteDummyMovies()
    private val movieId = movieResponses[0].id
    private val tvShowResponses = DataDummy.generateRemoteDummyTvShows()
    private val tvShowId = tvShowResponses[0].id

    private val random = SortUtils.RANDOM

    @Test
    fun getAllMovies() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getAllMovies(random)).thenReturn(dataSourceFactory)
        movieAppRepository.getAllMovies(random)

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllMovies(random)
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTvShows() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getAllTvShows(random)).thenReturn(dataSourceFactory)
        movieAppRepository.getAllTvShows(random)

        val tvShowEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShows()))
        verify(local).getAllTvShows(random)
        assertNotNull(tvShowEntities.data)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun getMovieById() {
        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = DataDummy.generateDummyMovies()[0]
        Mockito.`when`(local.getMovie(movieId)).thenReturn(dummyMovie)

        val movieEntity = LiveDataTestUtil.getValue(movieAppRepository.getMovieById(movieId)).data
        verify(local).getMovie(movieId)
        val movieResponse = movieResponses[0]
        assertNotNull(movieEntity)
        if (movieEntity != null) {
            assertEquals(movieResponse.title, movieEntity.title)
            assertEquals(movieResponse.releaseDate, movieEntity.releaseDate)
            assertEquals(movieResponse.overview, movieEntity.overview)
            assertEquals(movieResponse.originalLanguage, movieEntity.originalLanguage)
            assertEquals(movieResponse.id, movieEntity.id)
            assertEquals(movieResponse.popularity, movieEntity.popularity, movieEntity.popularity)
            assertEquals(
                movieResponse.voteAverage,
                movieResponse.voteAverage,
                movieResponse.voteAverage
            )
            assertEquals(movieResponse.posterPath, movieEntity.posterPath)
        }
    }

    @Test
    fun getTvShowById() {
        val dummyTvShow = MutableLiveData<MovieEntity>()
        dummyTvShow.value = DataDummy.generateDummyTvShows()[0]
        Mockito.`when`(local.getTvShow(tvShowId)).thenReturn(dummyTvShow)

        val tvShowEntity =
            LiveDataTestUtil.getValue(movieAppRepository.getTvShowById(tvShowId)).data
        verify(local).getTvShow(tvShowId)
        val tvShowResponse = tvShowResponses[0]
        assertNotNull(tvShowEntity)
        if (tvShowEntity != null) {
            assertEquals(tvShowResponse.name, tvShowEntity.title)
            assertEquals(tvShowResponse.firstAirDate, tvShowEntity.releaseDate)
            assertEquals(tvShowResponse.overview, tvShowEntity.overview)
            assertEquals(tvShowResponse.originalLanguage, tvShowEntity.originalLanguage)
            assertEquals(tvShowResponse.id, tvShowEntity.id)
            assertEquals(
                tvShowResponse.popularity,
                tvShowEntity.popularity,
                tvShowEntity.popularity
            )
            assertEquals(
                tvShowResponse.voteAverage,
                tvShowResponse.voteAverage,
                tvShowResponse.voteAverage
            )
            assertEquals(tvShowResponse.posterPath, tvShowEntity.posterPath)
        }
    }

    @Test
    fun getFavoriteMovies() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getAllFavoriteMovies(random)).thenReturn(dataSourceFactory)
        movieAppRepository.getFavoriteMovies(random)

        val favoriteMovieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllFavoriteMovies(random)
        assertNotNull(favoriteMovieEntities)
        assertEquals(movieResponses.size.toLong(), favoriteMovieEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTvShows() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getAllFavoriteTvShows(random)).thenReturn(dataSourceFactory)
        movieAppRepository.getFavoriteTvShows(random)

        val favoriteTvShowEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShows()))
        verify(local).getAllFavoriteTvShows(random)
        assertNotNull(favoriteTvShowEntities)
        assertEquals(tvShowResponses.size.toLong(), favoriteTvShowEntities.data?.size?.toLong())
    }
}