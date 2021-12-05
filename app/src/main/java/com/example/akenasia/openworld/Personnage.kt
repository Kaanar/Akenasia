package com.example.akenasia.openworld

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.database.DatabaseHandler
import com.example.akenasia.database.ListItems
import com.example.akenasia.database.PersonnageTable
import com.example.akenasia.databinding.PersonnageBinding
import com.example.akenasia.home.MainActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.akenasia.R
import com.example.akenasia.databinding.ItemDialogBinding
import kotlinx.android.synthetic.main.item_dialog.*


class Personnage: AppCompatActivity() {
    private lateinit var binding: PersonnageBinding
    private lateinit var dbHandler: DatabaseHandler
    private lateinit var currentPersonnage: PersonnageTable



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PersonnageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHandler = DatabaseHandler(this)


        binding.NavigationView.selectedItemId = R.id.PersonnageClick

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
        inflateStats()

        //Affiche les armures du sac
        binding.armure.setOnClickListener{
            val infos= ItemDialog()
            val navHostFragment = supportFragmentManager
            val bundle = Bundle()
            bundle.putSerializable("type",ListItems.ARMURE)
            infos.arguments=bundle
            infos.show(navHostFragment,"ItemDialog")
            infos.setText("Liste de vos armures")
        }
        //Affiche les épées du sac
        binding.epee.setOnClickListener{
            val infos= ItemDialog()
            val navHostFragment = supportFragmentManager
            val bundle = Bundle()
            bundle.putSerializable("type",ListItems.EPEE)
            infos.arguments=bundle
            infos.show(navHostFragment,"ItemDialog")
            infos.setText("Liste de vos épées")
        }
        //Affiche les boucliers du sac
        binding.bouclier.setOnClickListener{
            val infos= ItemDialog()
            val navHostFragment = supportFragmentManager
            val bundle = Bundle()
            bundle.putSerializable("type",ListItems.BOUCLIER)
            infos.arguments=bundle
            infos.show(navHostFragment,"ItemDialog")
            infos.setText("Liste de vos boucliers")
        }
        //Affiche les chaussures du sac
        binding.chaussures.setOnClickListener{
            val infos= ItemDialog()
            val navHostFragment = supportFragmentManager
            val bundle = Bundle()
            bundle.putSerializable("type",ListItems.CHAUSSURES)
            infos.arguments=bundle
            infos.show(navHostFragment,"ItemDialog")
            infos.setText("Liste de vos chaussures")
        }


    }

    /*override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Toast.makeText(this,"Hello ça marche", Toast.LENGTH_LONG).show()

        val databaseHandler= DatabaseHandler(this)
        databaseHandler.updatePersonnage(databaseHandler.getItem(p2))

    }*/



    @SuppressLint("SetTextI18n")
    private fun inflateStats() {
        if (dbHandler.viewPersonnage().isEmpty()) {
            dbHandler.createPersonnage()
        }
        currentPersonnage=dbHandler.getPersonnage(1)

        //Affichage des stats du personnage
        binding.personnagePv.text="PV: "+currentPersonnage.getpersoHp().toString()
        binding.personnageAtt.text="ATT: "+currentPersonnage.getpersoAtt().toString()
        binding.personnageDef.text="DEF: "+currentPersonnage.getpersoDef().toString()

        //Affichage des stats de l'armure
        binding.armureAtt.text="ATT: "+dbHandler.getItem(currentPersonnage.armure).ItemAtt.toString()
        binding.armureDef.text="DEF: "+dbHandler.getItem(currentPersonnage.armure).ItemDef.toString()

        //Affichage des stats du bouclier
        binding.bouclierAtt.text="ATT: "+dbHandler.getItem(currentPersonnage.bouclier).ItemAtt.toString()
        binding.bouclierDef.text="DEF: "+dbHandler.getItem(currentPersonnage.bouclier).ItemDef.toString()


        //Affichage des stats de l'épée
        binding.epeeAtt.text="ATT: "+dbHandler.getItem(currentPersonnage.epee).ItemAtt.toString()
        binding.epeeDef.text="DEF: "+dbHandler.getItem(currentPersonnage.epee).ItemDef.toString()


        //Affichage des stats des chaussures
        binding.chaussuresAtt.text="ATT: "+dbHandler.getItem(currentPersonnage.chaussures).ItemAtt.toString()
        binding.chaussuresDef.text="DEF: "+dbHandler.getItem(currentPersonnage.chaussures).ItemDef.toString()

    }


}