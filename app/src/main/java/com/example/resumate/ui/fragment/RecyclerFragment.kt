package com.example.resumate.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.resumate.R
import com.example.resumate.ui.recycler.RecyclerItemObj
import com.example.resumate.ui.recycler.ResListAdapter
import com.example.resumate.utilities.dataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.recycler_layout.*

class RecyclerFragment : Fragment(),View.OnClickListener{

    private val skillList : ArrayList<RecyclerItemObj> = ArrayList()
    private lateinit var mAdapter : ResListAdapter
    private lateinit var skillTextIn: String
    private lateinit var skillBox :TextView
    private var firstPos = 1
    private lateinit var firebaseDatabase: DatabaseReference

    companion object {
        fun newInstance() = RecyclerFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initSkillsList()
        saveUserSkills()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.recycler_layout, container, false)
        val remSkillButton:Button = v.findViewById(R.id.remove_skill_button)
        val addSkillButton:Button = v.findViewById(R.id.add_skill_button)
        skillBox = v.findViewById(R.id.skill_TextBox)

        remSkillButton.setOnClickListener(this)
        addSkillButton.setOnClickListener(this)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            recyclerView.apply{
            layoutManager = LinearLayoutManager(activity)
            mAdapter =  ResListAdapter(skillList) { obj , pos ->
                if(obj.mImageResource == R.drawable.ic_star) {
                    obj.mImageResource = R.drawable.ic_remove
                }else{
                    obj.mImageResource = R.drawable.ic_star
                }
                mAdapter.notifyItemChanged(pos)
            }
            adapter = mAdapter
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun initSkillsList(){
        for(e in dataModel.sanitizedResume){
            skillList.add(RecyclerItemObj(R.drawable.ic_star, e))
        }
    }

    private fun saveUserSkills(){
        val user = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance().reference
        if (user != null){
            firebaseDatabase.child("users").child(user.email.toString().substringBefore('.')).setValue(skillList)
        } else {
            // No user is signed in
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.remove_skill_button -> removeMarkedSkills()
            R.id.add_skill_button -> addSkillatLastPos()
        }
    }

    private fun removeMarkedSkills(){

        val temp:MutableList<RecyclerItemObj> = emptyList<RecyclerItemObj>().toMutableList()
        var i = 0

        while(i < skillList.size){
            if(skillList[i].mImageResource == R.drawable.ic_remove) {
                skillList.remove(skillList[i])
                mAdapter.notifyItemRemoved(i)
                mAdapter.notifyItemRangeChanged(i,skillList.size)
            }else {
                i++
            }
        }
        saveUserSkills()
    }

    private fun addSkillatLastPos(){
        skillTextIn = skillBox.text.toString()
        skillBox.text = ""
        skillBox.invalidate()
        skillList.add(firstPos, RecyclerItemObj(R.drawable.ic_star, skillTextIn))
        mAdapter.notifyItemInserted(firstPos)
        mAdapter.notifyItemRangeChanged(firstPos, skillList.size)
        saveUserSkills()
    }
}