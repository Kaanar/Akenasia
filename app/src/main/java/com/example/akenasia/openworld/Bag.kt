package com.example.akenasia.openworld

import com.example.akenasia.adapter.ItemAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.handler.DatabaseHandler
import com.example.akenasia.R
import com.example.akenasia.database.Item
import com.example.akenasia.handler.ItemHandler
import com.example.akenasia.database.ListItems
import com.example.akenasia.databinding.BagBinding
import com.example.akenasia.home.MainActivity
import kotlinx.android.synthetic.main.bag.*
import java.util.ArrayList

class Bag : AppCompatActivity(), AdapterView.OnItemClickListener,
    AdapterView.OnItemSelectedListener {

    private lateinit var binding: BagBinding
    private lateinit var itemHandler : ItemHandler
    private lateinit var type : ListItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BagBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemHandler = ItemHandler(applicationContext)

        val spinnerList = ArrayList<ListItems>()
        spinnerList.add(ListItems.POTION)
        spinnerList.add(ListItems.GADGET)
        spinnerList.add(ListItems.TICKET)
        spinnerList.add(ListItems.BOUCLIER)
        spinnerList.add(ListItems.EPEE)
        spinnerList.add(ListItems.ARMURE)
        spinnerList.add(ListItems.CHAUSSURES)

        val spinner: Spinner = findViewById(R.id.BagSpinner)
        spinner.onItemSelectedListener = this
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            spinnerList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        ListViewItem.onItemClickListener = this

        binding.NavigationView.selectedItemId = R.id.BagClick

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
                R.id.ForgeClick -> {
                    val intent = Intent(this, Forge::class.java)
                    this.startActivity(intent)
                }

                else -> {
                    val intent = Intent(this, Personnage::class.java)
                    this.startActivity(intent)
                }
            }
            true
        }
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
        ListViewItem?.adapter = myListAdapter

    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val a = Integer.parseInt(ListViewItem.getItemAtPosition(p2).toString())
        val i = itemHandler.get(a)

        //On envoie les infos de l'item au dialog
        val dialog = BagDialog()
        dialog.setItem(updateItem(i))
        dialog.setTitle(updateTitle(i.getItemid()))
        dialog.setBag(updateBag())
        val navHostFragment = supportFragmentManager
        dialog.show(navHostFragment, "BagDialog")
    }

    fun refresh() {
        searchRecord()
    }

    fun updateBag() : Bag {
        return this
    }

    fun updateItem(item : Item) : Item {
        return item
    }

    fun updateTitle(title : Int) : String {
        return title.toString()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val a = BagSpinner.getItemAtPosition(p2)
        type = a as ListItems
        searchRecord()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}