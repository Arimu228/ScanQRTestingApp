package com.example.testingapp.ui.MainQrCodeScanner

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.testingapp.data.QrCodeItem
import com.example.testingapp.data.QrCodeItemTest
import com.example.testingapp.data.retrofit.RetrofitClient
import com.example.testingapp.databinding.MainQrCodeScannerActivityBinding
import com.example.testingapp.ui.statistic.StatisticQrCodeActivity
import com.example.testingapp.ui.statistic.ViewModelStatisticScanner
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.security.auth.callback.Callback

class MainQrCodeScannerActivity : AppCompatActivity() {
    private lateinit var binding: MainQrCodeScannerActivityBinding
    private val retrofitClient by lazy { RetrofitClient()}
    private var viewModel: ViewModelStatisticScanner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainQrCodeScannerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ViewModelStatisticScanner::class.java]

        initListener()
    }

    private fun initListener() {
        binding.btnFabScan.setOnClickListener {
            checkPermissionCamera(this)
        }
        binding.statistic.setOnClickListener {
            val intent = Intent(this, StatisticQrCodeActivity::class.java)
            startActivity(intent)
        }
        binding.get.setOnClickListener {
            initRetrofit()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showCamera()
            }
        }

    private fun initRetrofit() {
        val workerData = retrofitClient.api.getWorkerStatistics()
        workerData.enqueue(object : retrofit2.Callback<QrCodeItemTest> {
            override fun onResponse(
                call: Call<QrCodeItemTest>,
                response: Response<QrCodeItemTest>
            ) {
                if (response.isSuccessful){
                    binding.name.text = response.body()?.first_name
                    Log.e("ololo", "onResponse: ${response.body()}")
                }
            }
            override fun onFailure(call: Call<QrCodeItemTest>, t: Throwable) {
                Log.e("ololo", "onResponse: ${t.message}")
            }
        })
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
            val intent = Intent(this, StatisticQrCodeActivity::class.java )
            intent.putExtra("time",formattedDateTime)
            viewModel?.timeState(formattedDateTime)
            startActivity(intent)

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}