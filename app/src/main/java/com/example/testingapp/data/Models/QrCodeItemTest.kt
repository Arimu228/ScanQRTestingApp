package com.example.testingapp.data

data class QrModelItem(
    val id: Int,
    val department: String,
    val first_name: String,
    val last_name: String,
    val position: String,
    val remote: Boolean
)

data class QrCodeTime(
    var id: Int? = null,
    var time:String? = null,
    var date:String? = null,
)