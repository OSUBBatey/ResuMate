package com.example.resumate.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.resumate.R
import kotlinx.android.synthetic.main.main_fragment.editText1

/**
 * JUST HERE FOR REFERENCE IS NOT PART OF THE PROJECT ANYMORE
 */
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    // Variable for viewModel

    lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return  inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        val b = view?.findViewById<Button>(R.id.button1)
        b?.setOnClickListener{
            //Set the OnClick() property to the fragment function
            setPT()
        }
    }

    private fun setPT(){
        //Sets the message in the shared viewModel
        val textIn = editText1.text
        viewModel.setMessage(textIn.toString())
    }

}
