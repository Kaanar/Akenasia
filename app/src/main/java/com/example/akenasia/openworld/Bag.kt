package com.example.akenasia.openworld

import com.example.akenasia.adapter.ItemAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.R
import com.example.akenasia.database.Item
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.databinding.BagBinding
import com.example.akenasia.home.MainActivity
import kotlinx.android.synthetic.main.bag.*

class Bag : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var binding: BagBinding
    private lateinit var itemHandler : ItemHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BagBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemHandler = ItemHandler(applicationContext)

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

    private fun viewRecord(){
        //calling the viewPlace method of DatabaseHandler class to read the records
        val emp: List<Item> = itemHandler.view()
        val empArrayId = Array(emp.size){"0"}
        val empArrayName = Array(emp.size){"null"}
        val empArrayDesc = Array(emp.size){"null"}
        val empArrayAtt = Array(emp.size){"null"}
        val empArrayDef = Array(emp.size){"null"}

        var index = 0
        for(e in emp){
            empArrayId[index] = e.Itemid.toString()
            empArrayName[index] = e.ItemName
            empArrayDesc[index] = e.ItemDesc
            empArrayAtt[index] = e.ItemAtt.toString()
            empArrayDef[index] = e.ItemDef.toString()
            index++
        }
        //creating custom ArrayAdapter
        val myListAdapter = ItemAdapter(this,empArrayId,empArrayName, empArrayDesc, empArrayAtt, empArrayDef)
        ListViewItem?.adapter = myListAdapter
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        itemHandler.delete(p2+1)
        viewRecord()
    }

}