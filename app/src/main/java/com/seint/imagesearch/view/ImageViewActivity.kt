package com.seint.imagesearch.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seint.imagesearch.R
import com.seint.imagesearch.model.ImageModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_image_view.*






class ImageViewActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)

        var imageUrl =  intent.getStringExtra("ImageUrl")
        Picasso.get().setIndicatorsEnabled(true)
        Picasso.get().isLoggingEnabled = (true)


        val bitmap = ImagesCache.instance.getImageFromWareHouse(imageUrl)

        if(bitmap != null){
            imageViewBig.setImageBitmap(bitmap)
        }else {

//            val imgTask =
//                DownloadImageTask(ImagesCache(), imageViewBig, 300, 300)//Since you are using it from `Activity` call second Constructor.
//                imgTask.execute(imageUrl)

            Picasso.get().load(imageUrl).placeholder(R.drawable.loadinganimation).into(object : Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                }

                override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {

                    imageViewBig.setImageDrawable(resources.getDrawable(R.drawable.error))
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {

                    if (bitmap != null) {
                        ImagesCache.instance.addImageToWareHouse(imageUrl, bitmap)
                        imageViewBig.setImageBitmap(bitmap)
                    }
                }
            })
        }

    }

    override fun onStop() {
        ImagesCache.instance.clearCache()
        super.onStop()
    }



}
