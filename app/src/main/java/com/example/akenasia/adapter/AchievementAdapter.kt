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

class AchievementAdapter (private val context: Activity,private val id: ArrayList<String>, private val description: ArrayList<String>, private val unlocked: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.achievements_listview,id) {


    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.achievements_listview, null, true)

        val descriptionTv = rowView.findViewById<View>(R.id.achievement_description) as TextView
        val unlockedTv = rowView.findViewById<View>(R.id.achievement_unlocked) as TextView
        val unlockedImage = rowView.findViewById<View>(R.id.achievement_unlocked_image) as ImageView

        descriptionTv.text=description[position]
        when(unlocked[position].toInt()){
            0->{
                unlockedTv.text="Succès vérouillé"
                unlockedImage.setImageResource(R.drawable.ic_unchecked)
            }
            1->{
                unlockedTv.text="Succès dévérouillé!"
                unlockedImage.setImageResource(R.drawable.ic_checked)
            }
        }

        return rowView
    }
}