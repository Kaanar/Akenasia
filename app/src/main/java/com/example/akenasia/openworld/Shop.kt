package com.example.akenasia.openworld

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.Handler.DatabaseHandler
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.Handler.PersonnageHandler
import com.example.akenasia.R
import com.example.akenasia.adapter.ShopListAdapter
import com.example.akenasia.database.Item
import com.example.akenasia.database.ItemType
import com.example.akenasia.database.ListItems
import com.example.akenasia.database.PersonnageTable
import com.example.akenasia.databinding.ShopBinding
import com.example.akenasia.home.MainActivity
import kotlinx.android.synthetic.main.bag.*
import kotlinx.android.synthetic.main.shop.*
import java.util.ArrayList


class Shop : AppCompatActivity(), AdapterView.OnItemClickListener,
    AdapterView.OnItemSelectedListener {

    private lateinit var binding: ShopBinding
    private lateinit var itemHandler: ItemHandler
    private lateinit var type: ListItems
    private lateinit var personnage: PersonnageHandler
    private lateinit var currentPersonnage: PersonnageTable
    private lateinit var listShop : ArrayList<Item>

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemHandler = ItemHandler(applicationContext)
        personnage = PersonnageHandler(this)
        currentPersonnage=personnage.get(1)
        listShop = ShopInit().initItem()
        ShopArgentTxt.text = currentPersonnage.getArgent().toString()

        val spinnerList = ArrayList<ListItems>()
        spinnerList.add(ListItems.POTION)
        spinnerList.add(ListItems.GADGET)
        spinnerList.add(ListItems.TICKET)

        val spinner: Spinner = findViewById(R.id.ShopSpinner)
        spinner.onItemSelectedListener = this
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            spinnerList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        ShopListViewItem.onItemClickListener = this

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

    private fun searchRecord(type : ListItems): List<Item> {
        val emp: List<Item> = listShop
        val final = ArrayList<Item>()
        for (e in emp) {
            if(e.ItemType == type.toString()) {
                final.add(e)
            }
        }
        return final
    }

    private fun viewRecord(emp : List<Item>) {
        val empArrayId = Array(emp.size) { "0" }
        val empArrayName = Array(emp.size) { "null" }
        val empArrayDesc = Array(emp.size) { "null" }
        val empArrayPrix = Array(emp.size) { "null" }

        var index = 0
        for (e in emp) {
            empArrayId[index] = e.Itemid.toString()
            empArrayName[index] = e.ItemName
            empArrayDesc[index] = e.ItemDesc
            empArrayPrix[index] = e.ItemPrix.toString()
            index++
        }
        //creating custom ArrayAdapter
        val myListAdapter =
            ShopListAdapter(this, empArrayId, empArrayName, empArrayDesc, empArrayPrix)
        ShopListViewItem?.adapter = myListAdapter

    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val a = Integer.parseInt(ShopListViewItem.getItemAtPosition(p2).toString())
        Toast.makeText(this, a.toString(), Toast.LENGTH_LONG).show()
        val i = listShop[a]

        //On envoie les infos de l'item au dialog
        val dialog = ShopDialog()
        dialog.setItem(updateItem(i))
        dialog.setShop(updateShop())
        val navHostFragment = supportFragmentManager
        dialog.show(navHostFragment, "ShopDialog")
    }

    fun updateItem(item : Item) : Item {
        return item
    }

    fun refresh() {
        currentPersonnage = personnage.get(1)
        ShopArgentTxt.text = currentPersonnage.getArgent().toString()
    }

    fun updateShop() : Shop {
        return this
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val a = ShopSpinner.getItemAtPosition(p2)
        viewRecord(searchRecord(a as ListItems))
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        //Toast.makeText(this, "Rien", Toast.LENGTH_LONG).show()
    }
}
