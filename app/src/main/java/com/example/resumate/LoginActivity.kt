package com.example.resumate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.resumate.ui.main.LoginFragment

class LoginActivity : SingleFragmentActivity() {

    override fun createFrag(): Fragment {
        return LoginFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_fragment_activity)
    }
}