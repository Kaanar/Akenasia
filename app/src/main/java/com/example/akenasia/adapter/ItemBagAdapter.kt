
package com.example.akenasia.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.akenasia.database.ItemBag
import com.example.akenasia.R
import java.util.*

class ItemBagAdapter(var context:Context, var arrayList: ArrayList<ItemBag>) : BaseAdapter() {


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
        var view:View = View.inflate(context, R.layout.bag_cardview,null)

        var icons:ImageView = view.findViewById(R.id.grid_img)
        var names:TextView =view.findViewById(R.id.grid_tv)

        var item: ItemBag = arrayList.get(position)

        //icons.setImageResource(item.icons!!)
        //names.text = item.name

        return view
    }


}