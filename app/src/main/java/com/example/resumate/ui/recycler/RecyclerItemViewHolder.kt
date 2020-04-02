package com.example.resumate.ui.recycler

import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.resumate.R

class RecyclerItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.recycler_item, parent, false)){

    private var imgV : ImageView = itemView.findViewById(R.id.skill_icon)
    private var textV : TextView = itemView.findViewById(R.id.skill_desc)

    fun bind(recObj: RecyclerItemObj){
        imgV.setImageResource(recObj.mImageResource)
        textV.text = recObj.objText
    }

}