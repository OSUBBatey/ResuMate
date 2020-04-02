package com.example.resumate.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.resumate.R
import com.example.resumate.ui.recycler.RecyclerItemObj
import com.example.resumate.ui.recycler.ResListAdapter
import kotlinx.android.synthetic.main.recycler_layout.*

class RecyclerFragment : Fragment(){
    companion object {
        fun newInstance() = RecyclerFragment()
    }
    private val skillList : ArrayList<RecyclerItemObj> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test1"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test2"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test1"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test2"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test1"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test2"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test1"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test2"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test1"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test2"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test1"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test2"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test1"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test2"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test1"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test2"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test1"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test2"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test1"))
        skillList.add(RecyclerItemObj(R.drawable.ic_star,"test2"))
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recycler_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = ResListAdapter(skillList)
        }
    }

    override fun onStart() {
        super.onStart()
    }
}