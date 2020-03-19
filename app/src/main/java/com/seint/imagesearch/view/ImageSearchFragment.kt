package com.seint.imagesearch.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.seint.imagesearch.R
import com.seint.imagesearch.viewModel.ImageSearchViewModel
import com.seint.tabapplication.ui.main.BaseFragment
import kotlinx.android.synthetic.main.image_search_fragment.*

class ImageSearchFragment : BaseFragment() {

    private lateinit var mAdapter: ImageAdapter
    lateinit var imageSearchViewModel :ImageSearchViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.image_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageSearchViewModel = ViewModelProviders.of(this).get(ImageSearchViewModel::class.java)
        mAdapter = ImageAdapter(context!!)

        val lm = LinearLayoutManager(context!!)
        lm.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = lm
        recyclerView.adapter = mAdapter
        recyclerView.isNestedScrollingEnabled =false


        mSwipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            // Your code to make your refresh action

            clearAdapterData()
            imageSearchViewModel.isThisLoading = true
            imageSearchViewModel.searchImage(searchText)
        })
        imageSearchViewModel.imageListObservable.observe(viewLifecycleOwner, Observer {

            if(it != null){
                mAdapter.setImageData(it)
                mAdapter.notifyDataSetChanged()

            }
            if(!imageSearchViewModel.isThisLoading){
                mSwipeRefreshLayout.isRefreshing = false
            }
        })

        imageSearchViewModel.onSuccess.observe(viewLifecycleOwner, Observer {

            if(it) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        })

        mSwipeRefreshLayout.isRefreshing = true

        recyclerView.addOnScrollListener( object :PaginationListener(lm) {
            override val isLastPage: Boolean
                get() = imageSearchViewModel.isThisLastPage //To change initializer of created properties use File | Settings | File Templates.
            override val isLoading: Boolean
                get() = imageSearchViewModel.isThisLoading //To change initializer of created properties use File | Settings | File Templates.

            override fun loadMoreItems() {
                imageSearchViewModel.isThisLoading = true
                imageSearchViewModel.currentPage ++
                if(! imageSearchViewModel.loadMoreData()){
                    imageSearchViewModel.isThisLoading = false
                    Toast.makeText(context,"No more data", Toast.LENGTH_LONG)

                }
            }


        })
    }

    override fun refreshFragment() {
        super.refreshFragment()
        refreshData()
    }

    fun refreshData(){
        imageSearchViewModel.isThisLoading = true
        mSwipeRefreshLayout.isRefreshing = true
        clearAdapterData()
        imageSearchViewModel.searchImage(searchText)
    }
    fun clearAdapterData(){
        mAdapter.setImageData(mutableListOf())
        mAdapter.notifyDataSetChanged()

        mSwipeRefreshLayout.isRefreshing = true

    }



}
