package com.example.akenasia.openworld.mobs

class Orc : Monstre() {
    override val name = "Orc"
    override var atk = 3.0
    override var hp = 20.0
    override val texte_spe ="L'orc s'inflige des dégats pour augmenter son attaque !"


    override fun attaque_spe(): Boolean{
        hp /= 2.0 //l'orc sacrifie la moitié de ses pvs pour doubler son attaque
        atk *= 2.0 //il l'utilisera seulement pour achever la joueur
        return true
    }
}