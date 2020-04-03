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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.display_results_layout.*


class DisplayResultsFragment : Fragment(), View.OnClickListener{
    private lateinit var firebaseDatabase: DatabaseReference
    private var url = OCRFragment.job_url

    companion object {
        fun newInstance() = DisplayResultsFragment()
    }
    //private lateinit var password: EditText
    //private lateinit var email: EditText
    //private lateinit var firebaseAuth: FirebaseAuth
    //private lateinit var emailStr: String
    private lateinit var passwordStr: String
    private lateinit var webpage_url: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //firebaseAuth = FirebaseAuth.getInstance()
        val v = inflater.inflate(R.layout.display_results_layout, container, false)
        val doneButton: Button = v.findViewById(R.id.done_button)
        val saveJobButton: Button = v.findViewById(R.id.save_job_button)
       // email = v.findViewById(R.id.email)
       // password = v.findViewById(R.id.password)
        //val loginButton: Button = v.findViewById(R.id.login_button)
        //val signupButton: Button = v.findViewById(R.id.signup_button)

        //loginButton.setOnClickListener(this)
        //signupButton.setOnClickListener(this)
        saveJobButton.setOnClickListener(this)
        doneButton.setOnClickListener(this)

        return v
    }


    override fun onClick(v: View) {

        when(v.id) {
            R.id.done_button -> goToOCR()
            R.id.save_job_button -> saveUserJobs()
        }
    }

    private fun goToOCR(){
        activity?.finish()
        startActivity(Intent("com.example.resumate.ui.main.OCR"))
    }

    private fun saveUserJobs(){
        val user = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance().reference
        if (user != null){
            firebaseDatabase.child("jobs").child(user.email.toString().substringBefore('.')).push().setValue(url)
        } else {
            // No user is signed in
        }
    }


}