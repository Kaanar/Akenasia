
package com.example.akenasia.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.akenasia.database.ItemBag
import com.example.akenasia.R

class ItemAdapter(var context:Context, var arrayList: ArrayList<ItemBag>) : BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View = View.inflate(context, R.layout.bag_cardview,null)

        val icons:ImageView = view.findViewById(R.id.grid_img)
        val names:TextView =view.findViewById(R.id.grid_tv)

        val item: ItemBag = arrayList.get(position)

        when (item.getItemName()) {
            "épée" -> icons.setImageResource(R.drawable.epee)
            "potion" -> icons.setImageResource(R.drawable.potion)
            "bouclier" -> icons.setImageResource(R.drawable.bouclier)
            "armure"-> icons.setImageResource(R.drawable.armure)
            else -> { // Note the block
                icons.setImageResource(R.drawable.ic_settings)
            }
        }
        names.text = item.getItemName()

        return view
    }


}