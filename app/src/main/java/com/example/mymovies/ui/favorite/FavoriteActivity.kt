package com.example.mymovies.ui.favorite

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.mymovies.R
import com.example.mymovies.databinding.ActivityFavoriteBinding
import com.example.mymovies.ui.home.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val actionBar = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#3f9d8b"))
        actionBar?.setBackgroundDrawable(colorDrawable)

        viewPagerConfig()

    }

    private fun viewPagerConfig() {
        val sectionsPagerAdapter = SectionPagerAdapterFavorite(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager_favorite)
        val tabs: TabLayout = findViewById(R.id.tabs_favorite)

        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(sectionsPagerAdapter.tabTitles[position])
        }.attach()
    }

}