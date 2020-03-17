package com.seint.imagesearch.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seint.imagesearch.model.ImageModel


@Dao
interface ImageModelDao{

    @Query("SELECT * FROM ImageModel")
    fun getAll() : LiveData<List<ImageModel>>

    @Query("SELECT * FROM ImageModel WHERE search_text = :searchText")
    fun  getAll(searchText : String) : LiveData<List<ImageModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchImages( imageList: List<ImageModel>)

    @Query("DELETE FROM ImageModel")
    fun nukeTable()

}