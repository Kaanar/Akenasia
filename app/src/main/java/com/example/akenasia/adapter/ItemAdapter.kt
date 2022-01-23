package com.example.akenasia.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.akenasia.R

class ItemAdapter (private val context: Activity, private val id: Array<String>, private val name: Array<String>, private val desc: Array<String>, private val att: Array<String>
,private val def: Array<String> ) : ArrayAdapter<String>(context,R.layout.items_listview,id) {

    lateinit var inflater: LayoutInflater

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.items_listview, null, true)

        val idItem = rowView.findViewById<View>(R.id.ItemId) as TextView
        val nomItem = rowView.findViewById<View>(R.id.ItemName) as TextView
        val descrItem = rowView.findViewById<View>(R.id.ItemDesc) as TextView
        val attItem = rowView.findViewById<View>(R.id.ItemAtt) as TextView
        val defItem = rowView.findViewById<View>(R.id.ItemDef) as TextView


        idItem.text = id[position]
        nomItem.text ="Nom: "+ name[position]
        descrItem.text ="Description: "+ desc[position]
        attItem.text="ATT: "+ att[position]
        defItem.text="DEF: "+def[position]
        return rowView
    }
}

