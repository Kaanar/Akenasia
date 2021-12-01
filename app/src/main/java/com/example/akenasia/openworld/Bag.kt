package com.example.akenasia.openworld

import ItemAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.R
import com.example.akenasia.database.DatabaseHandler
import com.example.akenasia.database.Item
import com.example.akenasia.database.ListItems
import com.example.akenasia.databinding.BagBinding
import com.example.akenasia.home.MainActivity
import kotlinx.android.synthetic.main.bag.*
import java.util.ArrayList

class Bag : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var binding: BagBinding


    private var items: ArrayList<Item>? = null
    private lateinit var dbHandler : DatabaseHandler
    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BagBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHandler = DatabaseHandler(applicationContext)

        // Instanciation des items en dur
        items = ArrayList<Item>()
        if(dbHandler.viewItem().isEmpty()) {

            dbHandler.addItem(Item(0,ListItems.POTION.toString(), "Potion", "Yo"))
            dbHandler.addItem(Item(1,ListItems.EPEE.toString(), "Epee", "blague humour noir epee"))
            dbHandler.addItem(Item(2,ListItems.ARMURE.toString(), "Armure", "blague humour noire armure"))
            dbHandler.addItem(Item(3,ListItems.BOUCLIER.toString(), "Bouclier", "blague hurmour noire bouclier"))
        }
        viewRecord()
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
                else -> {
                    val intent = Intent(this, Personnage::class.java)
                    this.startActivity(intent)
                }
            }
            true
        }

    }

    private fun viewRecord(){
        //creating the instance of DatabaseHandler class
        val databaseHandler = DatabaseHandler(applicationContext)
        //calling the viewPlace method of DatabaseHandler class to read the records
        val emp: List<Item> = databaseHandler.viewItem()
        val empArrayId = Array<String>(emp.size){"0"}
        val empArrayName = Array<String>(emp.size){"null"}
        val empArrayDesc = Array<String>(emp.size){"null"}

        var index = 0
        for(e in emp){
            empArrayId[index] = e.Itemid.toString()
            empArrayName[index] = e.ItemName
            empArrayDesc[index] = e.ItemDesc

            index++
        }
        //creating custom ArrayAdapter
        val myListAdapter = ItemAdapter(this,empArrayId,empArrayName, empArrayDesc)
        ListViewItem?.adapter = myListAdapter
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val databaseHandler= DatabaseHandler(applicationContext)
        databaseHandler.deleteItem(p2)
        orderRecord(p2)
        viewRecord()
    }

    private fun orderRecord(deleteId: Int) {
        val databaseHandler = DatabaseHandler(applicationContext)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val emp: List<Item> = databaseHandler.viewItem()
        val empArrayId = Array<String>(emp.size) { "0" }
        val empArrayName = Array<String>(emp.size) { "null" }
        val empArrayDesc = Array<String>(emp.size) { "null" }

        var index = 0
        for (e in emp) {
            empArrayId[index] = e.Itemid.toString()
            empArrayName[index] = e.ItemName
            empArrayDesc[index] = e.ItemDesc
            if (e.Itemid < deleteId) {
                //Toast.makeText(this, e.Itemid.toString(), Toast.LENGTH_LONG).show()
            } else {
                if (e.Itemid.toString().trim() != "") {
                    databaseHandler.updateItem(
                        Item(e.Itemid,
                            e.ItemType,
                            e.ItemName,
                            e.ItemDesc,
                        )
                    )
                }
            }
            index++
        }
    }
}