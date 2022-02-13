package com.example.akenasia.openworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.Handler.PersonnageHandler
import com.example.akenasia.database.Item
import com.example.akenasia.database.ListItems
import com.example.akenasia.database.PersonnageTable
import com.example.akenasia.databinding.ShopBinding
import java.util.ArrayList


class Shop : AppCompatActivity() {

    private lateinit var binding: ShopBinding
    private lateinit var itemHandler: ItemHandler
    private lateinit var type: ListItems
    private lateinit var personnage: PersonnageHandler
    private lateinit var currentPersonnage: PersonnageTable
    private lateinit var listShop : ArrayList<Item>

    private lateinit var potionSoin : Item
    private lateinit var potionDef : Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemHandler = ItemHandler(applicationContext)
        personnage = PersonnageHandler(this)
        currentPersonnage=personnage.get(1)
        getId()



        binding.ShopButtonPotionPV.setOnClickListener() {
            getId()
            this.itemHandler.add(potionSoin)
        }

        binding.ShopButtonPotionDef.setOnClickListener() {
            getId()
            this.itemHandler.add(potionDef)
        }
    }

    fun getId() {
        var id: Int
        try {
            id = itemHandler.view().last().getItemid() + 1
        } catch (e: java.util.NoSuchElementException) {
            id = 1
        }
        potionSoin = Item(
            id,
            ListItems.POTION.toString(),
            "Potion de soin basique",
            "Soigne 10 PV",
            0.0,
            0.0,
            0,
            1000
        )
        potionDef = Item(
            id,
            ListItems.POTION.toString(),
            "Potion de défense basique",
            "Augmente la DEF de 10",
            0.0,
            10.0,
            0,
            1000
        )
        listShop.add(potionDef)
        listShop.add(potionSoin)
    }
}
