package com.seint.imagesearch.model

import com.google.gson.annotations.SerializedName

data class ImageModel (
    @SerializedName("url") var imageUrl : String,
    @SerializedName ("height")var imageHeight : Int,
    @SerializedName ("width")var width: String,
    @SerializedName ("thumbnail") var thumbnail: String,
    @SerializedName ("thumbnailHeight" )var thumbnailHeight: Int,
    @SerializedName ("thumbnailWidth") var thumbnailWidth: Int,
    @SerializedName ("name") var  name:String,
    @SerializedName ("title" ) var title: String
    )