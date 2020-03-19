package com.seint.imagesearch.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ImageModel (
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id : Int =0,

    @ColumnInfo(name = "url")
    @SerializedName("url") var imageUrl : String,

    @ColumnInfo(name = "height")
    @SerializedName ("height")var imageHeight : Int,

    @ColumnInfo(name = "width")
    @SerializedName ("width")var width: String,

    @ColumnInfo(name = "thumbnail")
    @SerializedName ("thumbnail") var thumbnail: String,

    @ColumnInfo(name = "thumbnailHeight")
    @SerializedName ("thumbnailHeight" )var thumbnailHeight: Int,

    @ColumnInfo(name = "thumbnailWidth")
    @SerializedName ("thumbnailWidth") var thumbnailWidth: Int,

    @ColumnInfo(name = "name")
    @SerializedName ("name") var  name:String,

    @ColumnInfo(name = "title")
    @SerializedName ("title" ) var title: String,

    @ColumnInfo(name ="imageWebSearchUrl")
    @SerializedName("imageWebSearchUrl") var imageWebSearchUrl: String,

    @ColumnInfo(name = "search_text")
    var search_text :String

)
