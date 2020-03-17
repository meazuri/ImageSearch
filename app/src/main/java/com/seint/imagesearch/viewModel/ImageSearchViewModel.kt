package com.seint.imagesearch.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.seint.imagesearch.model.APIResponse
import com.seint.imagesearch.model.ImageModel
import com.seint.imagesearch.model.retrofit.Repository
import com.seint.imagesearch.model.room.AppDatabase
import com.seint.imagesearch.model.room.LocalRepository
import com.seint.imagesearch.view.PaginationListener
import java.util.concurrent.Executors

class ImageSearchViewModel( application: Application) : AndroidViewModel(application) {

     val appContext = application
     var imageListObservable: LiveData<List<ImageModel>> = MutableLiveData<List<ImageModel>>()

     var isThisLastPage = false
     var totalPage = 10
     var isThisLoading = false
     var currentPage = PaginationListener.PAGE_START

    private var searchTextLiveData = MutableLiveData<String>()

    val localRepository : LocalRepository
    private val mExecutor = Executors.newFixedThreadPool(5)


    init {
        //initialize local repository
        val imageModelDao = AppDatabase.getDataBase(application).imageModelDao()
        localRepository = LocalRepository(imageModelDao)

        imageListObservable = localRepository.allImages

//        imageListObservable =Transformations.switchMap(searchTextLiveData , {searchText ->
//            fetchData(searchText)
//        })
        searchTextLiveData.value =" Taylor swift"
        fetchData(searchTextLiveData.value.toString())



    }

    fun loadData(): LiveData<List<ImageModel>> {
        var  localImageList  :LiveData<List<ImageModel>> =  MutableLiveData<List<ImageModel>>()
        localImageList = localRepository.getALlImages(searchTextLiveData.value.toString())
        if(localImageList.value == null){
            localImageList =fetchData(searchTextLiveData.value.toString() )
        }else{
            localImageList
        }
        return localImageList
    }

    fun fetchData(search: String):LiveData<List<ImageModel>> {

        //var  localImageList  :LiveData<List<ImageModel>> =  MutableLiveData<List<ImageModel>>()

        if (search != "") {
            var parameters = mutableMapOf<String, String>()
            parameters.put("autoCorrect", "false")
            parameters.put("pageNumber", currentPage.toString())
            parameters.put("pageSize", "50")
            parameters.put("q", search)
            parameters.put("safeSearch", "true")


            Repository.instance.imageSearch(parameters, object :
                APIResponse<List<ImageModel>> {
                override fun onSuccess(data: LiveData<List<ImageModel>>, totalCount : Int) {
                    totalPage = totalCount
                    imageListObservable = data
                    mExecutor.execute {
                        if (data.value != null) {
                            if(currentPage == PaginationListener.PAGE_START) {
                                localRepository.clearData()
                            }
                            localRepository.saveSearchImages(data.value!!)
                            imageListObservable = localRepository.allImages

                        }
                    }
                }

            })

        }

        return imageListObservable


    }

    fun clearData(){
        mExecutor.execute {
            localRepository.clearData()
        }
    }

    fun  searchImage(search: String){
        this.searchTextLiveData.value= search
        fetchData(search)

    }
    fun  loadMoreData() : Boolean{
        if(currentPage < totalPage) {
            fetchData(this.searchTextLiveData.value.toString())
            return true
        }
        return false
    }





}