package com.example.resumate.ui.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.resumate.R
import com.example.resumate.utilities.dataModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException


class LoginFragment : Fragment(), View.OnClickListener{
    companion object {
        fun newInstance() = LoginFragment()
    }
    private lateinit var password: EditText
    private lateinit var email: EditText
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var emailStr: String
    private lateinit var passwordStr: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = FirebaseAuth.getInstance()
        val v = inflater.inflate(R.layout.login_layout, container, false)
        email = v.findViewById(R.id.email)
        password = v.findViewById(R.id.password)
        val loginButton: Button = v.findViewById(R.id.login_button)
        val signupButton: Button = v.findViewById(R.id.signup_button)

        loginButton.setOnClickListener(this)
        signupButton.setOnClickListener(this)

        return v
    }

    private fun emailOrPassEmpty(emailStr: String, passwordStr: String): Boolean {
        if (emailStr.isEmpty() || passwordStr.isEmpty()){
            if (emailStr.isEmpty()) {
                email.error = "Email was not provided"
                email.isFocusable = true
            }
            if (passwordStr.isEmpty()) {
                password.error = "Password was not provided"
                password.isFocusable = true
            }
            return true
        }
        return false
    }
    override fun onClick(v: View) {
        emailStr = email.text.toString()
        passwordStr = password.text.toString()
        val status: Boolean = emailOrPassEmpty(emailStr, passwordStr)
        if(!status) {
            when (v.id) {
                R.id.login_button -> {
                    firebaseAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                        .addOnCompleteListener(
                            OnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Sign in and update UI
                                    Log.d(TAG, "signInWithEmail:success")
                                    val user = firebaseAuth.currentUser
                                    goToOCR()
                                } else {
                                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                                    val e =
                                        task.exception as FirebaseAuthException?
                                    Toast.makeText(
                                        activity, "Sign in failed. " + e?.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            })
                }
                R.id.signup_button -> {
                    firebaseAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                        .addOnCompleteListener(
                            OnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success")
                                    val user = firebaseAuth.currentUser
                                    goToOCR()
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                    val e =
                                        task.exception as FirebaseAuthException?
                                    Toast.makeText(
                                        activity, "Sign up failed. " + e?.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            })
                }
            }
        }
    }

    private fun goToOCR(){
        activity?.finish()
        startActivity(Intent("com.example.resumate.ui.main.OCR"))
    }


}