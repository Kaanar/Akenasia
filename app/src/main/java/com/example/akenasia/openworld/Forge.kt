package com.example.akenasia.openworld

import ItemAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.Handler.DatabaseHandler
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.R
import com.example.akenasia.database.Item
import com.example.akenasia.database.ListItems
import com.example.akenasia.databinding.ForgeBinding
import com.example.akenasia.home.MainActivity
import kotlinx.android.synthetic.main.bag.*
import kotlinx.android.synthetic.main.forge.*


class Forge :  AppCompatActivity(), AdapterView.OnItemClickListener {
    private lateinit var binding: ForgeBinding
    private lateinit var itemHandler: ItemHandler
    private lateinit var type : ListItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ForgeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemHandler = ItemHandler(applicationContext)
        //On affiche tous les items sans filtre
        classicRecord()
        //Implémentation des différents choix du menu
        ListViewItemForge.onItemClickListener = this
        binding.NavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.QuitClick -> {
                    val intent = Intent(this, MainActivity::class.java)
                    this.startActivity(intent)
                }
                R.id.MapClick -> {
                    val intent = Intent(this, OpenWorld::class.java)
                    this.startActivity(intent)
                }
                R.id.BagClick -> {
                    val intent = Intent(this, Bag::class.java)
                    this.startActivity(intent)
                }
                else -> {
                    val intent = Intent(this, Personnage::class.java)
                    this.startActivity(intent)
                }
            }
            true
        }

        //Chaque bouton permet d'appliquer un filtre en fonction du type de l'objet
        binding.CBEpee.setOnClickListener {
            if(CBEpee.isChecked) {
                type = ListItems.EPEE
                searchRecord()
            }
            else {
                classicRecord()
            }
        }

        binding.CBBouclier.setOnClickListener {
            if(CBBouclier.isChecked) {
                type = ListItems.BOUCLIER
                searchRecord()
            }
            else {
                classicRecord()
            }
        }

        binding.CBChaussures.setOnClickListener {
            if(CBChaussures.isChecked) {
                type = ListItems.CHAUSSURES
                searchRecord()
            }
            else {
                classicRecord()
            }
        }

        binding.CBArmure.setOnClickListener {
            if(CBArmure.isChecked) {
                type = ListItems.ARMURE
                searchRecord()
            }
            else {
                classicRecord()
            }
        }
    }

    //Afficher tous les items sans filtre
    private fun classicRecord() {
        //creating the instance of DatabaseHandler class
        val databaseHandler = DatabaseHandler(applicationContext)
        //calling the viewPlace method of DatabaseHandler class to read the records
        val emp: List<Item> = itemHandler.view()
        viewRecord(emp)
    }

    //Affiche les objets par type
    private fun searchRecord() {
        val databaseHandler = DatabaseHandler(applicationContext)
        val emp: List<Item> = itemHandler.viewByType(type)
        viewRecord(emp)
    }

    private fun viewRecord(emp : List<Item>) {
        val empArrayId = Array(emp.size) { "0" }
        val empArrayName = Array(emp.size) { "null" }
        val empArrayDesc = Array(emp.size) { "null" }
        val empArrayAtt = Array(emp.size) { "null" }
        val empArrayDef = Array(emp.size) { "null" }

        var index = 0
        for (e in emp) {
            empArrayId[index] = e.Itemid.toString()
            empArrayName[index] = e.ItemName
            empArrayDesc[index] = e.ItemDesc
            empArrayAtt[index] = e.ItemAtt.toString()
            empArrayDef[index] = e.ItemDef.toString()
            index++
        }
        //creating custom ArrayAdapter
        val myListAdapter =
            ItemAdapter(this, empArrayId, empArrayName, empArrayDesc, empArrayAtt, empArrayDef)
        ListViewItemForge?.adapter = myListAdapter

    }

    //On récupère les infos en bdd de l'item sur lequel on clique
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val i = itemHandler.get(p2+1)
        //Toast.makeText(this, p2.toString(),Toast.LENGTH_LONG).show()
        itemHandler.upItem(i)
        classicRecord()
    }

}