package com.example.akenasia.openworld

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.R
import com.example.akenasia.database.DatabaseHandler
import com.example.akenasia.database.Item
import com.example.akenasia.database.ListItems
import com.example.akenasia.database.PersonnageTable
import com.example.akenasia.databinding.PersonnageBinding
import com.example.akenasia.home.MainActivity

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

        binding.armure.setOnClickListener{
            val infos= ItemDialog()
            infos.setText("Liste de vos armures")
            val navHostFragment = supportFragmentManager
            infos.show(navHostFragment,"infos")
        }

    }

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