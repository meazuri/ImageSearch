package com.seint.imagesearch.model.retrofit

import com.seint.imagesearch.model.ImageSearchResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @Headers(
        "x-rapidapi-host: contextualwebsearch-websearch-v1.p.rapidapi.com",
        "x-rapidapi-key: 8e2cdebf94msh74ffb5be48ed5ffp192133jsnf19dc89b5c8d"

    )
    @GET("api/Search/ImageSearchAPI")
    fun searchImage(@QueryMap  params : Map<String, String>) : Call<ImageSearchResponse>
}