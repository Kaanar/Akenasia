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

class AchievementAdapter (private val context: Activity, private val description: Array<String>, private val unlocked: Array<Int>)
    : ArrayAdapter<String>(context, R.layout.achievements_listview) {


    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.achievements_listview, null, true)

        val description_tv = rowView.findViewById<View>(R.id.achievement_description) as TextView
        val unlocked_tv = rowView.findViewById<View>(R.id.achievement_unlocked) as TextView
        val unlocked_image = rowView.findViewById<View>(R.id.achievement_unlocked_image) as ImageView

        description_tv.text=description[position]
        when(unlocked[position]){
            0->{
                unlocked_tv.text="Succès vérouillé"
                unlocked_image.setImageResource(R.drawable.ic_unchecked)
            }
            1->{
                unlocked_tv.text="Succès dévérouillé!"
                unlocked_image.setImageResource(R.drawable.ic_checked)
            }
        }

        return rowView
    }
}