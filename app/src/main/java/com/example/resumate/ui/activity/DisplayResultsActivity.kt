package com.example.resumate.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.resumate.R
import com.example.resumate.ui.fragment.DisplayResultsFragment
import com.example.resumate.ui.fragment.LoginFragment

class DisplayResultsActivity : SingleFragmentActivity() {

    override fun createFrag(): Fragment {
        return DisplayResultsFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_fragment_activity)
    }
}