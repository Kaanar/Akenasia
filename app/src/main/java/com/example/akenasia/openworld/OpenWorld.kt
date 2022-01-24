package com.example.akenasia.openworld

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.akenasia.R
import com.example.akenasia.databinding.ActivityOpenworldBinding
import com.example.akenasia.home.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_openworld.*
import kotlinx.android.synthetic.main.content_map.*
import android.content.res.Resources
import android.util.Log
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.Handler.MarqueurHandler
import com.example.akenasia.Handler.PersonnageHandler
import com.example.akenasia.database.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ORANGE
import java.time.LocalTime
import java.util.concurrent.ThreadLocalRandom

class OpenWorld : AppCompatActivity(),OnMapReadyCallback {

    private val TAG: String = OpenWorld::class.java.getSimpleName()


    private lateinit var pos: Position
    private var isPlay: Boolean = false
    private lateinit var binding: ActivityOpenworldBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var googleMap: GoogleMap
    private lateinit var chronometre: Chronometer
    private lateinit var listMarker : ArrayList<LatLng>
    private lateinit var Markers: HashMap<Int,LatLng>
    private lateinit var currentPersonnage: PersonnageTable
    lateinit var itemHandler: ItemHandler
    lateinit var personnageHandler: PersonnageHandler
    lateinit var marqueurHandler: MarqueurHandler
    private var cameraFocus: Boolean = true
    private var spawnTime= 0


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
        personnageHandler= PersonnageHandler(this)
        currentPersonnage= personnageHandler.get(1)
        Markers= HashMap()


