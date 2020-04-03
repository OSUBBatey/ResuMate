package com.example.resumate.utilities

import android.util.Log

object DataModel{
    var userName: String = ""
    var completeResume: String = ""
    var userSkills: List<String> = emptyList()
    var jobURL: String = ""
    var jobSkills: List<String>  = emptyList()

    init {
        Log.d("DEBUG","DATAMODEL INITIALIZED")
    }
}
