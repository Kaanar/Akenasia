package com.example.akenasia.openworld

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.databinding.PersonnageBinding
import com.example.akenasia.home.MainActivity
import com.example.akenasia.R
import com.example.akenasia.handler.ItemHandler
import com.example.akenasia.handler.PersonnageHandler
import com.example.akenasia.achievement.AchievementFragment
import com.example.akenasia.amis.Amis
import com.example.akenasia.authentication.Authentication.Companion.TAG
import com.example.akenasia.database.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class Personnage: AppCompatActivity() {
    private lateinit var binding: PersonnageBinding
    private lateinit var personnageHandler: PersonnageHandler
    private lateinit var itemHandler: ItemHandler
    private lateinit var currentPersonnage: PersonnageTable

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]
    //Start Firebase connection
    private lateinit var database: FirebaseDatabase
    //End Firebase connection


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PersonnageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        personnageHandler = PersonnageHandler(this)
        itemHandler = ItemHandler(this)

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]
        // Initialize Firebase Realtime Database connection
        database= FirebaseDatabase.getInstance()


        val totalJoueursRencontres =database.getReference("User").child(auth.uid.toString()).child("Stats").child("TotalJoueurs")

        totalJoueursRencontres.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val total=snapshot.childrenCount-1
                binding.totalPlayersSeen.text= "Joueurs rencontr??s: $total"
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i(TAG, "onCancelled: Error: " + error.message);
            }

        })



        binding.NavigationView.selectedItemId = R.id.PersonnageClick

        binding.achievements.setOnClickListener{
            val intent= Intent(this,AchievementFragment::class.java)
            this.startActivity(intent)
        }
        binding.amis.setOnClickListener{
            val intent= Intent(this,Amis::class.java)
            this.startActivity(intent)
        }

        //Impl??mentation des diff??rents choix du menu
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
        //Affiche les ??p??es du sac
        binding.epee.setOnClickListener{
            val infos= ItemDialog()
            val navHostFragment = supportFragmentManager
            val bundle = Bundle()
            bundle.putSerializable("type",ListItems.EPEE)
            infos.arguments=bundle
            infos.show(navHostFragment,"ItemDialog")
            infos.setText("Liste de vos ??p??es")
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
    //M??thode qui affiche les stats du joueur et de ses items ??quip??s
    private fun inflateStats() {
        currentPersonnage=personnageHandler.get(1)

        //Affichage des stats du personnage
        binding.personnagePv.text="PV: "+currentPersonnage.persoHp.toString()
        binding.personnageAtt.text="ATT: "+currentPersonnage.persoAtt.toString()
        binding.personnageDef.text="DEF: "+currentPersonnage.persoDef.toString()
        binding.personnagePoints.text="POINTS: "+currentPersonnage.points.toString()
        binding.personnageLevel.text="LEVEL: "+currentPersonnage.level.toString()


        //Affichage des stats de l'armure
        binding.armureAtt.text="ATT: "+itemHandler.get(currentPersonnage.armure).ItemAtt.toString()
        binding.armureDef.text="DEF: "+itemHandler.get(currentPersonnage.armure).ItemDef.toString()

        //Affichage des stats du bouclier
        binding.bouclierAtt.text="ATT: "+itemHandler.get(currentPersonnage.bouclier).ItemAtt.toString()
        binding.bouclierDef.text="DEF: "+itemHandler.get(currentPersonnage.bouclier).ItemDef.toString()


        //Affichage des stats de l'??p??e
        binding.epeeAtt.text="ATT: "+itemHandler.get(currentPersonnage.epee).ItemAtt.toString()
        binding.epeeDef.text="DEF: "+itemHandler.get(currentPersonnage.epee).ItemDef.toString()


        //Affichage des stats des chaussures
        binding.chaussuresAtt.text="ATT: "+itemHandler.get(currentPersonnage.chaussures).ItemAtt.toString()
        binding.chaussuresDef.text="DEF: "+itemHandler.get(currentPersonnage.chaussures).ItemDef.toString()

    }


}