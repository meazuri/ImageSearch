package com.seint.imagesearch

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image_view.*
import okhttp3.internal.cache.DiskLruCache
import java.io.File
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class ImageViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)

        val imageUrl = intent.getStringExtra("ImageUrl")
        Picasso.get().setIndicatorsEnabled(true)
        Picasso.get().load(imageUrl).error(R.drawable.error).fit().into(imageViewBig);

    }


}
