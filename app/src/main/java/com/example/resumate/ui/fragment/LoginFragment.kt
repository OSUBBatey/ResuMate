package com.example.resumate.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.resumate.R

class LoginFragment : Fragment(), View.OnClickListener{
    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.login_layout, container, false)

        val loginButton: Button = v.findViewById(R.id.login_button)
        loginButton.setOnClickListener(this)
        val signupButton: Button = v.findViewById(R.id.signup_button)
        signupButton.setOnClickListener(this)

        return v
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.login_button -> goToOCR()
        }
    }

    private fun goToOCR(){
        activity?.finish()
        startActivity(Intent("com.example.resumate.ui.main.OCR"))
    }
}