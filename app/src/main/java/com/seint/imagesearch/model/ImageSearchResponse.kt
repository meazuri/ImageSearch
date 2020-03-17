package com.seint.imagesearch.model

import com.google.gson.annotations.SerializedName

data class ImageSearchResponse(
    @SerializedName("_type") var type:String,
    @SerializedName("totalCount") var totalCount:Int,
    @SerializedName("value") var imageList:List<ImageModel>

) {
}