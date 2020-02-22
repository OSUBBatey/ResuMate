package com.example.resumate.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.resumate.R
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(), View.OnClickListener{
    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val firebaseAuth = FirebaseAuth.getInstance()
        val v = inflater.inflate(R.layout.login_layout, container, false)
        val email: EditText = v.findViewById(R.id.email)
        val password: EditText = v.findViewById(R.id.password)
        val loginButton: Button = v.findViewById(R.id.login_button)
        val signupButton: Button = v.findViewById(R.id.signup_button)
        val emailId = email.text.toString()
        val paswd = password.text.toString()

        signupButton.setOnClickListener {
            if(emailId.isEmpty() || paswd.isEmpty()) {
                if (emailId.isEmpty()) {
                    email.setError("Email was not provided")
                    email.isFocusable = true
                }
                if (paswd.isEmpty()) {
                    password.setError("Password was not provided")
                    password.isFocusable = true
                }
            }
                //firebaseAuth.createUserWithEmailAndPassword(emailId, paswd)
        }
        loginButton.setOnClickListener{
            //firebaseAuth.signInWithEmailAndPassword(emailId, paswd)
            loginButton.setOnClickListener(this)
        }

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