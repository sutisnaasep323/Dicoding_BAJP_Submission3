package com.example.mymovies.ui.tvshows

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
import com.example.mymovies.databinding.FragmentTvShowsBinding
import com.example.mymovies.utils.SortUtils
import com.example.mymovies.viewmodel.ViewModelFactory
import com.example.mymovies.vo.Resource
import com.example.mymovies.vo.Status


class TvShowsFragment : Fragment() {

    private var _fragmentTvShowsBinding: FragmentTvShowsBinding? = null
    private val fragmentTvShowsBinding get() = _fragmentTvShowsBinding!!

    private lateinit var tvShowsAdapter: TvShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentTvShowsBinding = FragmentTvShowsBinding.inflate(layoutInflater, container, false)
        return fragmentTvShowsBinding.root
    }

    private lateinit var viewModel: TvShowsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[TvShowsViewModel::class.java]

            tvShowsAdapter = TvShowsAdapter()
            setList(SortUtils.RANDOM)

            fragmentTvShowsBinding.progressBar.visibility = View.VISIBLE

            with(fragmentTvShowsBinding.rvTvShows) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowsAdapter
            }

            fragmentTvShowsBinding.random.setOnClickListener { setList(SortUtils.RANDOM) }
            fragmentTvShowsBinding.newest.setOnClickListener { setList(SortUtils.NEWEST) }
            fragmentTvShowsBinding.popularity.setOnClickListener { setList(SortUtils.POPULARITY) }
            fragmentTvShowsBinding.vote.setOnClickListener { setList(SortUtils.VOTE) }
        }
    }

    private fun setList(sort: String) {
        viewModel.getTvShows(sort).observe(viewLifecycleOwner, tvShowsObserver)
    }

    private val tvShowsObserver = Observer<Resource<PagedList<MovieEntity>>> { tvShow ->
        if (tvShow != null) {
            when (tvShow.status) {
                Status.LOADING -> {
                    fragmentTvShowsBinding.progressBar.visibility = View.VISIBLE
                    fragmentTvShowsBinding.notFound.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    fragmentTvShowsBinding.progressBar.visibility = View.GONE
                    fragmentTvShowsBinding.notFound.visibility = View.GONE
                    tvShowsAdapter.submitList(tvShow.data)
                    tvShowsAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    fragmentTvShowsBinding.progressBar.visibility = View.GONE
                    fragmentTvShowsBinding.notFound.visibility = View.VISIBLE
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentTvShowsBinding = null
    }
}