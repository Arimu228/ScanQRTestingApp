package com.example.testingapp.ui.statistic


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.testingapp.data.QrCodeItemTest

class ViewModelStatisticScanner(): ViewModel() {
    val timeState = MutableLiveData<String>()

    fun timeState(time:String){
        timeState.value = time
    }
}