package com.example.testingapp.ui.MainQrCodeScanner

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.testingapp.data.QrModelItem
import com.example.testingapp.data.retrofit.RetrofitClient
import com.example.testingapp.databinding.MainQrCodeScannerActivityBinding
import com.example.testingapp.ui.statistic.StatisticQrCodeActivity
import com.example.testingapp.ui.statistic.ViewModelStatisticScanner
import com.example.testingapp.utils.Preferences
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainQrCodeScannerActivity : AppCompatActivity() {
    private lateinit var binding: MainQrCodeScannerActivityBinding
    private var viewModel: ViewModelStatisticScanner? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainQrCodeScannerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ViewModelStatisticScanner::class.java]
        initListener()
        initApi()
    }

    private fun initListener() {
        binding.btnFabScan.setOnClickListener {
            checkPermissionCamera(this)
        }
        binding.statistic.setOnClickListener {
            val intent = Intent(this, StatisticQrCodeActivity::class.java)
            startActivity(intent)
        }
        binding.online.setOnClickListener {

        }
    }

    private fun initApi() {
//
//        val workerFirstName = intent.getStringExtra("first_name")
//        val workerLastName = intent.getStringExtra("last_name")
//        val workerPosition = intent.getStringExtra("position")
//        val workerDepartment = intent.getStringExtra("department")
        val dataFromViewModel = viewModel?.getData()
        if (dataFromViewModel != null) {
        binding.name.text = dataFromViewModel.first_name
        binding.surname.text = dataFromViewModel.last_name
        binding.position.text = dataFromViewModel.position
        binding.department.text = dataFromViewModel.department
        }else  {
            Log.e("ololo", "initApi: $dataFromViewModel ", )
        }

    }


//
//        val call = apiService.getWorkerStatistics()
//
//        call.enqueue(object : Callback<QrModelItem> {
//            override fun onResponse(call: Call<QrModelItem>, response: Response<QrModelItem>) {
//                if (response.isSuccessful) {
//                    Log.d("ololo", "onResponse: ${response.body()}")
//                    binding.name.text = response.body()?.first_name
//                    binding.surname.text = response.body()?.last_name
//                    binding.position.text = response.body()?.position
//                    binding.department.text = response.body()?.department
//                }
//            }
//
//            override fun onFailure(call: Call<QrModelItem>, t: Throwable) {
//                Log.e("ololo", "onFailure: ${t.message}")
//            }
//        })
//    private fun initTestApi() {
//        val testData = retrofitClient.api.getDataTest()
//        testData.enqueue(object : Callback<List<TestApiModelItem>> {
//            override fun onResponse(
//                call: Call<List<TestApiModelItem>>,
//                response: Response<List<TestApiModelItem>>
//            ) {
//                val responseBody = response.body()!!
//                val stringBuilder = StringBuilder()
//                for (myData in responseBody) {
//                    stringBuilder.append( myData.id, myData.title)
//                    stringBuilder.append("\n")
//                }
//                binding.position.text = stringBuilder
//            }
//
//            override fun onFailure(call: Call<List<TestApiModelItem>>, t: Throwable) {
//                Log.e("ololo", "onFailure: ${t.message}", )
//            }
//
//        })
//    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showCamera()
            }
        }


    private val scannerLauncher =
        registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
            run {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this, StatisticQrCodeActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(false)
        options.setOrientationLocked(true)
        scannerLauncher.launch(options)
    }

    private fun checkPermissionCamera(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showCamera()
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            Toast.makeText(context, "Camera permission required", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // Get the current time and day
            val currentTimeMillis = System.currentTimeMillis()
            val sdf = SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a", Locale.getDefault())
            val formattedDateTime = sdf.format(Date(currentTimeMillis))
            val intent = Intent(this, StatisticQrCodeActivity::class.java)
            intent.putExtra("time", formattedDateTime)
            viewModel?.timeState(formattedDateTime)
            startActivity(intent)

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}