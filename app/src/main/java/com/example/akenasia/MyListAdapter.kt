package com.example.akenasia

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MyListAdapter(private val context: Activity, private val id: Array<String>, private val name: Array<String>, private val lat: Array<String>, private val long: Array<String>)
    : ArrayAdapter<String>(context, R.layout.custom_list, name) {

    @SuppressLint("SetTextI18n", "ViewHolder", "InflateParams")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.place_listview, null, true)

        val idText = rowView.findViewById(R.id.id) as TextView
        val nameText = rowView.findViewById(R.id.name) as TextView
        val latText = rowView.findViewById(R.id.latitude) as TextView
        val longText = rowView.findViewById(R.id.longitude) as TextView
        val choisirButton = rowView.findViewById(R.id.choisir) as Button

        idText.text = "Id: ${id[position]}"
        nameText.text = "Name: ${name[position]}"
        latText.text = "Latitude: ${lat[position]}"
        longText.text = "Longitude: ${long[position]}"
        choisirButton.setOnClickListener(){
            Toast.makeText(context,"hello",Toast.LENGTH_LONG).show()
        }

        return rowView
    }
}