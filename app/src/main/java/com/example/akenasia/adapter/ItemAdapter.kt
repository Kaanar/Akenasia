package com.example.akenasia.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.akenasia.R
import com.example.akenasia.database.DatabaseHandler
import com.example.akenasia.database.Item
import com.example.akenasia.database.Place
import java.util.ArrayList


class ItemAdapter : BaseAdapter() {

    lateinit var context: Context
    lateinit var item: ArrayList<Item>
    lateinit var inflater: LayoutInflater
    lateinit var DbContext: DatabaseHandler

    fun ItemAdapter(context: Context,items: ArrayList<Item>) {
        this.context=context
        this.item=items
        inflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
       return item.size
    }

    override fun getItem(position: Int): Any {
        return item.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        DbContext = DatabaseHandler(context)
        var conView = convertView
        conView= inflater.inflate(R.layout.items_list_2, null)

        val nomItem = conView.findViewById<View>(R.id.name_item) as TextView
        nomItem.text =item.get(position).getItemName()

        val descrItem = conView.findViewById<View>(R.id.descr_item) as TextView
        descrItem.text = item.get(position).getitemDesc()

        return conView
    }


}