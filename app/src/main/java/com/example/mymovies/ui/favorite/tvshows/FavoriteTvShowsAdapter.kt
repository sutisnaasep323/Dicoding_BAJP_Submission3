package com.example.mymovies.ui.favorite.tvshows

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.mymovies.data.source.local.entity.MovieEntity
import com.example.mymovies.databinding.ItemsListBinding
import com.example.mymovies.ui.detail.DetailMoviesActivity
import com.example.mymovies.ui.detail.DetailType

class FavoriteTvShowsAdapter :
    PagedListAdapter<MovieEntity, FavoriteTvShowsAdapter.TvShowsViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsViewHolder {
        val itemListBinding =
            ItemsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowsViewHolder(itemListBinding)
    }

    override fun onBindViewHolder(holder: FavoriteTvShowsAdapter.TvShowsViewHolder, position: Int) {
        val tvShowEntity = getItem(position)
        if (tvShowEntity != null) {
            holder.bind(tvShowEntity)
        }
    }

    fun getSwipedData(swipedPosition: Int): MovieEntity? = getItem(swipedPosition)

    inner class TvShowsViewHolder(private val binding: ItemsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieEntity: MovieEntity) {
            with(binding) {
                title.text = movieEntity.title
                release.text = movieEntity.releaseDate
                language.text = movieEntity.originalLanguage
                popularity.text = movieEntity.popularity.toString()
                overview.text = movieEntity.overview
                vote.text = movieEntity.voteAverage.toString()
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original/" + movieEntity.posterPath)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }
                    })
                    .into(poster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMoviesActivity::class.java).apply {
                        putExtra(DetailMoviesActivity.EXTRA_TYPE, DetailType.TV_SHOWS.ordinal)
                        putExtra(DetailMoviesActivity.EXTRA_ID, movieEntity.id)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}