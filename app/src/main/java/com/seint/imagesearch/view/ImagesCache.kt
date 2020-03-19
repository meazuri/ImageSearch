package com.seint.imagesearch.view

import android.graphics.Bitmap
import android.util.LruCache

class ImagesCache {

    var imagesWareHouse :LruCache<String,Bitmap>

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() /1024);

        var cacheSize = maxMemory /8

        System.out.println("cache size = "+cacheSize);
        imagesWareHouse = LruCache(cacheSize.toInt())

        imagesWareHouse = object : LruCache<String, Bitmap>(cacheSize.toInt()) {
            override fun sizeOf(key: String, value: Bitmap): Int {
                // The cache size will be measured in kilobytes rather than number of items.

                val bitmapByteCount = value.rowBytes * value.height

                return bitmapByteCount / 1024
            }
        }
    }
    companion object {
        private var imagesCache: ImagesCache? = null

        val instance: ImagesCache
            @Synchronized get() {
                if (imagesCache == null) {

                        imagesCache = ImagesCache()
                }
                return imagesCache as ImagesCache
            }
    }
    fun addImageToWareHouse(key:String, value:Bitmap){

        if(imagesWareHouse != null && imagesWareHouse.get(key) == null){
            imagesWareHouse.put(key,value)
        }
    }

    fun getImageFromWareHouse(key:String): Bitmap? {

        if(key != null){
            return imagesWareHouse.get(key)
        }else{
            return null
        }
    }

    fun deleteFromWareHouse(key:String){
        imagesWareHouse.remove(key);
    }

    fun clearCache(){
        if(imagesWareHouse != null){
            imagesWareHouse.evictAll()
        }
    }

}