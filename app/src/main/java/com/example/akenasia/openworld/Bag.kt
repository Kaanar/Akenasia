package com.example.akenasia.openworld

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

    }

    private fun setdataList():ArrayList<ItemBag>{

        var arrayList:ArrayList<ItemBag> = ArrayList()

        arrayList.add(ItemBag(1,"épée","ceci est une belle épée",))
        arrayList.add(ItemBag(2,"bouclier","ceci est une beau bouclier",))
        arrayList.add(ItemBag(3,"armure","ceci est une belle armure",))
        arrayList.add(ItemBag(4,"potion","ceci est une belle potion",))

        return arrayList
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


        var itemBag: ItemBag = arrayList!!.get(position)
        Toast.makeText(applicationContext, itemBag.getItemName(), Toast.LENGTH_LONG).show()
    }
}