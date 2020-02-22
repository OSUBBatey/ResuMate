package com.example.resumate.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.resumate.R
import com.example.resumate.ui.fragment.OCRFragment


class OCRActivity : SingleFragmentActivity() {

    override fun createFrag(): Fragment {
        return OCRFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_fragment_activity)
    }

}