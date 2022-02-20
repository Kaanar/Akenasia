package com.example.akenasia.authentication

import com.example.akenasia.achievement.Achievement
import com.example.akenasia.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class DataInitialisation(val id: String, val email: String, val password: String, val pseudo: String) {

    private var auth: FirebaseAuth = Firebase.auth
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var achievements: Achievement = Achievement()


    fun initialise(){
        //Création de l'objet user pour le jeu
        val user= User(email, password, pseudo)
        database.getReference("User").child(auth.uid.toString()).setValue(user)
        for (e in achievements.data_init()){
        database.getReference("User").child(auth.uid.toString()).child("Achievement").child(e).child("unlocked").setValue(0)
        }
    }
}