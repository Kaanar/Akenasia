package com.example.akenasia

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import java.util.ArrayList
import kotlinx.android.synthetic.main.position_listview.id;
import kotlinx.android.synthetic.main.position_listview.longitude;
import kotlinx.android.synthetic.main.position_listview.latitude;





class PositionAdapter(private val context: Activity,private val id: Array<String>, private val lat: Array<String>, private val long: Array<String>, private val partie: Array<String>) : ArrayAdapter<String>(context, R.layout.custom_list, id) {
    lateinit var position: List<PositionTable>
    lateinit var inflater: LayoutInflater
    lateinit var DbContext: DatabaseHandler

    override fun getCount(): Int {
        return position.size
    }

    override fun getItem(position: Int): String {
        return this.position.get(position).toString()
    }

    override fun getItemId(position: Int): Long {
        return this.position.get(position).getposId().toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.position_listview, null, true)

        //Lie les éléments xml à l'adapter
        val idText = rowView.findViewById(R.id.id) as TextView
        val latText = rowView.findViewById(R.id.latitude) as TextView
        val longText = rowView.findViewById(R.id.longitude) as TextView

        //remplit les TextView par les valeurs des positions
        idText.text = id[position]
        latText.text = lat[position]
        longText.text = long[position]

        return rowView
    }

}