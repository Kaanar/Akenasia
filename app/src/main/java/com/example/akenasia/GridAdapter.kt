package com.example.akenasia

import android.R
import android.content.Context

import android.widget.TextView

import android.view.LayoutInflater

import android.view.ViewGroup




class GridAdapter {


    var context: Context? = null
    lateinit var flowerName: Array<String>
    lateinit var image: IntArray

    var inflater: LayoutInflater? = null

    fun GridAdapter(context: Context?, flowerName: Array<String>, image: IntArray) {
        this.context = context
        this.flowerName = flowerName
        this.image = image
    }

    fun getCount(): Int {
        return flowerName.size
    }

    fun getItem(position: Int): Any? {
        return null
    }

    fun getItemId(position: Int): Long {
        return 0
    }

    fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView: View? = convertView
        if (inflater == null) inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, null)
        }
        val imageView: ImageView = convertView.findViewById(R.id.grid_image)
        val textView: TextView = convertView.findViewById(R.id.item_name)
        imageView.setImageResource(image[position])
        textView.text = flowerName[position]
        return convertView
    }
}