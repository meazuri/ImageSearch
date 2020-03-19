package com.seint.imagesearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seint.imagesearch.viewModel.ImageSearchViewModel
import kotlinx.android.synthetic.main.activity_main.*

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.seint.imagesearch.R
import com.seint.imagesearch.model.LocalSharePreference
import com.seint.imagesearch.toEditable
import com.seint.tabapplication.ui.main.BaseFragment
import com.seint.tabapplication.ui.main.SectionsPagerAdapter




class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        btnSearch.setOnClickListener {

            val currentFragment = sectionsPagerAdapter
                .instantiateItem(view_pager, view_pager.currentItem) as BaseFragment
            if (currentFragment.isVisible) {
                currentFragment.updateSearchText(editTextSearch.text.toString())
                currentFragment.refreshFragment()
            }

        }
        var saveSearchText = LocalSharePreference.getSearchText(application)
        editTextSearch.text = saveSearchText.toEditable()




    }

    fun getSearchText(): String {

        return editTextSearch.text.toString()
    }

}
