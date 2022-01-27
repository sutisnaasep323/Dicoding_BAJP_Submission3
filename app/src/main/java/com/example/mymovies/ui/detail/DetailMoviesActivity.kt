package com.example.mymovies.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mymovies.R
import com.example.mymovies.data.source.local.entity.MovieEntity
import com.example.mymovies.databinding.ActivityDetailMoviesBinding
import com.example.mymovies.viewmodel.ViewModelFactory
import com.example.mymovies.vo.Resource
import com.example.mymovies.vo.Status

class DetailMoviesActivity : AppCompatActivity() {

    private lateinit var detailMoviesBinding: ActivityDetailMoviesBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailMoviesBinding = ActivityDetailMoviesBinding.inflate(layoutInflater)

        setContentView(detailMoviesBinding.root)

        detailMoviesBinding.backButton.setOnClickListener { onBackPressed() }

        window.statusBarColor = ContextCompat.getColor(this, R.color.purple_700)
        val type = intent.getIntExtra(EXTRA_TYPE, -1)
        val enumType: DetailType = DetailType.values()[type]
        val id = intent.getIntExtra(EXTRA_ID, -1)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        detailMoviesBinding.progressBar.visibility = View.VISIBLE
        detailMoviesBinding.nestedScrollView.visibility = View.GONE
        detailMoviesBinding.collapsingToolbar.visibility = View.GONE
        detailMoviesBinding.favoriteButton.setOnClickListener {
            when (enumType) {
                DetailType.MOVIES -> {
                    viewModel.setFavoriteMovie()
                }
                DetailType.TV_SHOWS -> {
                    viewModel.setFavoriteTvShow()
                }
            }
        }

        when (enumType) {
            DetailType.MOVIES -> {
                viewModel.selectedMovieId(id)
                viewModel.movieDetail.observe(this, { movie ->
                    if (movie != null) {
                        showDetail(movie)
                    }
                })
            }
            DetailType.TV_SHOWS -> {
                viewModel.selectedTvShowId(id)
                viewModel.tvShowDetail.observe(this, { tvShow ->
                    if (tvShow != null) {
                        showDetail(tvShow)
                    }
                })
            }
        }

    }

    private fun showDetail(movie: Resource<MovieEntity>) {
        when (movie.status) {
            Status.LOADING -> detailMoviesBinding.progressBar.visibility = View.VISIBLE
            Status.SUCCESS -> if (movie.data != null) {
                detailMoviesBinding.progressBar.visibility = View.GONE
                detailMoviesBinding.nestedScrollView.visibility = View.VISIBLE
                detailMoviesBinding.collapsingToolbar.visibility = View.VISIBLE

                val state = movie.data.favorite

                setFavoriteState(state)
                populateMovieDetail(movie.data)
            }
            Status.ERROR -> {
                detailMoviesBinding.progressBar.visibility = View.GONE
                Toast.makeText(
                    this,
                    "Terjadi kesalahan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setFavoriteState(state: Boolean) {
        if (state) {
            detailMoviesBinding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite
                )
            )
        } else {
            detailMoviesBinding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_border
                )
            )
        }
    }

    private fun populateMovieDetail(movie: MovieEntity) {
        with(detailMoviesBinding) {
            titleDetail.text = movie.title
            releaseDetail.text = movie.releaseDate
            overviewDetail.text = movie.overview
            popularityDetail.text = movie.popularity.toString()
            voteDetail.text = movie.voteAverage.toString()
            languageDetail.text = movie.originalLanguage

            Glide.with(this@DetailMoviesActivity)
                .load("https://image.tmdb.org/t/p/original/" + movie.posterPath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                .into(detailMoviesBinding.posterDetail)
        }
    }

    companion object {
        const val EXTRA_TYPE = "extraType"
        const val EXTRA_ID = "extraId"
    }
}

