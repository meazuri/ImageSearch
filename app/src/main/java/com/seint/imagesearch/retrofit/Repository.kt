package com.seint.imagesearch.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seint.imagesearch.model.ImageModel
import com.seint.imagesearch.model.ImageSearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository private constructor() {
    private val retrofitService: RetrofitService
    val HTTPS_API_URL = "https://contextualwebsearch-websearch-v1.p.rapidapi.com/"


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

    fun imageSearch(parameters: MutableMap<String,String>): MutableLiveData<List<ImageModel>> {
        var data = MutableLiveData<List<ImageModel>>()

        retrofitService.searchImage(parameters).enqueue(object :
            Callback<ImageSearchResponse> {
            override fun onResponse(call: Call<ImageSearchResponse>, response: Response<ImageSearchResponse>) {
                val imageSearchResponse =  response.body()
                data.value = imageSearchResponse?.imageList!!
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

