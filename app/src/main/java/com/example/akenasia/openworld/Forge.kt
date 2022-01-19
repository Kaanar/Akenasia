package com.example.akenasia.openworld

import ItemAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.Handler.DatabaseHandler
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.R
import com.example.akenasia.database.Item
import com.example.akenasia.database.ListItems
import com.example.akenasia.databinding.ForgeBinding
import com.example.akenasia.home.MainActivity
import kotlinx.android.synthetic.main.forge.*
import kotlinx.android.synthetic.main.regles_generales.*
import java.util.ArrayList


class Forge :  AppCompatActivity() {
    private lateinit var binding: ForgeBinding
    private lateinit var itemHandler: ItemHandler
    private lateinit var type : ListItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ForgeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemHandler = ItemHandler(applicationContext)
        classicRecord()
        //Implémentation des différents choix du menu
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

    private fun classicRecord() {
        //creating the instance of DatabaseHandler class
        val databaseHandler = DatabaseHandler(applicationContext)
        //calling the viewPlace method of DatabaseHandler class to read the records
        val emp: List<Item> = itemHandler.view()
        viewRecord(emp)
    }

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

}