package com.example.testingapp.ui.statistic

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testingapp.data.QrCodeTime
import com.example.testingapp.databinding.ActivityStatisticQrCodeBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StatisticQrCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticQrCodeBinding
    private val scannedItems = ArrayList<QrCodeTime>()
    private var adapter: StatisticAdapter? = null
    private var viewModel: ViewModelStatisticScanner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticQrCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
        initViewModel()
        timeInitView()
        initListener()

    }

    private fun initListener() {
        binding.includeToolBar.tvBack.setOnClickListener {
            finish()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ViewModelStatisticScanner::class.java]
    }

    private fun initAdapter() {
        binding.rvQrCodeStats.layoutManager = LinearLayoutManager(this)
        binding.rvQrCodeStats.adapter = adapter

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun timeInitView() {
        val result = intent.extras?.getString("time", "fvdcdfgvf")
        val currentTimeMillis = System.currentTimeMillis()
        val sdf = SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a", Locale.getDefault())
        val formattedDateTime = sdf.format(Date(currentTimeMillis))
        adapter?.notifyItemInserted(scannedItems.size - 1)
        adapter = StatisticAdapter(time = formattedDateTime)
        scannedItems.add(QrCodeTime(date = formattedDateTime, time = currentTimeMillis.toString()))
        adapter?.setQrCode(scannedItems)


    }
}