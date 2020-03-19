package com.seint.imagesearch

import android.text.Editable

object Utilities {

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)