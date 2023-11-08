package com.example.testingapp.ui.statistic


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.testingapp.data.QrModelItem

class ViewModelStatisticScanner(): ViewModel() {
    val timeState = MutableLiveData<String>()
    private var responseData: QrModelItem? = null


    fun saveData(data: QrModelItem) {
        responseData = data
    }

    fun getData(): QrModelItem? {
        return responseData
    }

    fun timeState(time:String){
        timeState.value = time
    }
}