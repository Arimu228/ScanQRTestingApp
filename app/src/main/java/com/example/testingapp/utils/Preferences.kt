package com.example.testingapp.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class Preferences(context: Context) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences("preferences", MODE_PRIVATE)

//Board
    fun isBoardingShowed(): Boolean {
        return sharedPref.getBoolean("board", false)
    }

    fun setBoardingShowed(isShow: Boolean) {
        sharedPref.edit().putBoolean("board", isShow).apply()
    }

    //image

    fun getImageUri(): String{
        return sharedPref.getString("img", "").toString()
    }
    fun saveImageUri(ImgUri: String){
        return sharedPref.edit().putString( "img", ImgUri).apply()
    }

    // text name


    var editor: Editor = sharedPref.edit()
    fun setName(): String {
        return sharedPref.getString("name", "").toString()
    }
    fun saveName(Name: String): Editor? {
        return sharedPref.edit().putString("name", Name)
    }
}