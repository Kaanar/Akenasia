package com.example.akenasia.openworld

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.akenasia.databinding.ActivityOpenworldBinding
import com.example.akenasia.home.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_openworld.*
import kotlinx.android.synthetic.main.content_map.*
import android.content.res.Resources
import android.location.Location
import android.location.LocationListener
import android.system.Os.remove
import android.util.Log
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.Handler.MarqueurHandler
import com.example.akenasia.Handler.PersonnageHandler
import com.example.akenasia.achievement.Stats
import com.example.akenasia.database.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ORANGE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.util.concurrent.ThreadLocalRandom
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng





class OpenWorld : AppCompatActivity(),OnMapReadyCallback {

    private val TAG: String = OpenWorld::class.java.simpleName
    private lateinit var pos: Position
    private var isPlay: Boolean = false
    private lateinit var binding: ActivityOpenworldBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var chronometre: Chronometer
    private lateinit var markers: HashMap<Int,LatLng>
    lateinit var itemHandler: ItemHandler
    lateinit var marqueurHandler: MarqueurHandler
    private var cameraFocus: Boolean = true
    //Variables qui permettent de mettre en place une fréquence
    private var spawnTime= 0
    //Marqueur du joueur sur la carte
    private lateinit var CurrentMarkerPosition: Marker
    //Marqueurs des lieux et des joueurs
    private var lesMarqueurs: ArrayList<Marker> = ArrayList()
    private var playerMarqueurs: ArrayList<Marker> = ArrayList()
    private var lesUsers: ArrayList<String> = ArrayList()
    //START Firebase database connection + Firebase Auth
    private lateinit var database: FirebaseDatabase
    private lateinit var user: FirebaseAuth
    //END
    private lateinit var personnage: PersonnageHandler
    private lateinit var currentPersonnage: PersonnageTable
    //Valeurs LatLong
    private var randomLat = ThreadLocalRandom.current().nextDouble(0.0001,0.0009)
    private var randomLong = ThreadLocalRandom.current().nextDouble(0.0001,0.0009)
    //Valeur random pour la position du marker ennemi
    private var randomPosition = ThreadLocalRandom.current().nextInt(0,4)
    //Valeur random pour la fréquence d'appartition du marker ennemi
    private var randomSpawnTime = ThreadLocalRandom.current().nextInt(4000,30000)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenworldBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pos = Position(this)
        pos.refreshLocation()
        itemHandler= ItemHandler(this)
        marqueurHandler= MarqueurHandler(this)
        personnage = PersonnageHandler(this)
      
        currentPersonnage=personnage.get(1)
        markers= HashMap()

        //START Initialize BD and auth
        database = FirebaseDatabase.getInstance()
        user = Firebase.auth
        //END

        chronometre = OWChrono
        if (!isPlay) {
            chronometre.base = SystemClock.elapsedRealtime() + 300000
            chronometre.start()
            isPlay = true
        }

        //Initialisation de la map
        OWmap_view.onCreate(savedInstanceState)
        OWmap_view.onResume()
        OWmap_view.getMapAsync(this)

