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
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import java.util.ArrayList
import kotlinx.android.synthetic.main.place_listview.name;
import kotlinx.android.synthetic.main.place_listview.longitude;
import kotlinx.android.synthetic.main.place_listview.latitude;





class PlaceAdapter : BaseAdapter() {
    lateinit var context: Context
    lateinit var place: ArrayList<Place>
    lateinit var inflater: LayoutInflater
    lateinit var DbContext: DatabaseHandler

    fun PlaceAdapter(context: Context,places: ArrayList<Place>) {
        this.context=context
        this.place=places
        inflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return place.size
    }

    override fun getItem(position: Int): Any {
        return place.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        DbContext = DatabaseHandler(context)
        var conView = convertView
        conView= inflater.inflate(R.layout.place_listview, null)
        val nomLieu = conView.findViewById<View>(R.id.name) as TextView
        nomLieu.text =place.get(position).getPlaceName()
        val longitude = conView.findViewById<View>(R.id.longitude) as TextView
        longitude.text= place.get(position).getPlaceLong().toString()
        val latitude = conView.findViewById<View>(R.id.latitude ) as TextView
        latitude.text=place.get(position).getPlaceLat().toString()

        val choisir = conView.findViewById<View>(R.id.choisir) as Button
        choisir.setOnClickListener(){
            //conView.findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        return conView
    }

}