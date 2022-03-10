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
    private var mob : Monstre = CreateMob().create_mob() //méthode qui va créer un monstre au hasard
    private var pv_monstre : Double = mob.hp
    private var att_monstre : Double = mob.atk
    private lateinit var itemHandler : ItemHandler


    fun crit(): Boolean {
        val nb = (0..5).random()
        if(nb == 1){
            return true
        }
        return false
    }

    private fun calcul_degat_joueur(): Double {
        val crit = crit()
        if(crit){
            TypeAttaqueJoueur.text = "Coup critique !"
            return att_joueur*2 // ajouter un message si coup crit
        }
        else {
            TypeAttaqueJoueur.text = "Vous attaquez !"
            return att_joueur
        }
    }

    fun calcul_degat_monstre(monstre : Monstre): Double {
        val crit = crit()
        val spe = monstre.attaque_spe()
        val def = def_joueur/5
        if(spe){
            TypeAttaqueMonstre.text = "Attaque spéciale !"
            if (monstre.atk - def > 1) {
                return monstre.atk - def
            }
            else {
                return 1.0
            }
        }
        else if (crit) {
            TypeAttaqueMonstre.text = "Coup critique !"
            return monstre.atk*2
        }
        else {
            TypeAttaqueMonstre.text = "Attaque normale"
            if (monstre.atk - def > 1) {
                return monstre.atk - def
            } else {
                return 1.0
            }
        }
    }

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, saveInstanceState : Bundle?) :
            View? {
        thiscontext = this.activity
        personnageHandler = PersonnageHandler(thiscontext!!)
        itemHandler = ItemHandler(thiscontext!!)
        personnageTable = personnageHandler.get(1)
        pv_joueur = personnageTable.persoHp
        att_joueur = personnageTable.persoAtt
        def_joueur = personnageTable.persoDef
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
            attaqueInfoJoueur.visibility = View.VISIBLE
            attaqueInfoMonstre.visibility = View.VISIBLE
            AttaqueMonstre.text = String.format("%.2f", mob.atk)
            AttaqueJoueur.text = String.format("%.2f", att_joueur)
            NomMonstre.text = String.format("%s", mob.name)
            PvMonstre.text = String.format("%.2f", pv_monstre)
            PvJoueur.text = String.format("%.2f", pv_joueur)
        }

        rootView.BtnAttaqueMonstre.setOnClickListener() {

            pv_monstre -= calcul_degat_joueur()
            if (pv_monstre <= 0) {
                PvMonstre.text = "0.0"
                victoireText.visibility = View.VISIBLE

                dropItem()
                
                 //MAJ des stats, +1 monstre vaincu et +1 item récupéré
                Stats(context!!, 1).upMonstres()
                Stats(context!!,1).upItems()

                BtnRetour.visibility = View.VISIBLE
                BtnAttaqueMonstre.visibility = View.INVISIBLE
            }
            else {
                PvMonstre.text = String.format("%.2f", pv_monstre)
            }

            if(pv_monstre <= 0) {

            }
            else {
                pv_joueur -= calcul_degat_monstre(mob)

                if (pv_joueur <= 0) {
                    PvJoueur.text = "0.0"
                    defaiteText.visibility = View.VISIBLE
                    BtnRetour.visibility = View.VISIBLE
                    BtnAttaqueMonstre.visibility = View.INVISIBLE
                }
                else {
                    PvJoueur.text = String.format("%.2f", pv_joueur)
                }
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

    fun dropItem() {
        val spinnerList = ArrayList<ListItems>()
        spinnerList.add(ListItems.BOUCLIER)
        spinnerList.add(ListItems.EPEE)
        spinnerList.add(ListItems.ARMURE)
        spinnerList.add(ListItems.CHAUSSURES)
        val pick: Int = Random().nextInt(spinnerList.size)
        val att = ThreadLocalRandom.current().nextInt(0,5)
        val def = ThreadLocalRandom.current().nextInt(0,5)
        this.itemHandler.add(Item(itemHandler.view().last().getItemid()+1, spinnerList[pick].toString(),"Un item surprise!","A voir où vous allez pouvoir l'équiper",att.toDouble(),def.toDouble(), 0, 0))
        Toast.makeText(context,"Vous avez gagné un item surprise", Toast.LENGTH_LONG).show()
    }
}