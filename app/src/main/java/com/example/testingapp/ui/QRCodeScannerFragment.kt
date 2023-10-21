package com.example.testingapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testingapp.R
import com.example.testingapp.databinding.ActivityMainBinding
import com.example.testingapp.databinding.FragmentQRCodeScannerBinding

class QRCodeScannerFragment : Fragment() {
    private lateinit var binding: FragmentQRCodeScannerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQRCodeScannerBinding.inflate(layoutInflater)
        return binding.root
    }


}