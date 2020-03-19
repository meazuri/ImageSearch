package com.seint.imagesearch.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.seint.imagesearch.R
import com.seint.tabapplication.ui.main.BaseFragment
import kotlinx.android.synthetic.main.fragment_google_search.*
import android.webkit.WebView
import android.webkit.URLUtil
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*


class GoogleSearchFragment() : BaseFragment() {

    companion object {
        const val ARG_NAME = "imageWebSearchUrl"


        fun newInstance(): GoogleSearchFragment {
            val fragment = GoogleSearchFragment()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_google_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(searchText != "") {
            loadUrl()
            progressBar.visibility = View.VISIBLE

        }
        webView.getSettings().setJavaScriptEnabled(true);

        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {

                if(progressBar != null) {
                    progressBar.visibility = View.GONE
                }
            }
        }

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if ( activity is MainActivity ) {
            searchText=  (activity as MainActivity).editTextSearch.text.toString()
            loadUrl()
        }
    }

    override fun refreshFragment() {
        super.refreshFragment()

        loadUrl()

    }
    fun loadUrl(){
        if(URLUtil.isValidUrl(searchText)) {
            webView.loadUrl(searchText);
        }else {
            var searchURl = "https://www.google.com/search?q=$searchText"
            webView.loadUrl(searchURl);

        }
    }


}
