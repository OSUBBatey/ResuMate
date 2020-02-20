package com.example.resumate

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.resumate.ui.main.SplashFragment

class SplashActivity : SingleFragmentActivity() {

    override fun createFrag(): Fragment {
        return SplashFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_fragment_activity)
    }

}