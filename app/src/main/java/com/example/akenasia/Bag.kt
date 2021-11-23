package com.example.akenasia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.akenasia.databinding.BagCardviewBinding
import com.example.akenasia.databinding.ChronometreBinding

class Bag() : AppCompatActivity() , AdapterView.OnItemClickListener{
    private lateinit var binding: BagCardviewBinding

    private var gridView:GridView ? = null
    private var arrayList : ArrayList<ItemBag> ? = null
    private var itemAdapter:ItemAdapter ? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BagCardviewBinding.inflate(layoutInflater)
        setContentView(R.layout.bag)

        gridView = findViewById(R.id.grid_v)
        arrayList = ArrayList()
        arrayList = setdataList()
        itemAdapter = ItemAdapter(applicationContext, arrayList!!)
        gridView?.adapter = itemAdapter
        gridView?.onItemClickListener = this

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


        var itemBag:ItemBag = arrayList!!.get(position)
        Toast.makeText(applicationContext, itemBag.name, Toast.LENGTH_LONG).show()
    }
}