package com.example.akenasia.openworld

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.Handler.PersonnageHandler
import com.example.akenasia.R
import com.example.akenasia.database.Item
import com.example.akenasia.achievement.Stats
import com.example.akenasia.database.ListItems
import com.example.akenasia.database.PersonnageTable
import com.example.akenasia.openworld.mobs.Dragon
import com.example.akenasia.openworld.mobs.Monstre
import com.example.akenasia.openworld.mobs.Orc
import com.example.akenasia.openworld.mobs.Slime
import kotlinx.android.synthetic.main.marker_dialog.*
import kotlinx.android.synthetic.main.marker_dialog.view.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class MarkerDialog : DialogFragment () {
    private lateinit var title : String
    private lateinit var info : String
    private var thiscontext: Context? = null
    private lateinit var personnageHandler : PersonnageHandler
    private lateinit var personnageTable : PersonnageTable
    private var pv_joueur : Double = 0.0
    private var att_joueur : Double = 0.0
    private var def_joueur : Double = 0.0
    private var mob : Monstre = create_mob()
    private var pv_monstre : Double = mob.hp
    private var att_monstre : Double = mob.atk
    private lateinit var itemHandler : ItemHandler


    /*
    On passe le titre et les infos présents de la classe Historique dans le dialog
    Le dialoque affiche le titre (n° de la position) et les infos (latitude, longitude)
    en fonction du marqueur sur lequel on clique sur la map de l'historique.
     */

    fun create_mob(): Monstre {
        var nb = (0..10).random()
        var mob : Monstre
        if(nb == 0 || nb == 1 || nb == 2 || nb == 3 || nb ==4 || nb == 5){
            mob = Slime()
            return mob
        }
        if(nb == 6 || nb == 7 || nb == 8 || nb == 9){
            mob = Orc()
            return mob
        }
            mob = Dragon()
            return mob
    }

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, saveInstanceState : Bundle?) :
            View? {
        thiscontext = this.activity
        personnageHandler = PersonnageHandler(thiscontext!!)
        itemHandler = ItemHandler(thiscontext!!)
        personnageTable = personnageHandler.get(1)
        pv_joueur = personnageTable.getpersoHp()
        att_joueur = personnageTable.getpersoAtt()
        def_joueur = personnageTable.getpersoDef()
        val rootView: View = inflater.inflate(R.layout.marker_dialog, container, false)

        rootView.BtnAttaque.setOnClickListener() {
            //On lance le combat
            BtnAttaque.visibility = View.INVISIBLE
            BtnFuite.visibility = View.INVISIBLE
            combatText.visibility = View.VISIBLE
            BtnAttaqueMonstre.visibility = View.VISIBLE
            PvJoueur.visibility = View.VISIBLE
            PvMonstre.visibility = View.VISIBLE
            TextMarker.visibility = View.INVISIBLE
            NomJoueur.visibility = View.VISIBLE
            NomMonstre.visibility = View.VISIBLE
            AttaqueJoueur.visibility = View.VISIBLE
            AttaqueMonstre.visibility = View.VISIBLE
            TexteJoueurAtt.visibility = View.VISIBLE
            TexteMonstreAtt.visibility = View.VISIBLE
            AttaqueMonstre.text = String.format("%f", mob.atk)
            AttaqueJoueur.text = String.format("%f", att_joueur)
            NomMonstre.text = String.format("%s", mob.name)
            PvMonstre.text = String.format("%f", pv_monstre)
            PvJoueur.text = String.format("%f", pv_joueur)
        }

        rootView.BtnAttaqueMonstre.setOnClickListener() {

            pv_monstre -= att_joueur
            PvMonstre.text = String.format("%f", pv_monstre)
            if (pv_monstre <= 0) {
                victoireText.visibility = View.VISIBLE
                var id:Int
                try{
                    id= itemHandler.view().last().getItemid()+1
                }
                catch (e:java.util.NoSuchElementException){
                    id=1
                }
                val pick: Int = Random().nextInt(ListItems.values().size)
                val att = ThreadLocalRandom.current().nextInt(0,5)
                val def = ThreadLocalRandom.current().nextInt(0,5)
                this.itemHandler.add(Item(id, ListItems.values()[pick].toString(),"Un item surprise!","A voir où vous allez pouvoir l'équiper",att.toDouble(),def.toDouble()))
                Toast.makeText(context,"Vous avez gagné un item surprise", Toast.LENGTH_LONG).show()
                
                 //MAJ des stats, +1 monstre vaincu et +1 item récupéré
                Stats(context!!, 1).upMonstres()
                Stats(context!!,1).upItems()

                BtnRetour.visibility = View.VISIBLE
                BtnAttaqueMonstre.visibility = View.INVISIBLE
            }

            pv_joueur -= att_monstre
            PvJoueur.text = String.format("%f", pv_joueur)


            if (pv_joueur <= 0) {
                defaiteText.visibility = View.VISIBLE
                BtnRetour.visibility = View.VISIBLE
                BtnAttaqueMonstre.visibility = View.INVISIBLE
            }
        }

        rootView.BtnRetour.setOnClickListener() {
            dismiss()
        }

        rootView.BtnFuite.setOnClickListener() {
            //On ferme le dialogue quand on clique sur le bouton (on ne récupère pas d'objet)
            dismiss()
        }
        return rootView
    }

    fun setTitle (message : String) {
        title = message
    }
    fun setInfo (message : String) {
        info = message
    }
}