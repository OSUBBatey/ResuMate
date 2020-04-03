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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.resumate.R
import com.example.resumate.ui.recycler.RecyclerItemObj
import com.example.resumate.ui.recycler.ResListAdapter
import com.example.resumate.utilities.dataModel
import kotlinx.android.synthetic.main.recycler_layout.*

class RecyclerFragment : Fragment(),View.OnClickListener{

    private val skillList : ArrayList<RecyclerItemObj> = ArrayList()
    private lateinit var mAdapter : ResListAdapter
    private lateinit var skillTextIn: String
    private var lastPos = 1
    companion object {
        fun newInstance() = RecyclerFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initSkillsList()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.recycler_layout, container, false)
        val remSkillButton:Button = v.findViewById(R.id.remove_skill_button)
        val addSkillButton:Button = v.findViewById(R.id.add_skill_button)

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
                lastPos = pos
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
    }

    private fun addSkillatLastPos(){
        skillList.add(lastPos, RecyclerItemObj(R.drawable.ic_star, skillTextIn))
        mAdapter.notifyItemInserted(lastPos)
        mAdapter.notifyItemRangeChanged(lastPos, skillList.size)
    }
}