package com.example.akenasia.openworld

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.akenasia.R
import com.example.akenasia.database.Item
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.achievement.Stats
import com.example.akenasia.database.ListItems
import kotlinx.android.synthetic.main.marker_dialog.*
import kotlinx.android.synthetic.main.marker_dialog.view.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.properties.Delegates

class MarkerDialog : DialogFragment () {
    private lateinit var title : String
    private lateinit var info : String
    private var cbtval by Delegates.notNull<Int>()
    private lateinit var itemHandler : ItemHandler


    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, saveInstanceState : Bundle?) :
            View? {
        val rootView: View = inflater.inflate(R.layout.marker_dialog, container, false)
        rootView.MarkerTitle.text = "Un slime !"
        rootView.MarkerInfo.text = "HP: 5   ATT:1   DEF:2"
        itemHandler = ItemHandler(context!!)

        rootView.BtnAttaque.setOnClickListener() {
            //On lance le combat
            BtnAttaque.visibility = View.INVISIBLE
            BtnFuite.visibility = View.INVISIBLE
            combatText.visibility = View.VISIBLE
            TextMarker.visibility = View.INVISIBLE

            cbtval = (0..2).random()


            if(cbtval == 0){
                defaiteText.visibility = View.VISIBLE
            }
            if(cbtval == 1 || cbtval == 2)
            {
                victoireText.visibility = View.VISIBLE
                var id:Int
                try{
                    id= itemHandler.view().last().getItemid()+1
                }
                catch (e:java.util.NoSuchElementException){
                    id=1
                }
                val pick: Int = Random().nextInt(ListItems.values().size)
                val att = ThreadLocalRandom.current().nextInt(0,5)
                val def = ThreadLocalRandom.current().nextInt(0,5)
                this.itemHandler.add(Item(id, ListItems.values()[pick].toString(),"Un item surprise!","A voir où vous allez pouvoir l'équiper",att.toDouble(),def.toDouble()))
                Toast.makeText(context,"Vous avez gagné un item surprise", Toast.LENGTH_LONG).show()
                //MAJ des stats, +1 monstre vaincu et +1 item récupéré
                Stats(context!!, 1).upMonstres()
                Stats(context!!,1).upItems()
            }
        }
        rootView.BtnFuite.setOnClickListener() {
            //On ferme le dialogue quand on clique sur le bouton (on ne récupère pas d'objet)
            dismiss()
        }
        return rootView
    }

    fun setTitle (message : String) {
        title = message
    }
    fun setInfo (message : String) {
        info = message
    }
}
