package com.example.akenasia.openworld

import ItemTypeAdapter
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.akenasia.R
import com.example.akenasia.database.Item
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.Handler.PersonnageHandler
import com.example.akenasia.database.PersonnageTable
import kotlinx.android.synthetic.main.bag.*
import kotlinx.android.synthetic.main.forge_dialog.view.*
import kotlinx.android.synthetic.main.item_dialog.view.*
import kotlinx.android.synthetic.main.poi_dialog.view.*
import java.io.Serializable

class ForgeDialog : DialogFragment() {
    private lateinit var itemHandler : ItemHandler
    private lateinit var title : String
    private lateinit var item : Item
    private lateinit var personnage: PersonnageHandler
    private lateinit var currentPersonnage: PersonnageTable



    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, saveInstanceState : Bundle?) :
            View? {
        itemHandler = ItemHandler(context!!)
        itemHandler = ItemHandler(context!!)
        personnage = PersonnageHandler(context!!)
        currentPersonnage = personnage.get(1)

        var rootView: View = inflater.inflate(R.layout.forge_dialog, container, false)
        val coutUpgrade = 1500
        //On affiche les infos de l'item à améliorer
        rootView.ForgeDialogTitle.text = "title"
        rootView.ForgeDialogDesc.text = item.getItemName()
        rootView.ForgeDialogAtk.text = item.getItemAtt().toString()
        rootView.ForgeDialogDef.text = item.getItemDef().toString()
        rootView.ForgeDialogCout.text = "1500"

        rootView.ForgeDialogOk.setOnClickListener() {
            if(currentPersonnage.getArgent()<coutUpgrade) {
                Toast.makeText(context, "Trop pauvre", Toast.LENGTH_LONG).show()
            }
            else if(item.getItemNbUpgrade()>=5) {
                Toast.makeText(context, "Niveau max atteint", Toast.LENGTH_LONG).show()
            }
            else {
                personnage.upArgent(currentPersonnage.getArgent()-coutUpgrade)
                currentPersonnage = personnage.get(1)
                //Toast.makeText(this, a.toString(),Toast.LENGTH_LONG).show()
                itemHandler.upItem(item)
            }

        }
        rootView.ForgeDialogCancel.setOnClickListener() {
            //Pour fermer le dialogue
            dismiss()
        }
        return rootView
    }

    fun setItem (i : Item) {
        item = i
    }
}


