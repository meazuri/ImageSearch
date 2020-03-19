package com.seint.imagesearch.model

import android.app.Application

object LocalSharePreference {
    fun saveSearchText(application: Application, text: String) {
            val pref = application.getSharedPreferences("MyPref", 0) // 0 - for private mode
            val editor = pref.edit()
            editor.putString("SearchText", text)
            editor.commit()


    }

    fun getSearchText(application: Application): String {
        val pref = application.getSharedPreferences("MyPref", 0) // 0 - for private mode
        val editor = pref.edit()
        return pref.getString("SearchText", "")!!
    }
}
