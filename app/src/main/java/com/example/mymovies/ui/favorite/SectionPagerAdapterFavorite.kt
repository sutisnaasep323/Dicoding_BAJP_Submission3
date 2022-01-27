package com.example.mymovies.ui.favorite

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mymovies.R
import com.example.mymovies.ui.favorite.movies.MoviesFragmentFavorite
import com.example.mymovies.ui.favorite.tvshows.TvShowsFragmentFavorite
import com.example.mymovies.ui.movies.MoviesFragment
import com.example.mymovies.ui.tvshows.TvShowsFragment

class SectionPagerAdapterFavorite(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MoviesFragmentFavorite()
            1 -> fragment = TvShowsFragmentFavorite()
        }
        return fragment as Fragment
    }

    @StringRes
    val tabTitles = intArrayOf(
        R.string.movies,
        R.string.tv_show
    )
}