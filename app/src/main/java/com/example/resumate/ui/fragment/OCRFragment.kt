package com.example.resumate.ui.fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.resumate.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class OCRFragment : Fragment(), View.OnClickListener{
    private var test = false
    private lateinit var firebaseDatabase: DatabaseReference

    companion object {
        fun newInstance() = OCRFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("DEBUG", "VIEW CREATED IN ONCREATEVIEW")
        val v = inflater.inflate(R.layout.ocr_layout, container, false)
        val pickButton: Button = v.findViewById(R.id.choose_button)
        pickButton.setOnClickListener(this)
        val viewButton: Button = v.findViewById(R.id.view_button)
        viewButton.setOnClickListener(this)
        val logoutButton: Button = v.findViewById(R.id.logout_button)
        logoutButton.setOnClickListener(this)
        val addResumeButton: Button = v.findViewById(R.id.add_resume_button)
        addResumeButton.setOnClickListener(this)
        val deleteResumeButton: Button = v.findViewById(R.id.delete_resume_button)
        deleteResumeButton.setOnClickListener(this)

        return v
    }

    override fun onPause() {
        super.onPause()
        Log.d("DEBUG", "CHECKING TEST VAR BEFORE ONPAUSE")
        Log.d("DEBUG", "CURRENT VAL:")
        Log.d("DEBUG", test.toString())
        Log.d("DEBUG", "SETTING TEST VAR:")
        test = true
        Log.d("DEBUG", "NEW VALUE:")
        Log.d("DEBUG", test.toString())
    }

    override fun onResume() {
        super.onResume()
        if (test) {
            Log.d("DEBUG", "CHECKING TEST VAR AFTER RESUME")
            Log.d("DEBUG", "CURRENT VALUE:")
            Log.d("DEBUG", test.toString())
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.view_button -> runRecog()
            R.id.logout_button -> {
                FirebaseAuth.getInstance().signOut()
                goToLogin()

            }
            R.id.add_resume_button -> updateUserData()
            R.id.delete_resume_button -> deleteUserData()
        }

    }

    private fun updateUserData(){
        val user = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance().reference
        if (user != null){
            firebaseDatabase.child(user?.email.toString().substringBefore('.')).setValue("test text version 2.0test")
        } else {
            // No user is signed in
        }
    }

    private fun deleteUserData(){
        val user = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance().reference
        if (user != null){
            firebaseDatabase.child(user?.email.toString().substringBefore('.')).setValue(null) //null deletes the data stored
        } else {
            // No user is signed in
        }
    }

    private fun goToLogin(){
        activity?.finish()
        startActivity(Intent("com.example.resumate.ui.main.Login"))
    }
    //Temporary OCR (Same function as OCR2)
    private fun runRecog(){
        val tView = activity?.findViewById<TextView>(R.id.ocrTextView)
        val ff7 = BitmapFactory.decodeStream(activity?.assets?.open("ff7.bmp"))
        val image = FirebaseVisionImage.fromBitmap(ff7)

        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer
        detector .processImage(image)
            .addOnSuccessListener { p0 ->
                val t = p0!!.text
                print(t)
                tView?.text = t
                tView?.textSize = 16.toFloat()
                tView?.movementMethod = ScrollingMovementMethod()
                tView?.invalidate()
            }
            .addOnFailureListener { e ->
                print(message = "Failed with exception$e")
            }
    }

}