        //Mise en place d'un navcontroller pour d'eventuels fragments
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.include3) as NavHostFragment?
        val navController = navHostFragment?.navController
        if (navController != null) {
            appBarConfiguration = AppBarConfiguration(navController.graph)
        }
        if (navController != null) {
            setupActionBarWithNavController(navController, appBarConfiguration)
        }

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


        binding.NavigationView.selectedItemId = R.id.MapClick

        //Implémentation des différents choix du menu
        binding.NavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                    R.id.QuitClick -> {
                        val intent = Intent(this, MainActivity::class.java)
                        this.startActivity(intent)
                        true
                    }
                    R.id.MapClick -> {
                        val intent = Intent(this, OpenWorld::class.java)
                        this.startActivity(intent)
                        true
                    }
                    R.id.BagClick -> {
                        val intent = Intent(this, Bag::class.java)
                        this.startActivity(intent)
                        true
                    }
                    else -> {
                        val intent = Intent(this, Personnage::class.java)
                        this.startActivity(intent)
                        true
                    }
                }
            true
        }

        CameraSwitch.setOnClickListener(){
            cameraFocus = cameraFocus != true
        }
    }


    //function qui ajoute des lieux près du joueur
    private fun FillMap(): HashMap<Int,LatLng>{
        val nb=150
        var randomradius : Int
        var randomLat: Double
        var randomLong: Double
        val markers: HashMap<Int,LatLng> = HashMap()
        pos.refreshLocation()
        for(i in 1..nb){
            randomLat = ThreadLocalRandom.current().nextDouble(0.000,0.10)
            randomLong = ThreadLocalRandom.current().nextDouble(0.000,0.10)
            randomradius = ThreadLocalRandom.current().nextInt(40)
            when (randomradius%4){
                0->markers.put(i,LatLng(pos.getLatitude() + randomLat, pos.getLongitude() +randomLong))
                1->markers.put(i,LatLng(pos.getLatitude() + randomLat, pos.getLongitude() -randomLong))
                2->markers.put(i,LatLng(pos.getLatitude() - randomLat, pos.getLongitude() +randomLong))
                3->markers.put(i,LatLng(pos.getLatitude() - randomLat, pos.getLongitude() -randomLong))
            }
        }
        return markers
    }

    override fun onMapReady(map: GoogleMap) {
        map.let {
            googleMap = it
            visible()


            //Peuplement de latlng dans la bdd
            if(marqueurHandler.view().isEmpty()){
                Markers=FillMap()
                for(x in Markers){
                    marqueurHandler.add(x.key,x.value)
                }
            }

            //rafraîchit la position du joueur à chaque tik
            chronometre.onChronometerTickListener = Chronometer.OnChronometerTickListener {
                //MAJ de l'affichage de la position du joueur
                pos.refreshLocation()
                val location= LatLng(pos.getLatitude(), pos.getLongitude())
                googleMap.clear()
                googleMap.addMarker(MarkerOptions()
                    .position(location)
                    .title("Current Position"))
                viewMarker()

                //Zoom de la caméra sur la position du joueur
                if (cameraFocus) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15f))
                }
            }

            //Différentiation des use case en fonction du type de marker

            googleMap.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener { Marker ->
                // Quand le joueur clique sur un marker, ses points et son niveau sont recalculés
                UpdatePointLevel()
                //Si le joueur click sur son marker
                if(Marker.title.toString() == "Current Position"){
                    Toast.makeText(this,"Votre position: Lat " + pos.getLatitude()+" Long: "+ pos.getLongitude(),Toast.LENGTH_SHORT).show()
                }
                //si il click sur un ennemi
                else if (Marker.title.toString() == "Un ennemi !"){
                    val dialog = MarkerDialog()
                    val navHostFragment = supportFragmentManager
                    dialog.show(navHostFragment, "MarkerDialog")
                    spawnTime=0
                    randomPosition = ThreadLocalRandom.current().nextInt(0,4)
                    randomSpawnTime = ThreadLocalRandom.current().nextInt(4000,30000)
                }
                //si il il click sur un lieu
                else{
                    val index= Marker.title?.toInt()
                    if (index != null) {
                        DropItem(index)
                        marqueurHandler.update(Marqueur(index,Marker.position,2,System.currentTimeMillis()))
                    }
                }
                true


            })
        }
        }

    //Méthode qui permet d'afficher ou non les marqueurs du jeu. Il y a le marqueur du joueur, le marqueur des ennemis et les lieux proches
    fun viewMarker() {
        val listLatLng=marqueurHandler.view()
        for (e in listLatLng) {
            val marker = LatLng(e.getMarqueurLocation().latitude,e.getMarqueurLocation().longitude)
            val distance = distanceMarker(marker)
            val index = e.getMarqueurId()

            //Affichage de l'ennemi à refactorer dans une autre classe

            if(this.spawnTime== randomSpawnTime ){
                randomLat = ThreadLocalRandom.current().nextDouble(0.0001,0.0009)
                randomLong = ThreadLocalRandom.current().nextDouble(0.0001,0.0009)
            }
            //Toutes les randomSpawnTime secondes, on fait pop un marker "ennemi"
            if(this.spawnTime > randomSpawnTime){
                // En fonction du nombre randomPosition généré, l'affichage de l'ennemi se fera dans une zone particulière (45°) parmi 360°
                when (randomPosition%4){
                    0->googleMap.addMarker(MarkerOptions()
                        .position(LatLng(pos.getLatitude() + randomLat, pos.getLongitude() + randomLong))
                        .title("Un ennemi !")
                        .icon(BitmapDescriptorFactory.defaultMarker(HUE_ORANGE))
                        .zIndex(1.0f)
                    )
                    1->googleMap.addMarker(MarkerOptions()
                        .position(LatLng(pos.getLatitude() + randomLat, pos.getLongitude() - randomLong))
                        .title("Un ennemi !")
                        .icon(BitmapDescriptorFactory.defaultMarker(HUE_ORANGE))
                        .zIndex(1.0f)
                    )
                    2->googleMap.addMarker(MarkerOptions()
                        .position(LatLng(pos.getLatitude() - randomLat, pos.getLongitude() + randomLong))
                        .title("Un ennemi !")
                        .icon(BitmapDescriptorFactory.defaultMarker(HUE_ORANGE))
                        .zIndex(1.0f)
                    )
                    3->googleMap.addMarker(MarkerOptions()
                        .position(LatLng(pos.getLatitude() - randomLat, pos.getLongitude() - randomLong))
                        .title("Un ennemi !")
                        .icon(BitmapDescriptorFactory.defaultMarker(HUE_ORANGE))
                        .zIndex(1.0f)
                    )
                }

            }
            spawnTime+=1
            //On récupère le temps actuel
            val currenTime= System.currentTimeMillis()
            //Si la distance entre le joueur et le lieu est inférieure à 150m, on affiche le lieu
            //Ou si ça fait plus d'une minute que le lieu est caché car on a clické dessus
            if(distance < 1500){
                if(e.getMarqueurVisible() == 1 ) {
                    googleMap.addMarker(MarkerOptions()
                        .position(marker)
                        .title(index.toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(randomColor(index)))
                        .zIndex(1.0f)
                    )
                }
                //Si (temps actuel - last_updated du marker) converti en seconde > 5 minutes, alors on affiche le marqueur
                else if((System.currentTimeMillis().minus(marqueurHandler.get(index).getMarqueurLastUpdated())/1000) > 10){
                    googleMap.addMarker(MarkerOptions()
                        .position(marker)
                        .title(index.toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(randomColor(index)))
                        .zIndex(1.0f)
                    )
                    //et on le met à jour en indiquant qu'il est visible à nouveau (MarqueurVisible = 1
                    marqueurHandler.update(Marqueur(e.getMarqueurId(),
                        e.getMarqueurLocation(),
                        1,
                        currenTime)
                    )
                }
            }

        }
    }

    fun UpdatePointLevel(){
        currentPersonnage.setPoints()
        //update des points d'xp en BD sur cette ligne -> personnageHandler.setXP(currentpersonnage)
        if (currentPersonnage.getPoints()%150 == 0){
            currentPersonnage.setLevel()
            //UPDatde des points d'xp en bd sur cette ligne
        }
    }

    //Méthode qui identifie le type de lieu et qui drop l'item correspondant
    fun DropItem(index: Int) {
        var id:Int
        val type = (0..3).random() // generated random from 1 to 3 included
        try{
            id= itemHandler.view().last().getItemid()+1
        }
        catch (e:java.util.NoSuchElementException){
            id=1
        }

        when (index %4) {
            0 -> { when (type%3){
                0 -> {Toast.makeText(this,"Vous trouvez un vieux bouclier type1 dans un buisson",Toast.LENGTH_LONG).show()
                    this.itemHandler.add(Item(id, ListItems.BOUCLIER.toString(),"Bouclier type1","Parfait pour les débutants",1.0,2.0))}
                1 -> {Toast.makeText(this,"Vous trouvez un vieux bouclier type2 dans un buisson",Toast.LENGTH_LONG).show()
                    this.itemHandler.add(Item(id, ListItems.BOUCLIER.toString(),"Bouclier type2","Parfait pour les débutants",5.0,10.0))}
                2 -> {Toast.makeText(this,"Vous trouvez un vieux bouclier type  dans un buisson",Toast.LENGTH_LONG).show()
                    this.itemHandler.add(Item(id, ListItems.BOUCLIER.toString(),"Bouclier type3","Parfait pour les débutants",10.0,20.0))}
                }
            }
            1 -> { when (type%3){
                0 -> {Toast.makeText(this,"Une épée rouillée type1 jonche le sol. Vous la ramassez.",Toast.LENGTH_LONG).show()
                    this.itemHandler.add(Item(id, ListItems.EPEE.toString(),"Epee de combat type1","Une épée basique",3.0,1.0))}
                1 -> {Toast.makeText(this,"Une épée rouillée type2 jonche le sol. Vous la ramassez.",Toast.LENGTH_LONG).show()
                    this.itemHandler.add(Item(id, ListItems.EPEE.toString(),"Epee de combat type2","Une épée basique",6.0,2.0))}
                2 -> {Toast.makeText(this,"Une épée rouillée type3 jonche le sol. Vous la ramassez.",Toast.LENGTH_LONG).show()
                    this.itemHandler.add(Item(id, ListItems.EPEE.toString(),"Epee de combat type3","Une épée basique",9.0,4.0))}
                }
            }
            2 -> { when (type%3){
                0 -> {Toast.makeText(this,"Vous avez trouvé des chaussures en cuir type1 abandonnées. Ca peut toujours servir",Toast.LENGTH_LONG).show()
                    this.itemHandler.add(Item(id, ListItems.CHAUSSURES.toString(),"Bottes type1","Pas très confortable",1.0,1.0))}
                1 -> {Toast.makeText(this,"Vous avez trouvé des chaussures en cuir type2 abandonnées. Ca peut toujours servir",Toast.LENGTH_LONG).show()
                    this.itemHandler.add(Item(id, ListItems.CHAUSSURES.toString(),"Bottes type2","Pas très confortable",3.0,2.0))}
                2 -> {Toast.makeText(this,"Vous avez trouvé des chaussures en cuir type3 abandonnées. Ca peut toujours servir",Toast.LENGTH_LONG).show()
                    this.itemHandler.add(Item(id, ListItems.CHAUSSURES.toString(),"Bottes type3","Pas très confortable",6.0,4.0))}
                }
            }
            3 -> { when (type%3){
                0 -> {Toast.makeText(this,"Une armure en cuir type1! Quelle chance !",Toast.LENGTH_LONG).show()
                    this.itemHandler.add(Item(id, ListItems.ARMURE.toString(),"Armure type1","une armure en cuivre",0.0,3.0))}
                1 -> {Toast.makeText(this,"Une armure en cuir type2! Quelle chance !",Toast.LENGTH_LONG).show()
                    this.itemHandler.add(Item(id, ListItems.ARMURE.toString(),"Armure type2","une armure en cuivre",0.0,6.0))}
                2 -> {Toast.makeText(this,"Une armure en cuir type3! Quelle chance !",Toast.LENGTH_LONG).show()
                    this.itemHandler.add(Item(id, ListItems.ARMURE.toString(),"Armure type3","une armure en cuivre",0.0,9.0))}
                }
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
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.style_visible
                    //Le nom du fichier json qui permet de définir l'affichage de la map
                )
            )
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
                //message d'erreur si le json n'est pas appliqué
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

    fun distanceMarker(marker: LatLng): Double {
        return pos.calcul_distance(
            pos.getLatitude(),
            pos.getLongitude(),
            marker.latitude,
            marker.longitude
        )
    }

}
