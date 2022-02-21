package com.example.akenasia.authentication

import com.example.akenasia.achievement.Achievement
import com.example.akenasia.database.PersonnageTable
import com.example.akenasia.database.Stats
import com.example.akenasia.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class DataInitialisation(val id: String, val email: String, val password: String, val pseudo: String) {

    private var auth: FirebaseAuth = Firebase.auth
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var achievements: Achievement = Achievement()
    private var stats: Stats  = Stats(0,0,0)

    fun initialise(){
        //Création de l'objet user pour le jeu
        val user= User(email, password, pseudo)
        database.getReference("User").child(auth.uid.toString()).setValue(user)
        //START initialisation des Achievements
        for ((index, e) in achievements.data_init().withIndex()){
            val achievement: com.example.akenasia.database.Achievement = com.example.akenasia.database.Achievement(index,e,0)
            database.getReference("User").child(auth.uid.toString()).child("Achievement").child(index.toString()).setValue(achievement)
        }
        //END
        //START initialisation des statistiques (historique)
        database.getReference("User").child(auth.uid.toString()).child("Stats").child("TotalMarqueur").setValue(0)
        database.getReference("User").child(auth.uid.toString()).child("Stats").child("TotalMonstre").setValue(0)
        database.getReference("User").child(auth.uid.toString()).child("Stats").child("TotalItem").setValue(0)
        database.getReference("User").child(auth.uid.toString()).child("Stats").child("TotalJoueurs").child(auth.uid.toString()).setValue(pseudo)

        //END
        //START initialisation de la position
        database.getReference("User").child(auth.uid.toString()).child("Position").child("latitude").setValue(0.0)
        database.getReference("User").child(auth.uid.toString()).child("Position").child("longitude").setValue(0.0)
        //END
        //START initialisation du personnage
        val personnage= PersonnageTable(auth.uid.toString(),20.0,12.0,10.0,-1,-1,-1,-1,0)
        database.getReference("User").child(auth.uid.toString()).child("Personnage").setValue(personnage)



    }
}