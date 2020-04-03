package com.example.resumate.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.EXTRA_OUTPUT
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.rotationMatrix
import androidx.fragment.app.Fragment
import com.example.resumate.R
import com.example.resumate.ui.activity.OCRActivity
import com.example.resumate.utilities.createTokenSetFromString
import com.example.resumate.utilities.createTokenSetFromString
import com.example.resumate.utilities.createTokenSetFromWebpageLink
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.ocr_layout.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

class OCRFragment : Fragment(), View.OnClickListener{
    private var test = false
    private lateinit var firebaseDatabase: DatabaseReference
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var imageBMP:Bitmap
    private lateinit var currentPhotoPath: String
    private var i = 0

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
        val addUserButton: Button = v.findViewById(R.id.add_user_button)
        addUserButton.setOnClickListener(this)
        val deleteUserButton: Button = v.findViewById(R.id.delete_user_button)
        deleteUserButton.setOnClickListener(this)
        val addJobsButton: Button = v.findViewById(R.id.add_job_button)
        addJobsButton.setOnClickListener(this)
        val deleteJobsButton: Button = v.findViewById(R.id.delete_job_button)
        deleteJobsButton.setOnClickListener(this)
        val compareResumeButton: Button = v.findViewById(R.id.compare_button)
        compareResumeButton.setOnClickListener(this)

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
            R.id.view_button -> {
                    runRecog()
            }
            R.id.choose_button -> captureImg()
            R.id.logout_button -> {
                FirebaseAuth.getInstance().signOut()
                goToLogin()
            }
            R.id.add_resume_button -> updateUserResume()
            R.id.delete_resume_button -> deleteUserResume()
            R.id.add_job_button -> updateUserJobs()
            R.id.delete_job_button -> deleteUserJobs()
            R.id.add_user_button -> updateUsers()
            R.id.delete_user_button -> deleteUsers()
            R.id.compare_button -> {
                // TODO: First check that there is a resume file

                val webpage = webpage_link.text.toString()
                // Check if link is empty
                if(webpage.isEmpty()){
                    Toast.makeText(
                        activity, "Webpage link is empty.",
                        Toast.LENGTH_SHORT
                    ).show()
                }  else if (!(webpage.startsWith("https://"))) {
                    // Check if link format is correct
                    Toast.makeText(
                        activity, "Webpage link must start with https://",
                        Toast.LENGTH_SHORT
                    ).show()
                }else {
                    //val task: StartAsyncTask = StartAsyncTask(context)
                    StartAsyncTask().execute(webpage)
                        // Need to pass in the results to the next page after comparing
                }

            }
        }
    }

    private fun updateUserResume(){
        val user = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance().reference
        if (user != null){
            firebaseDatabase.child("resume").child(user.email.toString().substringBefore('.')).setValue("test resume")
        } else {
            // No user is signed in
        }
    }

    private fun deleteUserResume(){
        val user = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance().reference
        if (user != null){
            firebaseDatabase.child("resume").child(user.email.toString().substringBefore('.')).setValue(null) //null deletes the data stored
        } else {
            // No user is signed in
        }
    }

    private fun updateUserJobs(){
        val user = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance().reference
        if (user != null){
            firebaseDatabase.child("jobs").child(user.email.toString().substringBefore('.')).setValue("test job")
        } else {
            // No user is signed in
        }
    }

    private fun deleteUserJobs(){
        val user = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance().reference
        if (user != null){
            firebaseDatabase.child("jobs").child(user.email.toString().substringBefore('.')).setValue(null) //null deletes the data stored
        } else {
            // No user is signed in
        }
    }

    private fun updateUsers(){
        val user = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance().reference
        if (user != null){
            firebaseDatabase.child("users").child(user.email.toString().substringBefore('.')).setValue(user.uid)
        } else {
            // No user is signed in
        }
    }

    private fun deleteUsers(){
        val user = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance().reference
        if (user != null){
            firebaseDatabase.child("users").child(user.email.toString().substringBefore('.')).setValue(null) //null deletes the data stored
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
        val image = FirebaseVisionImage.fromBitmap(imageBMP)
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

    private fun captureImg(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                val imgFile:File? = try{
                    getImgFile()
                } catch (e:IOException){
                    Log.d("DEBUG", e.toString())
                    exitProcess(1)
                }
                imgFile.also {
                    val imgUri = FileProvider.getUriForFile(activity!!,
                        "com.example.resumate.fileprovider",it!!)
                    takePictureIntent.putExtra(EXTRA_OUTPUT, imgUri)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun getImgFile():File{

        val ts = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imgName = "jpg_$ts"
        val storedDir = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        Log.d("URI",storedDir!!.toString())

        return File.createTempFile(imgName, ".jpg",storedDir).apply{
            currentPhotoPath = absolutePath
            Log.d("URI", currentPhotoPath)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val camImg = intent?.extras
            if(camImg != null){
                imageBMP = camImg.get("data") as Bitmap
            }else{
                setPic()
            }
        }
    }

    private fun setPic(){
        val bmOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true

            val photoW = outWidth
            val photoH = outHeight
            val scaleFactor: Int = (photoW / 1920).coerceAtMost(photoH / 1080)

            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            rotationMatrix(90.toFloat())
        }
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also {bitmap ->
            imageBMP = rotateImg(bitmap, 90)
        }
    }

    private fun rotateImg(img:Bitmap, degree: Int): Bitmap{
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotated = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        img.recycle()
        return rotated
    }

    inner class StartAsyncTask: AsyncTask<String, Any, Any>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): Boolean {

            try {
                val doc: Document =
                    Jsoup.connect(params[0]).get()
                val body: String = Jsoup.parse(doc.body().text()).text()
                val tokenizedWebpage: MutableList<String> = createTokenSetFromWebpageLink(body.toLowerCase())
                System.out.println(tokenizedWebpage)
                return true
            } catch (e: Exception){

                System.out.println("Error " + e)
                return false
            }
       }

        override fun onPostExecute(result: Any?) {
            super.onPostExecute(result)
            if(result == true) {
                startActivity(Intent("com.example.resumate.ui.main.DisplayResults"))
            }
            if(result == false){
                Toast.makeText(
                    activity, "There's an error with this link, please try another one.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
