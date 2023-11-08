package com.example.testingapp.data

data class AuthRequest(
    var email: String? = null,
    var password: String? = null,
    var token:String? = null
)