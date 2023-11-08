@file:Suppress("DEPRECATION")

package com.example.testingapp

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

fun View.loadImage(url: String){

    Glide.with(this).load(url).centerCrop().placeholder(R.drawable.baseline_account_circle_24).into(this as ImageView)

}
fun Fragment.showToast(msg:String){
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}
//internal fun View.showKeyboard() {
//    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
//}
//
//internal fun View.hideKeyboard() {
//    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    imm.hideSoftInputFromWindow(this.windowToken, 0)
