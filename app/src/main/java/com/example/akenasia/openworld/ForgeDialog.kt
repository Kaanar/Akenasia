package com.example.akenasia.openworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.akenasia.R
import com.example.akenasia.database.Item
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.Handler.PersonnageHandler
import com.example.akenasia.database.PersonnageTable
import kotlinx.android.synthetic.main.forge_dialog.view.*

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
        rootView.ForgeDialogTitle.text = "Id de l'item : " +title
        rootView.ForgeDialogDesc.text = "Type d'item : " + item.getItemName()
        rootView.ForgeDialogAtk.text = "Atk actuelle : " +item.getItemAtt().toString()
        rootView.ForgeDialogDef.text = "Def actuelle : " +item.getItemDef().toString()
        rootView.ForgeDialogCout.text = "Cout de l'upgrade : " + upgradeCost(item.nb_Upgrade).toString()
        rootView.ForgeDialogNiveau.text = "Level : " + item.getItemNbUpgrade().toString() + "/5"

        rootView.ForgeDialogOk.setOnClickListener() {
            if(currentPersonnage.getArgent()<upgradeCost(item.nb_Upgrade)) {
                Toast.makeText(context, "Pas assez d'argent", Toast.LENGTH_LONG).show()
            }
            else if(item.getItemNbUpgrade()>=5) {
                Toast.makeText(context, "Niveau max atteint", Toast.LENGTH_LONG).show()
            }
            else {
                personnage.upArgent(currentPersonnage.getArgent()-upgradeCost(item.nb_Upgrade))
                currentPersonnage = personnage.get(1)
                //Toast.makeText(this, a.toString(),Toast.LENGTH_LONG).show()
                itemHandler.upItem(item)
                dismiss()
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

    fun setTitle (i : String) {
        title = i
    }

    fun upgradeCost(nb_Updgrade: Int): Int {
        return 1500 * (nb_Updgrade + 1) * 2 * (nb_Updgrade+1)
    }
}


