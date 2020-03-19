package com.seint.imagesearch

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.seint.imagesearch.model.ImageModel
import com.seint.imagesearch.model.retrofit.Repository
import com.seint.imagesearch.model.room.AppDatabase
import com.seint.imagesearch.model.room.ImageModelDao
import com.seint.imagesearch.viewModel.ImageSearchViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class ImageModelTest {

    private  var imageDataDao : ImageModelDao? =null
    val context :Context
    init {
        context = InstrumentationRegistry.getTargetContext()
    }

    @Before
    fun setUp(){
        //AppDatabase.TEST_MODE =true
        imageDataDao = AppDatabase.getDataBase(context = context.applicationContext).imageModelDao()

    }

    @Test
    fun testSaveImages(){
        //val jsonString = "{"url":"http://cdn01.cdn.justjared.com/wp-content/uploads/2018/10/swift-record/taylor-swift-sets-amas-record-02.jpg",
        // "height":1222,"width":964,"thumbnail":"https://rapidapi.contextualwebsearch.com/api/thumbnail/get?value=7523045288948770587","thumbnailHeight":228,"thumbnailWidth":179,"base64Encoding":null,"name":"taylor swift sets amas record 024161957","title":"Taylor Swift Sets an AMAs Record, Urges Fans to Vote While Accepting Artist of the Year!: Photo 4161957 | 2018 American Music Awards, American Music Awards, Taylor Swift Pictures | Just Jared","imageWebSearchUrl":"https://contextualwebsearch.com/search/Taylor%20swift/images"}"
        Assert.assertEquals(imageDataDao?.getImagesCount(),0)

        val imageModel = ImageModel(
            imageUrl = "http://cdn01.cdn.justjared.com/wp-content/uploads/2018/10/swift-record/taylor-swift-sets-amas-record-02.jpg",
            imageHeight = 1222,
            width = "964",
            thumbnail ="https://rapidapi.contextualwebsearch.com/api/thumbnail/get?value=7523045288948770587" ,
            thumbnailHeight = 100,
            thumbnailWidth = 100,
            name = "taylor swift and mathur hunt",search_text = "taylor swift",
            title = "Something",
            imageWebSearchUrl = "imageWebSearchUrl")
        imageDataDao?.insertSearchImages(listOf(imageModel))
        //val imageTest = getValue(imageDataDao?.getImageModel(imageModel.name)!!)
        //Assert.assertEquals(imageModel.name,imageTest.name )

        Assert.assertEquals(imageDataDao?.getImagesCount(),1)

    }

    @Test
    fun  testDeleteAllImage(){
        imageDataDao?.nukeTable()
        Assert.assertEquals(imageDataDao?.getImagesCount(),0)
    }

    // Copied from stackoverflow
    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T?) {
                data[0] = t
                latch.countDown()
                liveData.removeObserver(this)//To change body of created functions use File | Settings | File Templates.
            }

        }
        Handler(Looper.getMainLooper()).post {
            liveData.observeForever(observer)
        }

        latch.await(2, TimeUnit.SECONDS)

        return data[0] as T
    }
}