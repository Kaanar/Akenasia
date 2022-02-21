package com.example.akenasia.openworld

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.akenasia.Handler.PersonnageHandler
import com.example.akenasia.R
import com.example.akenasia.authentication.Authentication.Companion.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.playerinteractiondialog.view.*

class PlayerInteractionDialog : DialogFragment() {

    private var thiscontext: Context? = null
    //START Firebase database connection + Firebase Auth
    private lateinit var database: FirebaseDatabase
    private lateinit var user: FirebaseAuth
    //END
    private lateinit var handler: PersonnageHandler
    private lateinit var playeruid: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //START récupération des infos du joueur rencontré
        val bundle = arguments
        playeruid = bundle!!.getString("uid", "")
        //START Initialize BD and auth
        database = FirebaseDatabase.getInstance()
        user = Firebase.auth
        //END
        thiscontext = this.activity
        handler= thiscontext?.let { PersonnageHandler(it) }!!
        val rootView: View = inflater.inflate(R.layout.playerinteractiondialog, container, false)

        rootView.BtnPartir.setOnClickListener {
            dismiss()
        }

        //Si il décide de s'entrainer
        rootView.BtnEntrainement.setOnClickListener{
            val ref= database.getReference("User").child(user.uid.toString()).child("Stats").child("TotalJoueurs").child(playeruid).child("visited")
            var visited= -1
            //START on vérifie si le joueur s'est déjà entrainé avec le joueur rencontré
            ref.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    visited = if (snapshot.value.toString()=="1"){
                        1
                    }
                    //sinon on augmente une stat de manière aléatoire
                    else{
                        ref.setValue("1")
                        0
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.i(TAG, "onCancelled: Error: " + error.message);
                }
            })
            if(visited==1){
                Toast.makeText(thiscontext,"Vous vous êtes déjà entrainés avec ce joueur",Toast.LENGTH_LONG).show()
                dismiss()
            }
            else if(visited==0){
                for (e in 1..5){
                    handler.upPoint(1) //ICI on donne 30 points d'exp au joueur
                }
                val stat = (0..2).random()
                val value = (1..3).random()
                when (stat){
                    0->{
                        handler.UpHP(1,value.toDouble())
                        Toast.makeText(thiscontext,"Votre entrainement vous a fait acquérir 30 exp et "+ value.toString() + " HP!",Toast.LENGTH_LONG).show()
                        dismiss()
                    }
                    1->{
                        handler.UpATT(1,value.toDouble())
                        Toast.makeText(thiscontext,"Votre entrainement vous a fait acquérir 30 exp et "+ value.toString() + " points d'ATT!",Toast.LENGTH_LONG).show()
                        dismiss()
                    }
                    2->{
                        handler.UpDEF(1,value.toDouble())
                        Toast.makeText(thiscontext,"Votre entrainement vous a fait acquérir 30 exp et "+ value.toString() + " points de DEF!",Toast.LENGTH_LONG).show()
                        dismiss()
                    }
                }
            }
        }
        return rootView
    }

}