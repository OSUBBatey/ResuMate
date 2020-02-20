package com.example.resumate.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    var message = MutableLiveData<String>()

    init{
        message.value = "Initial Message"
    }


    fun setMessage(msg: String){
        message.postValue(msg)
    }

    fun getMessage() : String{
        return message.value.toString()
    }

    fun getObservableMsg() : LiveData<String>{
        return message
    }
}
