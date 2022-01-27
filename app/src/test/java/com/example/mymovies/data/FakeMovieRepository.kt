package com.example.mymovies.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.example.mymovies.data.source.local.LocalDataSource
import com.example.mymovies.data.source.local.entity.MovieEntity
import com.example.mymovies.data.source.remote.ApiResponse
import com.example.mymovies.data.source.remote.RemoteDataSource
import com.example.mymovies.data.source.remote.response.Movie
import com.example.mymovies.data.source.remote.response.TvShow
import com.example.mymovies.utils.AppExecutors
import com.example.mymovies.vo.Resource
import kotlinx.coroutines.Dispatchers

class FakeMovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    MovieAppDataSource {

    override fun getAllMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<Movie>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return Pager(
                    PagingConfig(
                        config.pageSize,
                        config.prefetchDistance,
                        config.enablePlaceholders,
                        config.initialLoadSizeHint,
                        config.maxSize
                    ),
                    this.initialLoadKey,
                    localDataSource.getAllMovies(sort).asPagingSourceFactory(Dispatchers.IO)
                ).liveData.build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<Movie>>> {
                return remoteDataSource.getMovies()
            }

            override fun saveCallResult(data: List<Movie>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        response.overview,
                        response.originalLanguage,
                        response.releaseDate,
                        response.popularity,
                        response.voteAverage,
                        response.id,
                        response.title,
                        response.posterPath,
                        favorite = false,
                        isTvShows = false
                    )
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getAllTvShows(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<TvShow>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return Pager(
                    PagingConfig(
                        config.pageSize,
                        config.prefetchDistance,
                        config.enablePlaceholders,
                        config.initialLoadSizeHint,
                        config.maxSize
                    ),
                    this.initialLoadKey,
                    localDataSource.getAllTvShows(sort).asPagingSourceFactory(Dispatchers.IO)
                ).liveData.build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<TvShow>>> {
                return remoteDataSource.getTvShows()
            }

            override fun saveCallResult(data: List<TvShow>) {
                val tvShowList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        response.overview,
                        response.originalLanguage,
                        response.firstAirDate,
                        response.popularity,
                        response.voteAverage,
                        response.id,
                        response.name,
                        response.posterPath,
                        favorite = false,
                        isTvShows = true
                    )
                    tvShowList.add(movie)
                }
                localDataSource.insertMovies(tvShowList)
            }
        }.asLiveData()
    }

    override fun getMovieById(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, List<Movie>>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> {
                return localDataSource.getMovie(movieId)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<List<Movie>>> {
                return remoteDataSource.getMovies()
            }

            override fun saveCallResult(data: List<Movie>) {
                lateinit var movieEntity: MovieEntity
                for (movie in data) {
                    if (movieId == movie.id) {
                        movieEntity = MovieEntity(
                            movie.overview,
                            movie.originalLanguage,
                            movie.releaseDate,
                            movie.popularity,
                            movie.voteAverage,
                            movie.id,
                            movie.title,
                            movie.posterPath,
                            favorite = false,
                            isTvShows = false
                        )
                    }
                }
                localDataSource.updateMovie(movieEntity)
            }
        }.asLiveData()
    }

    override fun getTvShowById(tvShowId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, List<TvShow>>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> {
                return localDataSource.getTvShow(tvShowId)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<List<TvShow>>> {
                return remoteDataSource.getTvShows()
            }

            override fun saveCallResult(data: List<TvShow>) {
                lateinit var movieEntity: MovieEntity
                for (movie in data) {
                    if (tvShowId == movie.id) {
                        movieEntity = MovieEntity(
                            movie.overview,
                            movie.originalLanguage,
                            movie.firstAirDate,
                            movie.popularity,
                            movie.voteAverage,
                            movie.id,
                            movie.name,
                            movie.posterPath,
                            favorite = false,
                            isTvShows = true
                        )
                    }
                }
                localDataSource.updateMovie(movieEntity)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovies(sort: String): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return Pager(
            PagingConfig(
                config.pageSize,
                config.prefetchDistance,
                config.enablePlaceholders,
                config.initialLoadSizeHint,
                config.maxSize
            ),
            this.initialLoadKey,
            localDataSource.getAllFavoriteMovies(sort).asPagingSourceFactory(Dispatchers.IO)
        ).liveData.build()
    }

    override fun getFavoriteTvShows(sort: String): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return Pager(
            PagingConfig(
                config.pageSize,
                config.prefetchDistance,
                config.enablePlaceholders,
                config.initialLoadSizeHint,
                config.maxSize
            ),
            this.initialLoadKey,
            localDataSource.getAllFavoriteTvShows(sort).asPagingSourceFactory(Dispatchers.IO)
        ).liveData.build()
    }

    override fun setCourseFavorite(movie: MovieEntity, state: Boolean) {
        return appExecutors.diskIO().execute { localDataSource.setMovieFavorite(movie, state) }
    }
}