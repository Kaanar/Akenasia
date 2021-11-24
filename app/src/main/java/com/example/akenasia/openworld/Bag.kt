package com.example.akenasia.openworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import com.example.akenasia.database.ItemBag
import com.example.akenasia.R
import com.example.akenasia.adapter.ItemBagAdapter
import com.example.akenasia.databinding.BagBinding

class Bag() : AppCompatActivity() , AdapterView.OnItemClickListener{
    private lateinit var binding: BagBinding

    private var gridView:GridView ? = null
    private var arrayList : ArrayList<ItemBag> ? = null
    private var itemBagAdapter: ItemBagAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BagBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gridView = findViewById(R.id.grid_v)
        arrayList = ArrayList()
        arrayList = setdataList()
        itemBagAdapter = ItemBagAdapter(applicationContext, arrayList!!)
        gridView?.adapter = itemBagAdapter
        gridView?.onItemClickListener = this

    }

    private fun setdataList():ArrayList<ItemBag>{

        var arrayList:ArrayList<ItemBag> = ArrayList()

        //arrayList.add(ItemBag(R.drawable.potion, "Potion"))
        //arrayList.add(ItemBag(R.drawable.epee, "Epee"))
        //arrayList.add(ItemBag(R.drawable.bouclier, "Bouclier"))
        //arrayList.add(ItemBag(R.drawable.armure, "Armure"))

        return arrayList
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


        var itemBag: ItemBag = arrayList!!.get(position)
        //Toast.makeText(applicationContext, itemBag.name, Toast.LENGTH_LONG).show()
    }

}