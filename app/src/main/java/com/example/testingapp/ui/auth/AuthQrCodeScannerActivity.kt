package com.example.testingapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.testingapp.data.AuthRequest
import com.example.testingapp.data.QrCodeItem
import com.example.testingapp.data.QrCodeItemTest
import com.example.testingapp.data.retrofit.RetrofitClient
import com.example.testingapp.databinding.ActivityAuthQrCodeScannerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthQrCodeScannerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthQrCodeScannerBinding
    private var retrofitClient = RetrofitClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthQrCodeScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()


    }

    private fun initListener() {
        binding.btnSignIn.setOnClickListener {

            val user = retrofitClient.api.auth(
                AuthRequest(
                    email = binding.email.text.toString(), password = binding.password.text.toString()
                )
            )
            user.enqueue(object : Callback<QrCodeItemTest> {
                override fun onResponse(
                    call: Call<QrCodeItemTest>,
                    response: Response<QrCodeItemTest>
                ) {
                    if (response.isSuccessful) {
                        binding.name.text = response.body()?.first_name
                    } else {
                        Toast.makeText(
                            this@AuthQrCodeScannerActivity,
                            "ведите данные",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<QrCodeItemTest>, t: Throwable) {
                    Log.e("ololo", "onFailure: ${t.message}")
                }
            })

        }
    }
}


//    private fun initRetrofit() {
//        val workerData = retrofitClient.api.getWorkerStatistics()
//        workerData.enqueue(object : retrofit2.Callback<QrCodeItemTest> {
//            override fun onResponse(
//                call: Call<QrCodeItemTest>,
//                response: Response<QrCodeItemTest>
//            ) {
//                if (response.isSuccessful){
//                    binding.name.text = response.body()?.first_name
//                    Log.e("ololo", "onResponse: ${response.body()}")
//                }
//            }
//            override fun onFailure(call: Call<QrCodeItemTest>, t: Throwable) {
//                Log.e("ololo", "onResponse: ${t.message}")
//            }
//        })
//    }