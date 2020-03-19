package com.seint.imagesearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seint.imagesearch.R

import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.fragment.app.Fragment


class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url = intent.getStringExtra(GoogleSearchFragment.ARG_NAME)

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val fragment = GoogleSearchFragment.newInstance()
        fragment.updateSearchText(url)
        transaction.add(R.id.contentPanel, fragment as Fragment, "")
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        finish()
    }
}
