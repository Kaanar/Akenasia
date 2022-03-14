package com.example.akenasia.amis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.authentication.Authentication
import com.example.akenasia.database.PersonnageTable
import com.example.akenasia.databinding.AmisBinding
import com.example.akenasia.databinding.SocialstoreBinding
import com.example.akenasia.handler.PersonnageHandler
import com.example.akenasia.home.MainActivity
import com.example.akenasia.openworld.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class SocialStore : AppCompatActivity(){

    private lateinit var binding: SocialstoreBinding
    //START Firebase database connection + Firebase Auth
    private lateinit var database: FirebaseDatabase
    private lateinit var user: FirebaseAuth
    private lateinit var personnage: PersonnageHandler
    private lateinit var currentPersonnage: PersonnageTable



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SocialstoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //START Initialize BD and auth
        database = FirebaseDatabase.getInstance()
        user = Firebase.auth
        //END
        personnage = PersonnageHandler(this)

        currentPersonnage=personnage.get(1)

        val SP = database.getReference("User").child(user.uid.toString()).child("Amis").child("Social_Points")
        val SpQuery : Query =SP.orderByKey()

        SpQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.PointsSociauxNombreTv.text=snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(Authentication.TAG, "onCancelled: Error: " + error.message);
            }

        })

        binding.expBtn.setOnClickListener {
            val Sp = binding.PointsSociauxNombreTv.text.toString().toInt()
            if(Sp<10){
                Toast.makeText(this,"Pas assez de points sociaux",Toast.LENGTH_SHORT).show()
            }
            else{
                database.getReference("User").child(user.uid.toString()).child("Amis").child("Social_Points").setValue(Sp-10)
                binding.PointsSociauxNombreTv.text=(Sp-10).toString()
                UpdatePointLevel()
            Toast.makeText(this,"Achat effectué!",Toast.LENGTH_SHORT).show()
            }
        }

        binding.NavigationView.selectedItemId = com.example.akenasia.R.id.PersonnageClick


        //Implémentation des différents choix du menu
        binding.NavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                com.example.akenasia.R.id.QuitClick -> {
                    val intent = Intent(this, MainActivity::class.java)
                    this.startActivity(intent)
                }
                com.example.akenasia.R.id.MapClick -> {
                    val intent = Intent(this, OpenWorld::class.java)
                    this.startActivity(intent)
                }
                com.example.akenasia.R.id.BagClick -> {
                    val intent = Intent(this, Bag::class.java)
                    this.startActivity(intent)
                }
                com.example.akenasia.R.id.ForgeClick -> {
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

    fun UpdatePointLevel(){

        for (e in 1..10){
            personnage.upPoint(1)
            if(personnage.get(1).points>=150){
                personnage.upLevel(1)
                val dialog = UpLevelDialog()
                val navHostFragment = supportFragmentManager
                dialog.show(navHostFragment, "UpLevelDialog")
            }
        }
    }

}