package com.example.akenasia.openworld

import com.example.akenasia.database.Item
import com.example.akenasia.database.ListItems
import java.util.ArrayList

class ShopInit {
    private val listShop = ArrayList<Item>()

    fun initItem() : ArrayList<Item> {
        val potionSoin = Item(
            0,
            ListItems.POTION.toString(),
            "Potion de soin basique",
            "Soigne 10 PV",
            0.0,
            0.0,
            0,
            2000
        )
        val potionDef = Item(
            1,
            ListItems.POTION.toString(),
            "Potion de défense basique",
            "Augmente la DEF de 10",
            0.0,
            10.0,
            0,
            2000
        )
        val ticketEvent = Item(
            2,
            ListItems.TICKET.toString(),
            "Ticket d'event",
            "Un ticket pour participer à un événement",
            0.0,
            0.0,
            0,
            50000
        )
        val mobSpawner = Item(
            3,
            ListItems.GADGET.toString(),
            "Mob Spawner",
            "Invoque un monstre à côté de votre position",
            0.0,
            0.0,
            0,
            0
        )
        listShop.add(potionSoin)
        listShop.add(potionDef)
        listShop.add(ticketEvent)
        listShop.add(mobSpawner)
        return listShop
    }
}