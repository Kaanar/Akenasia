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
import kotlinx.android.synthetic.main.forge_dialog.view.*
import kotlinx.android.synthetic.main.shop_dialog.view.*

class ShopDialog : DialogFragment() {
    private lateinit var itemHandler : ItemHandler
    private lateinit var item : Item
    private lateinit var personnage: PersonnageHandler
    private lateinit var currentPersonnage: PersonnageTable
    private lateinit var shop : Shop


    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, saveInstanceState : Bundle?) :
            View? {
        itemHandler = ItemHandler(context!!)
        itemHandler = ItemHandler(context!!)
        personnage = PersonnageHandler(context!!)
        currentPersonnage = personnage.get(1)

        var rootView: View = inflater.inflate(R.layout.shop_dialog, container, false)
        //On affiche les infos de l'item à améliorer
        rootView.ShopDialogTitle.text = "Nom de l'item : " +item.getItemName()
        rootView.ShopDialogDesc.text = "Description : " + item.getItemDesc()
        rootView.ShopDialogCout.text = "Cout de l'achat : " + item.getItemPrix()

        rootView.ShopDialogOk.setOnClickListener() {
            if(currentPersonnage.argent<item.getItemPrix()) {
                Toast.makeText(context, "Pas assez d'argent", Toast.LENGTH_LONG).show()
            }
            else {
                item.Itemid = this.itemHandler.view().last().getItemid() + 1
                personnage.upArgent(currentPersonnage.argent-item.getItemPrix())
                currentPersonnage = personnage.get(1)
                this.itemHandler.add(item)
                Toast.makeText(context, "L'achat a été effectué",Toast.LENGTH_LONG).show()
                shop.refresh()
                dismiss()
            }
        }

        rootView.ShopDialogCancel.setOnClickListener() {
            //Pour fermer le dialogue
            dismiss()
        }
        return rootView
    }

    fun setItem (i : Item) {
        item = i
    }

    fun setShop(shop: Shop) {
        this.shop = shop
    }
}


