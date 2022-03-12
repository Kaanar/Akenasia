package com.example.akenasia.amis


import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.adapter.AchievementAdapter
import com.example.akenasia.adapter.AmisAdapter
import com.example.akenasia.authentication.Authentication
import com.example.akenasia.databinding.AmisBinding
import com.example.akenasia.databinding.BagBinding
import com.example.akenasia.home.MainActivity
import com.example.akenasia.openworld.Bag
import com.example.akenasia.openworld.Forge
import com.example.akenasia.openworld.OpenWorld
import com.example.akenasia.openworld.Personnage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.amis.*
import android.widget.ArrayAdapter as ArrayAdapter

class Amis : AppCompatActivity(){
    
    private lateinit var binding: AmisBinding
    //START Firebase database connection + Firebase Auth
    private lateinit var database: FirebaseDatabase
    private lateinit var user: FirebaseAuth
    //END
    private var usersPseudos: HashMap<String,String> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AmisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //START Initialize BD and auth
        database = FirebaseDatabase.getInstance()
        user = Firebase.auth
        //END

        val users= database.getReference("User")
        val query: Query = users.orderByKey()

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (children in snapshot.children) {
                    usersPseudos[children.key.toString()] = children.child("pseudo").value.toString()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i(Authentication.TAG, "onCancelled: Error: " + error.message);
            }

        })

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
        viewRecord()
    }


    private fun viewRecord(){
        //Référencement de la BD au niveau des suucès du user + on trie les succès par ID
        val amis= database.getReference("User").child(user.uid.toString()).child("Amis").child("Liste")
        val query: Query = amis.orderByKey()
        var nb=1000

        val empArrayid : Array<String> = Array(nb){"0"}
        val empArrayPseudo : Array<String> = Array(nb){"0"}
        val empArrayDate : Array<String> = Array(nb){"0"}
        val empIsSent : Array<String> = Array(nb){"0"}

        nb=0
        //Query qui permet de récupérer les infos des amis du joueurs
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //START on récupère tous les achievements et on stocke les infos dans des ArrayList pour les envoyer
                //vers l'adapter
                for (children in snapshot.children) {
                    val id = children.key.toString()
                    val date = children.child("Date").value.toString()
                    val isSent = children.child("isSent").value.toString()
                    empArrayid[nb]=id
                    empArrayDate[nb]=date
                    empIsSent[nb]=isSent
                    //On compare l'id de chaque user dans les amis aves la liste des joueurs
                    //Si c'est le même (ils sont amis), alors on ajoute le pseudo dans l'arrayList
                    for(pseudos in usersPseudos){
                        if(pseudos.key==children.key){
                            empArrayPseudo[nb]=pseudos.value
                        }
                    }
                    nb++
                }
                //END
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i(Authentication.TAG, "onCancelled: Error: " + error.message);
            }


        })
        Toast.makeText(this@Amis,empArrayid.size.toString(),Toast.LENGTH_SHORT).show()

        //creating custom ArrayAdapter
        val amisAdapter = AmisAdapter(this,empArrayid,empArrayPseudo,empArrayDate,empIsSent)
        ListViewAmis?.adapter= amisAdapter
    }

    
}