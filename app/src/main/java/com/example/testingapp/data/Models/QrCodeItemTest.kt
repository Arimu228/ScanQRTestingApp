package com.example.testingapp.data

data class QrCodeItemTest(
    val id: Int,
    val last_name: String,
    val first_name: String,
    val department: String,
    val position: String,
    val remote: Boolean,
    val token:String? = null
)

data class QrCodeTime(
    var id: Int? = null,
    var time:String? = null,
    var date:String? = null,
)