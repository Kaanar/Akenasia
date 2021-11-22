package com.example.akenasia

import android.R
import android.app.Activity
import android.content.Context

import android.widget.TextView

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView


class GridAdapter : BaseAdapter() {


    private var context: Activity? = null
    lateinit var name: Array<String>
    lateinit var description: Array<String>

    lateinit var inflater: LayoutInflater

    fun GridAdapter(context: Activity?, name: Array<String>, description: Array<String>) {
        this.context = context
        this.name = name
        this.description = description
    }

    fun getCount(): Int {
        return name.size
    }

    fun getItem(position: Int): Any? {
        return null
    }

    fun getItemId(position: Int): Long {
        return 0
    }

    fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView: View? = convertView
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.bag_cardview, null)
        }
        val name: TextView? = convertView?.findViewById(R.id.name) ?
        val desc: TextView? = convertView?.findViewById(R.id.desc) ?
        name.text= name[position]
        desc.text = description[position]
        return convertView
    }
}