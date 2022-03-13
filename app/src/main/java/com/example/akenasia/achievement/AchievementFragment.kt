package com.example.akenasia.achievement

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.handler.AchievementHandler
import com.example.akenasia.R
import com.example.akenasia.adapter.AchievementAdapter
import com.example.akenasia.authentication.Authentication.Companion.TAG
import com.example.akenasia.databinding.AchievementsBinding
import com.example.akenasia.home.MainActivity
import com.example.akenasia.openworld.Bag
import com.example.akenasia.openworld.OpenWorld
import com.example.akenasia.openworld.Personnage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.achievements.*

class AchievementFragment : AppCompatActivity() {

    private lateinit var binding: AchievementsBinding
    private lateinit var achievementHandler: AchievementHandler
    //START Firebase database connection + Firebase Auth
    private lateinit var database: FirebaseDatabase
    private lateinit var user: FirebaseAuth
    //END
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AchievementsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        achievementHandler = AchievementHandler(applicationContext)

        //START Initialize BD and auth
        database = FirebaseDatabase.getInstance()
        user = Firebase.auth
        //END

        viewRecord()

        binding.NavigationView.selectedItemId = R.id.PersonnageClick

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
    }



    private fun viewRecord(){
        //Référencement de la BD au niveau des suucès du user + on trie les succès par ID
        val achievements= database.getReference("User").child(user.uid.toString()).child("Achievement")
        val query: Query = achievements.orderByKey()
        var nb =27 //ATTENTION IL FAUT FAIRE EN SORTE QUE CE SOIT = NB DE ACHIEVEMENTS

        Toast.makeText(this@AchievementFragment,nb.toString(),Toast.LENGTH_LONG).show()
        val empArrayid : Array<String> = Array(nb){"0"}
        val empArrayDesc : Array<String> = Array(nb){"0"}
        val empArrayUnlocked : Array<String> = Array(nb){"0"}

        nb =0
        //Query qui permet de récupérer tous les marqueurs de la table
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //START on récupère tous les achievements et on stocke les infos dans des ArrayList pour les envoyer
                //vers l'adapter
                for (children in snapshot.children) {
                    val id = children.key.toString()
                    val description = children.child("description").value.toString()
                    val unlocked = children.child("unlocked").value.toString()
                    empArrayid[nb]=id
                    empArrayDesc[nb]=description
                    empArrayUnlocked[nb]=unlocked
                    nb++
                }
                //END
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(TAG, "onCancelled: Error: " + error.message);
            }

        })
        //creating custom ArrayAdapter
        val achievementAdapter = AchievementAdapter(this,empArrayid,empArrayDesc,empArrayUnlocked)
        ListViewAchievements?.adapter= achievementAdapter
    }
}