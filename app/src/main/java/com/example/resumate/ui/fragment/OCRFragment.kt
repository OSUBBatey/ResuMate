package com.example.resumate.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
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
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.core.graphics.rotationMatrix
import androidx.fragment.app.Fragment
import com.example.resumate.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

class OCRFragment : Fragment(), View.OnClickListener{
    private var test = false
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var imageBMP:Bitmap
    private lateinit var currentPhotoPath: String

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
            R.id.choose_button -> captureImg() }
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
            val scaleFactor: Int = Math.min(photoW / 1920, photoH / 1080)

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
}