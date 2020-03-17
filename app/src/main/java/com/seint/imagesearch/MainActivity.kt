package com.seint.imagesearch

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
import com.seint.imagesearch.view.PaginationListener






class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: ImageAdapter
    lateinit var imageSearchViewModel :ImageSearchViewModel
    private var mLastFetchedDataTimeStamp: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAdapter = ImageAdapter(this)

        val lm = LinearLayoutManager(this)
        lm.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = lm
        recyclerView.adapter = mAdapter
        recyclerView.isNestedScrollingEnabled =false

        imageSearchViewModel = ViewModelProviders.of(this).get(ImageSearchViewModel::class.java)

        btnSearch.setOnClickListener {
            var searchText = editTextSearch.text.toString()
            imageSearchViewModel.searchImage(searchText)
            mSwipeRefreshLayout.isRefreshing = true

        }
        mSwipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            // Your code to make your refresh action
            var searchText = editTextSearch.text.toString()
            imageSearchViewModel.searchImage(searchText)
        })
        imageSearchViewModel.imageListObservable.observe(this, Observer {

            if(it != null){
                mAdapter.setImageData(it)
                mAdapter.notifyDataSetChanged()
                imageSearchViewModel.isThisLoading =false
                mSwipeRefreshLayout.isRefreshing =false
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
                    Toast.makeText(applicationContext,"No more data",Toast.LENGTH_LONG)

                }
            }


        })


    }



//    override fun onSaveInstanceState(outState: Bundle?) {
//        super.onSaveInstanceState(outState)
//        outState?.putInt(NOTE_POSITION, notePosition)
//    }
}
