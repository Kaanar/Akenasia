package com.example.akenasia.openworld

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
import kotlinx.android.synthetic.main.bag_dialog.view.*

class BagDialog : DialogFragment() {
    private lateinit var itemHandler : ItemHandler
    private lateinit var title : String
    private lateinit var item : Item
    private lateinit var bag : Bag

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, saveInstanceState : Bundle?) :
            View? {
        itemHandler = ItemHandler(context!!)

        var rootView: View = inflater.inflate(R.layout.bag_dialog, container, false)
        //On affiche les infos de l'item à améliorer
        rootView.BagDialogTitle.text = "Id de l'item : " +title
        rootView.BagDialogDesc.text = "Type d'item : " + item.getItemName()
        rootView.BagDialogAtk.text = "Atk actuelle : " +item.getItemAtt().toString()
        rootView.BagDialogDef.text = "Def actuelle : " +item.getItemDef().toString()

        rootView.BagDialogOk.setOnClickListener() {
            itemHandler.delete(item.getItemid())
            Toast.makeText(context, "Item "+ item.getItemid() +" supprimé", Toast.LENGTH_LONG).show()
            bag.refresh()
            dismiss()
        }

        rootView.BagDialogCancel.setOnClickListener() {
            //Pour fermer le dialogue
            dismiss()
        }
        return rootView
    }

    fun setBag(bag : Bag) {
        this.bag = bag
    }

    fun setItem (i : Item) {
        item = i
    }

    fun setTitle (i : String) {
        title = i
    }


}


