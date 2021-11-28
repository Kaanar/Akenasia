package com.example.akenasia.openworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import com.example.akenasia.database.ItemBag
import com.example.akenasia.R
import com.example.akenasia.adapter.ItemAdapter
import com.example.akenasia.databinding.BagBinding
import com.example.akenasia.home.MainActivity

class Bag() : AppCompatActivity() , AdapterView.OnItemClickListener{
    private lateinit var binding: BagBinding

    private var gridView:GridView ? = null
    private var arrayList : ArrayList<ItemBag> ? = null
    private var itemAdapter: ItemAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BagBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gridView = findViewById(R.id.grid_v)
        arrayList = ArrayList()
        arrayList = setdataList()
        itemAdapter = ItemAdapter(applicationContext, arrayList!!)
        gridView?.adapter = itemAdapter
        gridView?.onItemClickListener = this


        binding.NavigationView.selectedItemId = R.id.BagClick


        //Implémentation des différents choix du menu
        binding.NavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.QuitClick -> {
                    val intent = Intent(this, MainActivity::class.java)
                    this.startActivity(intent)
                    true
                }
                R.id.MapClick -> {
                    val intent = Intent(this, OpenWorld::class.java)
                    this.startActivity(intent)
                    true
                }
                R.id.BagClick -> {
                    val intent = Intent(this, Bag::class.java)
                    this.startActivity(intent)
                    true
                }
                else -> {
                    val intent = Intent(this, Personnage::class.java)
                    this.startActivity(intent)
                    true
                }
            }
            true
        }

    }

    private fun setdataList():ArrayList<ItemBag>{

        var arrayList:ArrayList<ItemBag> = ArrayList()

        arrayList.add(ItemBag(R.drawable.potion, "Potion"))
        arrayList.add(ItemBag(R.drawable.epee, "Epee"))
        arrayList.add(ItemBag(R.drawable.bouclier, "Bouclier"))
        arrayList.add(ItemBag(R.drawable.armure, "Armure"))

        return arrayList
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


        var itemBag: ItemBag = arrayList!!.get(position)
        Toast.makeText(applicationContext, itemBag.name, Toast.LENGTH_LONG).show()
    }
}