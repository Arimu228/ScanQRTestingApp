package com.example.testingapp.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.testingapp.data.QrModelItem
import com.example.testingapp.data.retrofit.RetrofitClient
import com.example.testingapp.databinding.ActivityAuthQrCodeScannerBinding
import com.example.testingapp.databinding.MainQrCodeScannerActivityBinding
import com.example.testingapp.ui.MainQrCodeScanner.MainQrCodeScannerActivity
import com.example.testingapp.ui.statistic.StatisticQrCodeActivity
import com.example.testingapp.ui.statistic.ViewModelStatisticScanner
import com.example.testingapp.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class AuthQrCodeScannerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthQrCodeScannerBinding
    val viewModel: ViewModelStatisticScanner by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthQrCodeScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
    }

    private fun initPreferences() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val shownFlag = "hasBeenShown"

        if (sharedPreferences.getBoolean(shownFlag, false)) {
            val intent = Intent(this, MainQrCodeScannerActivity::class.java)
            startActivity(intent)
        } else {
            val editor = sharedPreferences.edit()
            editor.putBoolean(shownFlag, true)
            editor.apply()
        }
        finish()
    }

    private fun initListener() {
        binding.btnSignIn.setOnClickListener {
            val apiService = RetrofitClient().api
            val email = "7203-0038@kyrgyzpost.kg"
            val password = "password"

            val call = apiService.auth(email, password)
            call.enqueue(object : Callback<QrModelItem> {
                override fun onResponse(call: Call<QrModelItem>, response: Response<QrModelItem>) {
                    if (response.isSuccessful) {
                        if (binding.email.text.toString() == email && binding.password.text.toString() == password) {
                            viewModel.saveData(response.body()!!)
//                            intent.putExtra("first_name", resultResponse?.first_name)
//                            intent.putExtra("last_name", resultResponse?.last_name)
//                            intent.putExtra("position", resultResponse?.position)
//                            intent.putExtra("department", resultResponse?.department)
                        } else {
                            Toast.makeText(
                                this@AuthQrCodeScannerActivity,
                                "Ведите логин и пароль",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        initPreferences()
                    }
                    Log.e("ololo", "onResponse: $response")
                }

                override fun onFailure(call: Call<QrModelItem>, t: Throwable) {
                    Log.e("ololo", "onResponse: ${t.message}")
                }
            })
        }
    }
}