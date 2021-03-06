package com.example.akenasia.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.akenasia.R
import com.example.akenasia.authentication.Authentication
import com.example.akenasia.authentication.Authentication.Companion.TAG
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AmisAdapter(private val context: Activity, private val id: ArrayList<String>, private val date: ArrayList<String>, private val isSent: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.achievements_listview,id) {

    //START Firebase database connection + Firebase Auth
    private var database = FirebaseDatabase.getInstance()
    private var user= Firebase.auth
    var current: LocalDateTime = LocalDateTime.now()
    var formatter: DateTimeFormatter = DateTimeFormatter.BASIC_ISO_DATE
    var currentDateFormatted: String = current.format(formatter)
    //END

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.amis_listview, null, true)

        val pseudoTv = rowView.findViewById<View>(R.id.pseudo) as TextView
        val socialPointsBtn = rowView.findViewById<View>(R.id.sendSocialPointBtn) as TextView

            //Inflate pseudo value in TextView
            val pseudo = database.getReference("User").child(id[position])
            val pseudoquery : Query = pseudo.orderByKey()

            pseudoquery.addListenerForSingleValueEvent((object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    pseudoTv.text=snapshot.child("pseudo").value.toString()
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.i(Authentication.TAG, "onCancelled: Error: " + error.message);
                }
                }))

            //START Compare current date with last time social points were sent
            val date=  date[position].format(formatter)
            var isSentInt =isSent[position].toInt()
            //If the date is different then we allow the user to send social point
            if(currentDateFormatted != date && isSentInt==1){
                database.getReference("User").child(user.uid.toString()).child("Amis").child("Liste").child(id[position]).child("isSent").setValue(0)
                isSentInt=0
            }
            else if(isSentInt==1){
                socialPointsBtn.isClickable=false
                socialPointsBtn.setBackgroundColor(Color.GRAY)
            }

            socialPointsBtn.setOnClickListener {
                if(isSentInt==0){
                    database.getReference("User").child(user.uid.toString()).child("Amis").child("Liste").child(id[position]).child("Date").setValue(currentDateFormatted)
                    database.getReference("User").child(user.uid.toString()).child("Amis").child("Liste").child(id[position]).child("isSent").setValue(1)

                    val SP= database.getReference("User").child(user.uid.toString()).child("Amis")
                    val query: Query = SP.orderByKey()

                    //Query qui permet de r??cup??rer les points sociaux du joueur pour les MAJ
                    query.addListenerForSingleValueEvent(object : ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            val socialPoints=snapshot.child("Social_Points").value
                            val newSPValue = socialPoints as Long + 1
                            database.getReference("User").child(user.uid.toString()).child("Amis").child("Social_Points").setValue(newSPValue)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.i(TAG, "onCancelled: Error: " + error.message);
                        }
                    })

                    val friend= database.getReference("User").child(id[position]).child("Amis")
                    val query2: Query = friend.orderByKey()

                    //Query qui permet de r??cup??rer les points sociaux du joueur click?? pour lui envoyer un point
                    query2.addListenerForSingleValueEvent(object : ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            val socialPoints=snapshot.child("Social_Points").value
                            val newSPValue = socialPoints as Long + 1
                            database.getReference("User").child(id[position]).child("Amis").child("Social_Points").setValue(newSPValue)

                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.i(TAG, "onCancelled: Error: " + error.message);
                        }
                    })
                    socialPointsBtn.isClickable=false
                    socialPointsBtn.setBackgroundColor(Color.GRAY)
                    }
                else{
                    Toast.makeText(context,"Vous avez d??j?? donn?? un point ?? cet ami",Toast.LENGTH_SHORT).show()
                }

            }

        return rowView
    }

}