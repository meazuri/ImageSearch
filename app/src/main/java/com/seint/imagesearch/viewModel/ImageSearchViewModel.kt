package com.seint.imagesearch.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.seint.imagesearch.model.APIResponse
import com.seint.imagesearch.model.ImageModel
import com.seint.imagesearch.model.retrofit.Repository
import com.seint.imagesearch.model.room.AppDatabase
import com.seint.imagesearch.model.room.LocalRepository
import java.util.concurrent.Executors

class ImageSearchViewModel(application: Application) : AndroidViewModel(application) {

    var imageListObservable: LiveData<List<ImageModel>> = MutableLiveData<List<ImageModel>>()

    private var searchTextLiveData = MutableLiveData<String>()

    val localRepository : LocalRepository
    private val mExecutor = Executors.newFixedThreadPool(5)


    init {
        imageListObservable =Transformations.switchMap(searchTextLiveData , {searchText ->
            fetchData(searchText)
        })
        searchTextLiveData.value =" Taylor swift"

        //initialize local repository
        val imageModelDao = AppDatabase.getDataBase(application).imageModelDao()
        localRepository = LocalRepository(imageModelDao)



    }

    fun fetchData(search: String):LiveData<List<ImageModel>> {

        if (!searchTextLiveData.value.equals("")) {
            var parameters = mutableMapOf<String, String>()
            parameters.put("autoCorrect", "false")
            parameters.put("pageNumber", "1")
            parameters.put("pageSize", "10")
            parameters.put("q", search)
            parameters.put("safeSearch", "true")

            Repository.instance.imageSearch(parameters, object :
                APIResponse<List<ImageModel>> {
                override fun onSuccess(data: LiveData<List<ImageModel>>) {
                    imageListObservable = data
                    mExecutor.execute {
                        if (data.value != null) {
                            localRepository.clearData()
                            localRepository.saveSearchImages(data.value!!)
                            imageListObservable = localRepository.getALlImages()
                        }
                    }
                }

            })

        }

        return imageListObservable


    }

    fun  searchImage(search: String){
        if(search == this.searchTextLiveData.value){
            imageListObservable = localRepository.allImages
        }else {
            this.searchTextLiveData.postValue(search)
            fetchData(search)
        }
    }




}