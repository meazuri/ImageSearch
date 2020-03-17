package com.seint.imagesearch.model

import androidx.lifecycle.LiveData


interface APIResponse<T> {

    fun onSuccess( data: LiveData<T>)
}