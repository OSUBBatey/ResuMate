package com.example.resumate.utilities

import android.util.Log

object dataModel{
    var userName: String = ""
    var sanitizedResume: List<String> = emptyList()
    var completeResume: String = ""
    var userSkills: List<String> = emptyList()
    var jobURL: String = ""
    var jobSkills: List<String>  = emptyList()

    init {
        Log.d("DEBUG","DATAMODEL INITIALIZED")
    }
}
