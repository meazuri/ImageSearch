package com.seint.imagesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seint.imagesearch.viewModel.ImageSearchViewModel
import kotlinx.android.synthetic.main.activity_main.*

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
        }
        imageSearchViewModel.imageListObservable.observe(this, Observer {

            if(it != null){
                mAdapter.setImageData(it)
                mAdapter.notifyDataSetChanged()
            }
        })

    }

//    override fun onSaveInstanceState(outState: Bundle?) {
//        super.onSaveInstanceState(outState)
//        outState?.putInt(NOTE_POSITION, notePosition)
//    }
}
