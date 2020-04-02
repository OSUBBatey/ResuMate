package com.example.resumate.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ResListAdapter(private val list: List<RecyclerItemObj>) : RecyclerView.Adapter<RecyclerItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecyclerItemViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        val recObj: RecyclerItemObj = list[position]
        holder.bind(recObj)
    }

    override fun getItemCount(): Int = list.size
}