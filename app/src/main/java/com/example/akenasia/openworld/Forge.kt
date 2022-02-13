package com.example.akenasia.openworld


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.Handler.DatabaseHandler
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.Handler.PersonnageHandler
import com.example.akenasia.R
import com.example.akenasia.adapter.ItemAdapter
import com.example.akenasia.database.Item
import com.example.akenasia.database.ListItems
import com.example.akenasia.database.PersonnageTable
import com.example.akenasia.databinding.ForgeBinding
import com.example.akenasia.home.MainActivity
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.forge.*
import kotlinx.android.synthetic.main.forge_dialog.*


class Forge :  AppCompatActivity(), AdapterView.OnItemClickListener {
    private lateinit var binding: ForgeBinding
    private lateinit var itemHandler: ItemHandler
    private lateinit var type : ListItems
    private lateinit var personnage: PersonnageHandler
    private lateinit var currentPersonnage: PersonnageTable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ForgeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemHandler = ItemHandler(applicationContext)
        personnage = PersonnageHandler(this)
        //On affiche toutes les epees (filtre par défaut)
        type = ListItems.EPEE
        searchRecord()
        //Implémentation des différents choix du menu
        ListViewItemForge.onItemClickListener = this
        if (personnage.view().isEmpty()) {
            personnage.create()
        }
        currentPersonnage=personnage.get(1)

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

        binding.ArgentTxt.text = currentPersonnage.getArgent().toString()
        //Chaque bouton permet d'appliquer un filtre en fonction du type de l'objet
        binding.CBEpee.setOnClickListener {
            if (CBEpee.isChecked) {
                type = ListItems.EPEE
                searchRecord()
            }
        }

        binding.CBBouclier.setOnClickListener {
            if(CBBouclier.isChecked) {
                type = ListItems.BOUCLIER
                searchRecord()
            }
        }

        binding.CBChaussures.setOnClickListener {
            if(CBChaussures.isChecked) {
                type = ListItems.CHAUSSURES
                searchRecord()
            }
        }

        binding.CBArmure.setOnClickListener {
            if(CBArmure.isChecked) {
                type = ListItems.ARMURE
                searchRecord()
            }
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
        ListViewItemForge?.adapter = myListAdapter

    }

    //On récupère les infos en bdd de l'item sur lequel on clique
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val a = Integer.parseInt(ListViewItemForge.getItemAtPosition(p2).toString())
        val i = itemHandler.get(a)

        //On envoie les infos de l'item au dialog
        val dialog = ForgeDialog()
        dialog.setItem(updateItem(i))
        dialog.setTitle(updateTitle(i.getItemid()))
        dialog.setForge(updateForge())
        val navHostFragment = supportFragmentManager
        dialog.show(navHostFragment, "ForgeDialog")
    }

    fun updateItem(item : Item) : Item {
        return item
    }

    fun refresh() {
        searchRecord()
        currentPersonnage = personnage.get(1)
        ArgentTxt.text = currentPersonnage.getArgent().toString()
    }

    fun updateForge() : Forge {
        return this
    }

    fun updateTitle(title : Int) : String {
        return title.toString()
    }

}