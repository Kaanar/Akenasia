package com.example.akenasia.openworld

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.akenasia.databinding.PersonnageBinding
import com.example.akenasia.home.MainActivity
import com.example.akenasia.R
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.Handler.PersonnageHandler
import com.example.akenasia.achievement.AchievementFragment
import com.example.akenasia.database.*


class Personnage: AppCompatActivity() {
    private lateinit var binding: PersonnageBinding
    private lateinit var personnageHandler: PersonnageHandler
    private lateinit var itemHandler: ItemHandler
    private lateinit var currentPersonnage: PersonnageTable



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PersonnageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        personnageHandler = PersonnageHandler(this)
        itemHandler = ItemHandler(this)



        binding.NavigationView.selectedItemId = R.id.PersonnageClick

        binding.achievements.setOnClickListener{
            val intent= Intent(this,AchievementFragment::class.java)
            this.startActivity(intent)
        }

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

        binding.retirer.setOnClickListener{
            personnageHandler.reset(1)
            this.recreate()
        }
    }



    @SuppressLint("SetTextI18n")
    //Méthode qui affiche les stats du joueur et de ses items équipés
    private fun inflateStats() {
        currentPersonnage=personnageHandler.get(1)

        //Affichage des stats du personnage
        binding.personnagePv.text="PV: "+currentPersonnage.getpersoHp().toString()
        binding.personnageAtt.text="ATT: "+currentPersonnage.getpersoAtt().toString()
        binding.personnageDef.text="DEF: "+currentPersonnage.getpersoDef().toString()

        //Affichage des stats de l'armure
        binding.armureAtt.text="ATT: "+itemHandler.get(currentPersonnage.armure).ItemAtt.toString()
        binding.armureDef.text="DEF: "+itemHandler.get(currentPersonnage.armure).ItemDef.toString()

        //Affichage des stats du bouclier
        binding.bouclierAtt.text="ATT: "+itemHandler.get(currentPersonnage.bouclier).ItemAtt.toString()
        binding.bouclierDef.text="DEF: "+itemHandler.get(currentPersonnage.bouclier).ItemDef.toString()


        //Affichage des stats de l'épée
        binding.epeeAtt.text="ATT: "+itemHandler.get(currentPersonnage.epee).ItemAtt.toString()
        binding.epeeDef.text="DEF: "+itemHandler.get(currentPersonnage.epee).ItemDef.toString()


        //Affichage des stats des chaussures
        binding.chaussuresAtt.text="ATT: "+itemHandler.get(currentPersonnage.chaussures).ItemAtt.toString()
        binding.chaussuresDef.text="DEF: "+itemHandler.get(currentPersonnage.chaussures).ItemDef.toString()

    }


}