package com.example.akenasia

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyListAdapter(private val context: Activity, private val id: Array<String>, private val name: Array<String>, private val lat: Array<String>, private val long: Array<String>)
    : ArrayAdapter<String>(context, R.layout.custom_list, name) {

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val idText = rowView.findViewById(R.id.textViewId) as TextView
        val nameText = rowView.findViewById(R.id.textViewName) as TextView
        val latText = rowView.findViewById(R.id.textViewLat) as TextView
        val longText = rowView.findViewById(R.id.textViewLong) as TextView

        idText.text = "Id: ${id[position]}"
        nameText.text = "Name: ${name[position]}"
        latText.text = "Latitude: ${lat[position]}"
        longText.text = "Longitude: ${long[position]}"

        return rowView
    }
}