package com.example.testingapp.data.retrofit

import com.example.testingapp.data.AuthRequest
import com.example.testingapp.data.QrCodeItem
import com.example.testingapp.data.QrCodeItemTest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QrCodeApiService {
    //http://worktime.kyrgyzpost.kg/api/V1/user-data
    @GET("user-data")
    fun getWorkerStatistics(): Call<QrCodeItemTest>

    @POST("auth/login")
    fun auth(
        @Body authRequest: AuthRequest
    ): Call<QrCodeItemTest>
}
//var department: String? = null,
//    var position: String? = null,
//    var remote: Boolean = false,