package com.seint.imagesearch.view

import androidx.recyclerview.widget.RecyclerView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager


abstract class PaginationListener

    (private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean
    private val visibleThreshold = 5

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount * visibleThreshold
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading && !isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= PAGE_SIZE
            ) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()

    companion object {
        val PAGE_START = 1
        /**
         * Set scrolling threshold here (for now i'm assuming 10 item in one page)
         */
        private val PAGE_SIZE = 10
    }
}