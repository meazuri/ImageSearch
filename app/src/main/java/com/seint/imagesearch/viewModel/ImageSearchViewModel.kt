package com.seint.imagesearch.viewModel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.seint.imagesearch.model.ImageModel
import com.seint.imagesearch.retrofit.Repository

class ImageSearchViewModel : ViewModel() {

    private var imageListObservable: LiveData<List<ImageModel>> = MutableLiveData<List<ImageModel>>()

    private var searchTextLiveData = MutableLiveData<String>()

    init {
        searchTextLiveData.value =""
        imageListObservable =Transformations.switchMap(searchTextLiveData , {searchText -> getImageListObservable()})

    }

    fun getImageListObservable( ) : LiveData<List<ImageModel>> {

        if(!searchTextLiveData.value.equals("")) {
            var parameters = mutableMapOf<String, String>()
            parameters.put("autoCorrect", "false")
            parameters.put("pageNumber", "1")
            parameters.put("pageSize", "10")
            parameters.put("q", searchTextLiveData.value.toString())
            parameters.put("safeSearch", "true")
            imageListObservable = Repository.instance.imageSearch(parameters)
        }
        return  imageListObservable
    }
    fun  searchImage(search: String){
        this.searchTextLiveData.value = search
        imageListObservable= getImageListObservable()
    }


}