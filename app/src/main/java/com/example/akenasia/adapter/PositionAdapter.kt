package com.example.akenasia.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.akenasia.R
import com.example.akenasia.database.DatabaseHandler


class PositionAdapter(private val context: Activity,private val id: Array<String>, private val lat: Array<String>, private val long: Array<String>, private val partie: Array<String>) : ArrayAdapter<String>(context,
    R.layout.custom_list, id) {
    lateinit var inflater: LayoutInflater
    lateinit var DbContext: DatabaseHandler

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.position_listview, null, true)

        //Lie les éléments xml à l'adapter
        val idText = rowView.findViewById(R.id.id) as TextView
        val latText = rowView.findViewById(R.id.latitude) as TextView
        val longText = rowView.findViewById(R.id.longitude) as TextView

        //remplit les TextView par les valeurs des positions
        idText.text = (id[position].toInt()+1).toString()
        latText.text = lat[position]
        longText.text = long[position]

        return rowView
    }

}