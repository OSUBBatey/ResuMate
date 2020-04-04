package com.example.resumate.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.resumate.R
import com.example.resumate.ui.recycler.RecyclerItemObj
import com.example.resumate.ui.recycler.ResListAdapter
import com.example.resumate.utilities.DataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.display_results_layout.*
import org.w3c.dom.Text
import kotlin.math.round


class DisplayResultsFragment : Fragment(), View.OnClickListener{
    private lateinit var firebaseDatabase: DatabaseReference
    private var url = OCRFragment.job_url
    private lateinit var mAdapter : ResListAdapter
    private val matchList : ArrayList<RecyclerItemObj> = ArrayList()

    companion object {
        fun newInstance() = DisplayResultsFragment()
    }

    private lateinit var passwordStr: String
    private lateinit var webpage_url: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.display_results_layout, container, false)
        val doneButton: Button = v.findViewById(R.id.done_button)
        val saveJobButton: Button = v.findViewById(R.id.save_job_button)
        val percentageText: TextView = v.findViewById(R.id.percentage_text)

        saveJobButton.setOnClickListener(this)
        doneButton.setOnClickListener(this)

        initMatchList()

        // Calculate the percentage and round to two decimal places
        val percentage = (matchList.size / (DataModel.jobSkills.size.toDouble())) * 100
        val percentage_rounded:Double = Math.round(percentage * 100.0) / 100.0
        percentageText.setText("Your resume matches " + percentage_rounded + "% of the needed skills for the job")

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        results_recycler.apply{
            layoutManager = LinearLayoutManager(activity)
            mAdapter =  ResListAdapter(matchList) { _, _ ->
            }
            adapter = mAdapter
        }
        super.onViewCreated(view, savedInstanceState)
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

    private fun initMatchList(){
        for(element in DataModel.jobSkills){
            if (DataModel.userSkills.contains(element)) {
                matchList.add(RecyclerItemObj(R.drawable.ic_star, element))
            }
        }
    }

}