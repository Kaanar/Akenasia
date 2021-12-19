package com.example.akenasia.openworld

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.akenasia.R
import com.example.akenasia.databinding.ActivityOpenworldBinding
import com.example.akenasia.home.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_openworld.*
import kotlinx.android.synthetic.main.content_map.*
import android.content.res.Resources
import android.graphics.Color.GREEN
import android.provider.ContactsContract
import android.util.Log
import com.example.akenasia.database.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ORANGE
import java.util.concurrent.ThreadLocalRandom

class OpenWorld : AppCompatActivity(),OnMapReadyCallback, GoogleMap.OnPoiClickListener {

    private val TAG: String = OpenWorld::class.java.getSimpleName()


    private lateinit var pos: Position
    private var isPlay: Boolean = false
    private lateinit var binding: ActivityOpenworldBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var googleMap: GoogleMap
    private lateinit var chronometre: Chronometer
    private lateinit var listMarker : ArrayList<LatLng>
    private lateinit var Markers: HashMap<LatLng,Int>
    lateinit var itemHandler: ItemHandler
    private var cameraFocus: Boolean = true
    private var spawnTime= 0
    private var randomLat = ThreadLocalRandom.current().nextDouble(0.0001,0.0009)
    private var randomLong = ThreadLocalRandom.current().nextDouble(0.0001,0.0009)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenworldBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pos = Position(this)
        pos.refreshLocation()
        itemHandler= ItemHandler(this)
        Markers= HashMap()

        //Peuplement de la zone du joueur
        //Markers = FillMap(pos)

        //Ajout de markers en dur
        var i=0
        val a = LatLng(37.4213234578268, -122.08250150084496)
        val b = LatLng(37.422509958470826, -122.08360254764557)
        val c = LatLng(37.421, -122.082)
        val d = LatLng(37.422, -122.083)
        val e = LatLng(37.5, -122.083)
        val f = LatLng(37.4213234578268, -122.083)
        listMarker = ArrayList()
        listMarker.add(a)
        listMarker.add(b)
        listMarker.add(c)
        listMarker.add(d)
        listMarker.add(e)
        listMarker.add(f)

        for (x in listMarker) {
            Markers.put(e,i)
            i++
        }

        //Indicateur qui permet de faire spawn un ennemi
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
    private fun FillMap(pos: Position): HashMap<LatLng,Int>{
        val nb=15
        var randomLat: Double
        var randomLong: Double
        val markers: HashMap<LatLng,Int> = HashMap()
        for(i in 0..nb){
                randomLat = ThreadLocalRandom.current().nextDouble(0.0001,0.0009)
                randomLong = ThreadLocalRandom.current().nextDouble(0.0001,0.0009)
                markers.put(LatLng( pos.getLatitude() + randomLat, pos.getLongitude() + randomLong),i)
        }
        return markers
    }

    override fun onMapReady(map: GoogleMap) {
        map.let {
            googleMap = it
            googleMap.setOnPoiClickListener(this)
            visible()

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

            googleMap.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener { Marker ->
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
                }
                //si il il click sur un lieu
                else{
                    val index= Marker.title?.toInt()
                    if (index != null) {
                        DropItem(index)
                        Marker.isVisible=false
                    }
                }
                true
            })
        }
        }



    //Implémentation de la méthode lorsqu'on click sur un POI
    override fun onPoiClick(poi: PointOfInterest) {
        val distance = distancePoi(poi)
        val navHostFragment = supportFragmentManager       //calcul de la distance
        if (distance < 150) {

            val dialog = PoiDialog()
            dialog.setName(updateTitle(poi))
            dialog.setLatLong(updateInfo(poi))
            dialog.show(navHostFragment, "PoiDialog") //ça pareil ça compile pas
        }
    }


    fun viewMarker() {
        var index = 0
        val listLat = Array<Double>(listMarker.size) { 0.0 }
        val listLong = Array<Double>(listMarker.size) { 0.0 }
        for (e in listMarker) {

            listLat[index] = e.latitude
            listLong[index] = e.longitude


            val marker = LatLng( listLat[index], listLong[index])
            val distance = distanceMarker(marker)

            if(this.spawnTime==360){
                randomLat = ThreadLocalRandom.current().nextDouble(0.0001,0.0009)
                randomLong = ThreadLocalRandom.current().nextDouble(0.0001,0.0009)
            }
            //Toutes les 60 secondes, on fait pop un marker "ennemi"
            if(this.spawnTime > 360){
                googleMap.addMarker(MarkerOptions()
                    .position(LatLng(pos.getLatitude() + randomLat, pos.getLongitude() + randomLong))
                    .title("Un ennemi !")
                    .icon(BitmapDescriptorFactory.defaultMarker(HUE_ORANGE))
                    .zIndex(1.0f)
                )
            }
            spawnTime+=1

            if(distance < 150) {
                googleMap.addMarker(MarkerOptions()
                    .position(marker)
                    .title(index.toString())
                    .icon(BitmapDescriptorFactory.defaultMarker(randomColor(index)))
                    .zIndex(1.0f)
                )
            }
            index++
        }
    }

    fun DropItem(index: Int) {
        var id:Int
        try{
            id= itemHandler.view().last().getItemid()+1
        }
        catch (e:java.util.NoSuchElementException){
            id=1
        }

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


    fun updateTitle(poi: PointOfInterest) : String {
        return poi.name
    }

    fun updateInfo(poi: PointOfInterest) : String {
        return poi.latLng.latitude.toString() +"\n" + poi.latLng.longitude.toString()
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

    fun distancePoi(poi: PointOfInterest): Double {
        return pos.calcul_distance(
            pos.getLatitude(),
            pos.getLongitude(),
            poi.latLng.latitude,
            poi.latLng.longitude,
        )
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
