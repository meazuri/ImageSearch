package com.seint.imagesearch.model.room

import androidx.lifecycle.LiveData
import com.seint.imagesearch.model.ImageModel

class LocalRepository (private val imageModelDao: ImageModelDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allImages: LiveData<List<ImageModel>> =imageModelDao.getAll()

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