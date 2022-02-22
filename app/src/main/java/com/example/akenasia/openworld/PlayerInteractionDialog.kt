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
import com.google.firebase.database.*
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

            val ref = database.getReference("User").child(user.uid.toString()).child("Stats").child("TotalJoueurs").child(playeruid)
            //START on vérifie si le joueur s'est déjà entrainé avec le joueur rencontré
            val query: Query = ref
            query.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //Si c'est la première fois ie la valeur est null
                    val time: Long = if(snapshot.value == null){
                        0
                    } else{
                        //Sinon on on prend la valeur stockée
                        snapshot.value.toString().toLong()
                    }
                    //Si (temps actuel - last_visited du joueur) converti en seconde < 1 jour, alors on refuse l'entrainement
                    if (System.currentTimeMillis().minus(time)/ 1000 <= 86400){
                        Toast.makeText(thiscontext,"Vous vous êtes déjà entrainés avec ce joueur aujourd'hui",Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                    //sinon on augmente une stat de manière aléatoire
                    else{
                        //MAJ du last_visited à la date actuelle
                        database.getReference("User").child(user.uid.toString()).child("Stats").child("TotalJoueurs").child(playeruid).setValue(System.currentTimeMillis())
                        Toast.makeText(thiscontext,time.div(1000).toString(),Toast.LENGTH_LONG).show()
                        val stat = (0..2).random()
                        val value = (1..3).random()
                        when (stat){
                            0->{
                                handler.UpHP(1,value.toDouble())
                                Toast.makeText(thiscontext,
                                    "Votre entrainement vous a fait acquérir 30 exp et $value HP!",Toast.LENGTH_SHORT).show()
                                dismiss()
                            }
                            1->{
                                handler.UpATT(1,value.toDouble())
                                Toast.makeText(thiscontext,
                                    "Votre entrainement vous a fait acquérir 30 exp et $value points d'ATT!",Toast.LENGTH_SHORT).show()
                                dismiss()
                            }
                            2->{
                                handler.UpDEF(1,value.toDouble())
                                Toast.makeText(thiscontext,
                                    "Votre entrainement vous a fait acquérir 30 exp et $value points de DEF!",Toast.LENGTH_SHORT).show()
                                dismiss()
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.i(TAG, "onCancelled: Error: " + error.message);
                }
            })
        }
        return rootView
    }

}