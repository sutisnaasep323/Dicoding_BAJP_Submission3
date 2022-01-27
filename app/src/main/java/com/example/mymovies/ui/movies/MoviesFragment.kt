package com.example.mymovies.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovies.data.source.local.entity.MovieEntity
import com.example.mymovies.databinding.FragmentMoviesBinding
import com.example.mymovies.utils.SortUtils
import com.example.mymovies.viewmodel.ViewModelFactory
import com.example.mymovies.vo.Resource
import com.example.mymovies.vo.Status

class MoviesFragment : Fragment() {

    private var _fragmentMoviesBinding: FragmentMoviesBinding? = null
    private val fragmentMoviesBinding get() = _fragmentMoviesBinding!!

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentMoviesBinding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return fragmentMoviesBinding.root
    }

    private lateinit var viewModel: MoviesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[MoviesViewModel::class.java]

        moviesAdapter = MoviesAdapter()
        setList(SortUtils.RANDOM)

        fragmentMoviesBinding.progressBar.visibility = View.VISIBLE
        fragmentMoviesBinding.random.setOnClickListener { setList(SortUtils.RANDOM) }
        fragmentMoviesBinding.newest.setOnClickListener { setList(SortUtils.NEWEST) }
        fragmentMoviesBinding.popularity.setOnClickListener { setList(SortUtils.POPULARITY) }
        fragmentMoviesBinding.vote.setOnClickListener { setList(SortUtils.VOTE) }

        with(fragmentMoviesBinding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }

    }

    private val moviesObserver = Observer<Resource<PagedList<MovieEntity>>> { movies ->
        if (movies != null) {
            when (movies.status) {
                Status.LOADING -> {
                    fragmentMoviesBinding.progressBar.visibility = View.VISIBLE
                    fragmentMoviesBinding.notFound.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    fragmentMoviesBinding.progressBar.visibility = View.GONE
                    fragmentMoviesBinding.notFound.visibility = View.GONE
                    moviesAdapter.submitList(movies.data)
                    moviesAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    fragmentMoviesBinding.progressBar.visibility = View.GONE
                    fragmentMoviesBinding.notFound.visibility = View.VISIBLE
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setList(sort: String) {
        viewModel.getMovies(sort).observe(viewLifecycleOwner, moviesObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentMoviesBinding = null
    }

}