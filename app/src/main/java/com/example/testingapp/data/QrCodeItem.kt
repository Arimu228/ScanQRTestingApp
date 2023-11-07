package com.example.testingapp.data

data class QrCodeItem(

    var id: Long? = null,
    var date: String? = null,
    var lastName: String? = null,
    var firstName: String? = null,
    var department: String? = null,
    var position: String? = null,
    var remote: Boolean = false,
    var token: String? = null,

) : java.io.Serializable



