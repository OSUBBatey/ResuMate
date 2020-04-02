package com.example.resumate.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.resumate.R
import com.example.resumate.ui.fragment.RecyclerFragment
import com.example.resumate.ui.fragment.SplashFragment
import com.example.resumate.ui.recycler.RecyclerItemObj

class RecyclerActivity : SingleFragmentActivity() {

    override fun createFrag(): Fragment {
        return RecyclerFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_fragment_activity)
    }

}