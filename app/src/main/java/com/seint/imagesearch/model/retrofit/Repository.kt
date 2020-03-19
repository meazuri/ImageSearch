package com.seint.imagesearch.model.retrofit

import androidx.lifecycle.MutableLiveData
import com.seint.imagesearch.model.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class Repository private constructor() {
    private val retrofitService: RetrofitService
    val HTTPS_API_URL = "https://contextualwebsearch-websearch-v1.p.rapidapi.com/"
    private val mExecutor = Executors.newFixedThreadPool(5)

    init {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging)
        val retrofit = Retrofit.Builder()
            .baseUrl(HTTPS_API_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitService = retrofit.create(RetrofitService::class.java!!)
    }

    fun imageSearch( parameters: MutableMap<String,String> ,dataResponse : APIResponse<List<ImageModel>>): MutableLiveData<List<ImageModel>> {
        var data = MutableLiveData<List<ImageModel>>()

        retrofitService.searchImage(parameters).enqueue(object :
            Callback<ImageSearchResponse> {
            override fun onResponse(call: Call<ImageSearchResponse>, response: Response<ImageSearchResponse>) {
                val imageSearchResponse =  response.body()

                if(imageSearchResponse?.imageList != null) {
                    imageSearchResponse.imageList.map {
                        it.search_text = parameters.getValue("q")
                    }
                    data.value = imageSearchResponse.imageList
                    dataResponse.onSuccess( data,imageSearchResponse?.totalCount)


                }else{
                    data.setValue(null)

                }
            }

            override fun onFailure(call: Call<ImageSearchResponse>, t: Throwable) {
                data.setValue(null)
            }
        })

        return data
    }



    private fun simulateDelay() {
        try {
            Thread.sleep(10)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    companion object {
        private var projectRepository: Repository? = null

        val instance: Repository
            @Synchronized get() {
                if (projectRepository == null) {
                    if (projectRepository == null) {
                        projectRepository = Repository()
                    }
                }
                return projectRepository as Repository
            }
    }
}

