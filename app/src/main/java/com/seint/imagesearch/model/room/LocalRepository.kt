package com.seint.imagesearch.model.room

import androidx.lifecycle.LiveData
import com.seint.imagesearch.model.ImageModel
import android.R.id.edit
import android.app.Application
import android.content.SharedPreferences
import com.seint.imagesearch.model.LocalSharePreference


class LocalRepository (private val imageModelDao: ImageModelDao,private val application: Application) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    var allImages: LiveData<List<ImageModel>> =imageModelDao.getAll()

    fun saveSearchImages(imageList: List<ImageModel>){
        imageModelDao.insertSearchImages(imageList)
    }

    fun getALlImages(searchText: String) :LiveData<List<ImageModel>>{
        return imageModelDao.getAll(searchText)
    }
    fun getALlImages() :LiveData<List<ImageModel>>{
        return imageModelDao.getAll()
    }

    fun clearData() {
        imageModelDao.nukeTable()
    }

}