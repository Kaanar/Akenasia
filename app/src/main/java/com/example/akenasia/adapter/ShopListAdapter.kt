package com.example.akenasia.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.akenasia.R

class ShopListAdapter (private val context: Activity,private val id: Array<String>, private val name: Array<String>, private val des: Array<String>, private val prix: Array<String>)
    : ArrayAdapter<String>(context, R.layout.shop_listview,id) {


    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.shop_listview, null, true)

        val nomItem = rowView.findViewById<View>(R.id.ShopItemName) as TextView
        val desItem = rowView.findViewById<View>(R.id.ShopItemDes) as TextView
        val prixItem = rowView.findViewById<View>(R.id.ShopItemPrix) as TextView


        nomItem.text ="Nom: "+ name[position]
        desItem.text ="Description: "+ des[position]
        prixItem.text="Prix: " + prix[position]
        return rowView
    }
}