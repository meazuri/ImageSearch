package com.seint.tabapplication.ui.main

import android.content.Context
import android.media.Image
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.seint.imagesearch.R
import com.seint.imagesearch.view.GoogleSearchFragment
import com.seint.imagesearch.view.ImageSearchFragment

private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): BaseFragment {
        // getItem is called to instantiate the fragment for the given page.
        if(position == 0){
            return ImageSearchFragment()
        }else{
            return GoogleSearchFragment();
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }

}