        binding.NavigationView.selectedItemId = com.example.akenasia.R.id.MapClick

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
        CameraSwitch.setOnClickListener{ cameraFocus = cameraFocus != true }

    }

    override fun onMapReady(map: GoogleMap) {
        map.let { laMAP ->
            googleMap = laMAP
            visible()

            //START Affichage de la position du joueur
            CurrentMarkerPosition = googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(pos.getLatitude(), pos.getLongitude()))
                    .title("Current Position")
            )!!
            //END

            //Référencement de la BD au nivea des marqueurs + on trie les marqueurs par ID
            val markers = database.getReference("Marqueur")
            var query: Query = markers.orderByKey()

            //Query qui permet de récupérer tous les marqueurs de la table
            query.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    //START on récupère tous les marqueurs une première fois en les cachant pour pouvoir les afficher ou
                    //non derrière
                    for (children in snapshot.children) {
                            val marker = children.getValue(Marqueur::class.java)
                            val latitude = marker!!.latitude!!.toDouble()
                            val longitude = marker.longitude!!.toDouble()
                            val position = LatLng(latitude, longitude)
                            val index = marker.id
                            googleMap.addMarker(
                                MarkerOptions()
                                    .position(position)
                                    .title(index.toString())
                                    .icon(BitmapDescriptorFactory.defaultMarker(randomColor(index!!)))
                                    .zIndex(1.0f).visible(false)
                            )?.let { lesMarqueurs.add(it) }
                    }
                    //END
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i(TAG, "onCancelled: Error: " + error.message);
                }
            })

            //Référencement de la BD au niveau des users + on trie les users par ID
            val users= database.getReference("User")
            query = users.orderByKey()

            //START Query qui permet de récupérer la position tous les joueurs de la table
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    for(children in snapshot.children){
                        if(children.key!=user.uid){
                            val latitude = children.child("Position").child("latitude").value
                            val longitude = children.child("Position").child("longitude").value
                            lesUsers.add(children.key.toString())

                            googleMap.addMarker(
                                MarkerOptions()
                                    .position(LatLng(latitude as Double,longitude as Double ))
                                    .title(children.key.toString())
                                    .zIndex(1.0f).visible(true)
                            )?.let { playerMarqueurs.add(it) }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i(TAG, "onCancelled: Error: " + error.message);
                }
            })


            //à chaque tik:
            chronometre.onChronometerTickListener = Chronometer.OnChronometerTickListener {
                //START Si la position du joueur a changé alors on la met à jour
                pos.refreshLocation()
                val loc = LatLng(pos.getLatitude(), pos.getLongitude())
                CurrentMarkerPosition.remove()
                CurrentMarkerPosition=googleMap.addMarker(MarkerOptions()
                    .position(loc)
                    .title("Current Position"))!!
                //END

                //rafraîchit l'affichage des marqueurs
                viewMarker()
                //rafraichit la position des autres joueurs
                viewPlayers()
                //Zoom de la caméra sur la position du joueur
                if (cameraFocus) {
                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                pos.getLatitude(),
                                pos.getLongitude()
                            ), 15f
                        )
                    )
                }
            }

            //Différentiation des use case en fonction du type de marker
            //Si le joueur click sur son marker
            googleMap.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener { Marker ->
                //Si le joueur click sur son marker
                when {
                    Marker.title.toString() == "Current Position" -> {
                        Toast.makeText(
                            this,
                            "Votre position: Lat " + pos.getLatitude() + " Long: " + pos.getLongitude(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    //si il click sur un ennemi
                    Marker.title.toString() == "Un ennemi !" -> {
                        val dialog = MarkerDialog()
                        val navHostFragment = supportFragmentManager
                        dialog.show(navHostFragment, "MarkerDialog")
                        spawnTime = 0
                        randomPosition = ThreadLocalRandom.current().nextInt(0, 4)
                        randomSpawnTime = ThreadLocalRandom.current().nextInt(40, 300)
                    }
                    //si il il click sur un autre joueur
                    Marker.title.toString() in lesUsers -> {
                        val pseudo =database.getReference("User").child(Marker.title.toString()).child("pseudo").toString()
                        Toast.makeText(this, "Oh, voici un autre joueur ! Bonjour :)",Toast.LENGTH_LONG).show()
                        database.getReference("User").child(user.uid.toString()).child("Stats").child("TotalJoueurs").child(Marker.title.toString()).setValue(pseudo)
                    }
                    //Et si il click sur un lieu
                    else -> {
                        val index = Marker.title?.toInt()
                        if (index != null) {
                            //On récupère le temps actuel
                            val currenTime= System.currentTimeMillis()
                            DropItem(index)
                            val update = HashMap<String, Any>()
                            update["visible"] = 0
                            update["last_updated"]=currenTime
                            database.getReference("Marqueur").child(Marker.title.toString())
                                .updateChildren(update)
                            Marker.isVisible = false
                        }
                        //MAJ des stats, +1 lieu fouillé et +1 item récupéré
                        Stats(this, 1).upMarqueurs()
                        Stats(this, 1).upItems()
                    }
                }
                true
            })


        }
    }

    private fun viewPlayers() {
        //Référencement de la BD au niveau des users + on trie les users par ID
        val users= database.getReference("User")
        val query: Query = users.orderByKey()

        //START Query qui permet de récupérer la position tous les joueurs de la table
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var nbPlayers=0
                var deltaPosition: Double
                //Pour chaque user...
                for(children in snapshot.children){
                    if(children.key!=user.uid){
                        //START on récupère la dernière localisation (latitude et longitude)
                        val latitude = children.child("Position").child("latitude").value
                        val longitude = children.child("Position").child("longitude").value
                        //END
                        //START on la compare avec l'ancienne position enregistrée du joueur
                        val oldlatitude =playerMarqueurs[nbPlayers].position.latitude
                        val oldlongitude =playerMarqueurs[nbPlayers].position.longitude
                        //calcul de la distance entre les deux positions
                        deltaPosition= pos.calcul_distance(latitude as Double,longitude as Double,oldlatitude,oldlongitude)
                        //Si la distance est >=150 ALORS on remplace le marqueur de l'ancienne position par un nouveau
                        if(deltaPosition>=150){
                            playerMarqueurs[nbPlayers].remove()
                            googleMap.addMarker(
                                MarkerOptions()
                                    .position(LatLng(latitude,longitude))
                                    .title(children.key.toString())
                                    .zIndex(1.0f).visible(true)
                            )?.let { playerMarqueurs.add(nbPlayers, it) }
                        }
                        //END
                        nbPlayers++
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(TAG, "onCancelled: Error: " + error.message);
            }
        })
    }

    //Méthode qui permet d'afficher ou non les marqueurs du jeu. Il y a le marqueur du joueur, le marqueur des ennemis et les lieux proches
    fun viewMarker() {
        //Référencement de la BD au niveau des marqueurs + on trie les marqueurs par ID
        val markers= database.getReference("Marqueur")
        val query: Query = markers.orderByKey()

        //Query qui permet de récupérer tous les marqueurs de la table
        query.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //START Pour chaque marqueur on check si on l'affiche ou pas
                for(children in snapshot.children){
                    val marker = children.getValue(Marqueur::class.java)
                    val latitude= marker!!.latitude!!.toDouble()
                    val longitude= marker.longitude!!.toDouble()
                    val position = LatLng(latitude,longitude)
                    val distance= distanceMarker(position)
                    val index= marker.id

                    //START Hashmap qui nous permettra de vérifier si un marker a déjà été ajouté ou non
                    val markersAdded = HashMap<Int,Marker>()
                    val isPresent= HashMap<Marker,Boolean>()
                    isPresent[lesMarqueurs[index!!]] = false
                    markersAdded[index] = lesMarqueurs[index]
                    //END

                    //On récupère le temps actuel
                    val currenTime= System.currentTimeMillis()

                    //Si la distance entre le joueur et le lieu est inférieure à 1500m, on affiche le lieu
                    //Ou si ça fait plus d'une minute que le lieu est caché car on a clické dessus
                    if(distance <1500) {
                        if (marker.visible == 1) {
                            if(markersAdded[index]?.isVisible==false){
                                markersAdded[index]?.isVisible=true
                            }
                        }
                        //Si (temps actuel - last_updated du marker) converti en seconde > 5 minutes, alors on affiche le marqueur
                        else if ((System.currentTimeMillis()
                                .minus(marker.last_updated!!) / 1000) > 300)
                        {
                            if(markersAdded[index]?.isVisible==false){
                                markersAdded[index]?.isVisible=true
                            }
                            //et on le met à jour en indiquant qu'il est visible à nouveau (MarqueurVisible = 1
                            val update =HashMap<String,Any>()
                            update["visible"] = 1
                            update["last_updated"]=currenTime
                            database.getReference("Marqueur").child(marker.id.toString()).updateChildren(update)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i(TAG, "onCancelled: Error: " + error.message);
            }
        })
            //Affichage de l'ennemi à refactorer dans une autre classe
            if(this.spawnTime== randomSpawnTime ){
                randomLat = ThreadLocalRandom.current().nextDouble(0.0001,0.0009)
                randomLong = ThreadLocalRandom.current().nextDouble(0.0001,0.0009)
                randomLat*=1
            }
            //Toutes les randomSpawnTime secondes, on fait pop un marker "ennemi"
            if(this.spawnTime > randomSpawnTime){
                // En fonction du nombre randomPosition généré, l'affichage de l'ennemi se fera dans une zone particulière (45°) parmi 360°
                when (randomPosition%4){
                    0-> {randomLat*=1
                        randomLong*=1}
                    1->{randomLat*=1
                        randomLong*=-1}
                    2->{randomLat*=-1
                        randomLong*=1}
                    3->{randomLat*=-1
                        randomLong*=-1}
                }
                googleMap.addMarker(MarkerOptions()
                    .position(LatLng(pos.getLatitude() + randomLat, pos.getLongitude() + randomLong))
                    .title("Un ennemi !")
                    .icon(BitmapDescriptorFactory.defaultMarker(HUE_ORANGE))
                    .zIndex(1.0f))
            }
            spawnTime+=1

        }

    //Méthode qui identifie le type de lieu et qui drop l'item correspondant
    fun DropItem(index: Int) {
        var id:Int
        try{
            id= itemHandler.view().last().getItemid()+1
        }
        catch (e:java.util.NoSuchElementException){
            id=1
        }
        currentPersonnage.setArgent(2000)
        personnage.upArgent(currentPersonnage.argent)
        //Toast.makeText(this, currentPersonnage.getArgent().toString(), Toast.LENGTH_LONG).show()
        when (index %4) {
            0 -> { Toast.makeText(this,"Vous trouvez un vieux bouclier dans un buisson",Toast.LENGTH_LONG).show()
                this.itemHandler.add(Item(id, ListItems.BOUCLIER.toString(),"Bouclier simple","Parfait pour les débutants",1.0,2.0))
            }
            1 -> {Toast.makeText(this,"Une épée rouillée jonche le sol. Vous la ramassez.",Toast.LENGTH_LONG).show()
                this.itemHandler.add(Item(id, ListItems.EPEE.toString(),"Epee de combat","Une épée basique",3.0,1.0))
            }
            2 -> { Toast.makeText(this,"Vous avez trouvé des chaussures en cuir abandonnées. Ca peut toujours servir",Toast.LENGTH_LONG).show()
                this.itemHandler.add(Item(id, ListItems.CHAUSSURES.toString(),"Bottes basiques","Pas très confortable",1.0,1.0))
            }
            3 -> { Toast.makeText(this,"Une armure en cuir ! Quelle chance !",Toast.LENGTH_LONG).show()
                this.itemHandler.add(Item(id, ListItems.ARMURE.toString(),"Armure simple","une armure en cuivre",0.0,3.0))
            }
        }
    }

    //Méthode qui attribue pour chaque marqueur une couleur, correspondant à un type de lieu
    fun randomColor(index : Int): Float {
        if (index %4 == 0) {
            return BitmapDescriptorFactory.HUE_CYAN
        }
        if (index %4 == 1) {
            return BitmapDescriptorFactory.HUE_YELLOW
        }
        return if (index %4 == 2) {
            BitmapDescriptorFactory.HUE_GREEN
        } else BitmapDescriptorFactory.HUE_VIOLET
    }

    fun visible() {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            //Le nom du fichier json qui permet de définir l'affichage de la map
            val success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, com.example.akenasia.R.raw.style_visible))
            //message d'erreur si le json n'est pas appliqué
            if (!success) { Log.e(TAG, "Style parsing failed.")}
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

    fun distanceMarker(marker: LatLng): Double {
        return pos.calcul_distance(
            pos.getLatitude(),
            pos.getLongitude(),
            marker.latitude,
            marker.longitude)
    }


}






