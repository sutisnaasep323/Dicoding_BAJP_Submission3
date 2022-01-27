package com.example.mymovies.ui.favorite.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R
import com.example.mymovies.data.source.local.entity.MovieEntity
import com.example.mymovies.databinding.FragmentMoviesFavoriteBinding
import com.example.mymovies.ui.favorite.FavoriteViewModel
import com.example.mymovies.utils.SortUtils
import com.example.mymovies.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class MoviesFragmentFavorite : Fragment() {
    private var _fragmentFavoriteMoviesBinding: FragmentMoviesFavoriteBinding? = null
    private val binding get() = _fragmentFavoriteMoviesBinding!!

    private lateinit var moviesAdapter: FavoriteMoviesAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentFavoriteMoviesBinding = FragmentMoviesFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding.rvMoviesFavorite)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        moviesAdapter = FavoriteMoviesAdapter()

        binding.progressBar.visibility = View.VISIBLE
        binding.notFound.visibility = View.GONE
        setList(SortUtils.RANDOM)

        with(binding.rvMoviesFavorite) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = moviesAdapter
        }

        binding.progressBar.visibility = View.VISIBLE
        binding.random.setOnClickListener { setList(SortUtils.RANDOM) }
        binding.newest.setOnClickListener { setList(SortUtils.NEWEST) }
        binding.popularity.setOnClickListener { setList(SortUtils.POPULARITY) }
        binding.vote.setOnClickListener { setList(SortUtils.VOTE) }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieEntity = moviesAdapter.getSwipedData(swipedPosition)
                movieEntity?.let {viewModel.setFavorite(it) }
                val snackbar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { _ ->
                    movieEntity?.let { viewModel.setFavorite(it) }
                }
                snackbar.show()
            }
        }
    })

    private fun setList(sort: String) {
        viewModel.getFavoriteMovies(sort).observe(viewLifecycleOwner, moviesObserver)
    }

    private val moviesObserver = Observer<PagedList<MovieEntity>> { movies ->
        if (movies.isNotEmpty()) {
            binding.progressBar.visibility = View.GONE
            binding.notFound.visibility = View.GONE
            moviesAdapter.submitList(movies)
            moviesAdapter.notifyDataSetChanged()
        } else {
            binding.progressBar.visibility = View.GONE
            binding.notFound.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentFavoriteMoviesBinding = null
    }
}