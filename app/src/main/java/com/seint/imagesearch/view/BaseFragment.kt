package com.seint.tabapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.seint.imagesearch.model.LocalSharePreference
import com.seint.imagesearch.view.GoogleSearchFragment
import com.seint.imagesearch.view.ImageSearchFragment
import com.seint.imagesearch.viewModel.ImageSearchViewModel

/**
 * A placeholder fragment containing a simple view.
 */
 open class BaseFragment : Fragment() {

    protected var searchText: String = ""

    open fun updateSearchText(text:String){
        searchText= text
    }

    open fun refreshFragment(){

    }

}