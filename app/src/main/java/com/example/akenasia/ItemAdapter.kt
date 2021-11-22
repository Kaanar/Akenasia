package com.example.akenasia

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ItemAdapter(private val context: Activity, private val id: Array<String>, private val name: Array<String>, private val desc: Array<String>) : ArrayAdapter<String>(context, R.layout.bag_cardview, id) {
    lateinit var inflater: LayoutInflater
    lateinit var DbContext: DatabaseHandler

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.bag_cardview, null, true)

        //Lie les éléments xml à l'adapter
        val idText = rowView.findViewById(R.id.id) as TextView
        val nameText = rowView.findViewById(R.id.name) as TextView
        val descText = rowView.findViewById(R.id.description) as TextView

        //remplit les TextView par les valeurs des positions
        idText.text = (id[position].toInt()+1).toString()
        nameText.text = name[position]
        descText.text = desc[position]

        return rowView
    }

}