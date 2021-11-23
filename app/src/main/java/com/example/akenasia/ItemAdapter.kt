
package com.example.akenasia

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ItemAdapter(var context:Context, var arrayList: ArrayList<Item>) : BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View = View.inflate(context,R.layout.bag_cardview,null)

        var icons:ImageView = view.findViewById(R.id.grid_img)
        var names:TextView =view.findViewById(R.id.grid_tv)

        return view
    }


}