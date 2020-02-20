package com.example.resumate.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_ocroutput_view.textView1

import com.example.resumate.R


/**
 * JUST HERE FOR REFERENCE IS NOT PART OF THE PROJECT ANYMORE
 */

class OCROutputView : Fragment() {

    companion object {
        fun newInstance() = OCROutputView()
    }

    lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ocroutput_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        viewModel.getObservableMsg().observe(this, Observer {
            textView1.text = viewModel.message.value
            })
        }
    }

