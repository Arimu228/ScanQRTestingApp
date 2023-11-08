package com.example.testingapp.data.retrofit

import com.example.testingapp.data.Models.AuthRequest
import com.example.testingapp.data.QrModelItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface QrCodeApiService {
    //http://worktime.kyrgyzpost.kg/api/V1/user-data
    @GET("V1/user-data")
    @Headers("Authorization: Bearer 22|yEGvh8PJdvyrjZhZaBTG6y1b7I6wfxiahitWRrR982592077")
    fun getWorkerStatistics(): Call<QrModelItem>

    @POST("V1/auth/login")
    @FormUrlEncoded
    fun auth(
        @Field("email") email:String,
        @Field("password") password:String,
    ): Call<QrModelItem>
}