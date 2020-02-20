package com.example.resumate.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.resumate.R

class OCRFragment : Fragment(){
    private var test = false

    companion object {
        fun newInstance() = OCRFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("DEBUG", "VIEW CREATED IN ONCREATEVIEW")
        return inflater.inflate(R.layout.ocr_layout, container, false)
    }

    override fun onPause() {
        super.onPause()
        Log.d("DEBUG", "CHECKING TEST VAR BEFORE ONPAUSE")
        Log.d("DEBUG", "CURRENT VAL:")
        Log.d("DEBUG", test.toString())
        Log.d("DEBUG", "SETTING TEST VAR:")
        test = true
        Log.d("DEBUG", "NEW VALUE:")
        Log.d("DEBUG", test.toString())
    }

    override fun onResume() {
        super.onResume()
        if (test) {
            Log.d("DEBUG", "CHECKING TEST VAR AFTER RESUME")
            Log.d("DEBUG", "CURRENT VALUE:")
            Log.d("DEBUG", test.toString())
        }
    }


}