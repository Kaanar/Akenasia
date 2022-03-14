package com.example.akenasia.home

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.akenasia.adapter.MyListAdapter
import com.example.akenasia.R
import com.example.akenasia.database.Place
import com.example.akenasia.handler.PlaceHandler
import com.example.akenasia.database.Position
import com.example.akenasia.databinding.DatabaseBinding
import kotlinx.android.synthetic.main.database.*
import java.util.ArrayList


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class Database : Fragment() {

    private var _binding: DatabaseBinding? = null
    private lateinit var pos: Position
    private lateinit var places: ArrayList<Place>
    private lateinit var placeHandler : PlaceHandler
    private val binding get() = _binding!!
    private var thiscontext: Context? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pos = Position(this.requireActivity())
        if (container != null) {
            thiscontext = container.getContext()
            placeHandler = PlaceHandler(thiscontext!!)
        }
        _binding = DatabaseBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewRecord()
        // Instanciation des positions en dur
        places = ArrayList<Place>()
        if(placeHandler.view().isEmpty()){
            placeHandler.add(
                Place(
                0,
                "BU",
                48.905273887110944,
                2.2156870365142827
            )
            )
            placeHandler.add(
                Place(
                    1,
                    "Crous",
                    48.904096168019976,
                    2.216480970382691
                )
            )
            placeHandler.add(
                Place(
                    2,
                    "Bat G",
                    48.903158204219174,
                    2.2155475616455083
                )
            )
        }
        viewRecord()
        binding.bSave.setOnClickListener {
            saveRecord()
        }
        binding.bDelete.setOnClickListener {
            deleteRecord()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveRecord() {
        pos.refreshLocation()//appel de la méthode qui récupère les coordonnées GPS de l'appareil
        val name = u_name.text.toString()
        val latitude = pos.getLatitude().toString()
        val longitude = pos.getLongitude().toString()

        val emp: List<Place> = placeHandler.view()

        val id = emp.size.toString()

        if (id.trim() != "" && name.trim() != "" && latitude.trim() !="" && longitude.trim() != "") {
            val status = placeHandler.add(Place(Integer.parseInt(id), name, latitude.toDouble(), longitude.toDouble()))
            if (status > -1) {
                Toast.makeText(activity, "Place added", Toast.LENGTH_LONG).show()
                u_name.text.clear()
                viewRecord()
            }
        } else {
            Toast.makeText(
                activity,
                "Name cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun orderRecord(deleteId: Int) {
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val emp: List<Place> = placeHandler.view()
        val empArrayId = Array<String>(emp.size) { "0" }
        val empArrayName = Array<String>(emp.size) { "null" }
        val empArrayLat = Array<String>(emp.size) { "null" }
        val empArrayLong = Array<String>(emp.size) { "null" }

        var index = 0
        for (e in emp) {
            empArrayId[index] = e.placeId.toString()
            empArrayName[index] = e.placeName
            empArrayLat[index] = e.placeLat.toString()
            empArrayLong[index] = e.placeLong.toString()
            if (e.placeId < deleteId) {
                Toast.makeText(activity, e.placeId.toString(), Toast.LENGTH_LONG).show()
            } else {
                if (e.placeId.toString().trim() != "") {
                    val status = placeHandler.update(
                        Place(e.placeId,
                            e.placeName,
                            e.placeLat,
                            e.placeLong
                        )
                    )
                    if (status > -1) {
                        Toast.makeText(activity, "record update", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(activity, "Id cannot be blank", Toast.LENGTH_LONG).show()
                }
            }
            index++
        }
    }

    private fun viewRecord(){
        //calling the viewPlace method of DatabaseHandler class to read the records
        val emp: List<Place> = placeHandler.view()
        val empArrayId = Array<String>(emp.size){"0"}
        val empArrayName = Array<String>(emp.size){"null"}
        val empArrayLat = Array<String>(emp.size){"null"}
        val empArrayLong = Array<String>(emp.size){"null"}

        var index = 0
        for(e in emp){
            empArrayId[index] = e.placeId.toString()
            empArrayName[index] = e.placeName
            empArrayLat[index] = e.placeLat.toString()
            empArrayLong[index] = e.placeLong.toString()

            index++
        }
        //creating custom ArrayAdapter
        val myListAdapter = MyListAdapter(this.requireActivity(),empArrayId,empArrayName, empArrayLat, empArrayLong)
        listView.adapter = myListAdapter
    }

    private fun deleteRecord(){
        //creating AlertDialog for taking user id
        val dialogBuilder = AlertDialog.Builder(thiscontext!!)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val dltId = dialogView.findViewById(R.id.deleteId) as EditText
        dialogBuilder.setTitle("Delete Place")
        dialogBuilder.setMessage("Enter Id below")
        dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->

            val deleteId = dltId.text.toString()
            if(deleteId.trim()!=""){
                //calling the deleteEmployee method of DatabaseHandler class to delete record
                val status = placeHandler.delete(Integer.parseInt(deleteId))
                if(status > -1){
                    Toast.makeText(activity,"Place deleted", Toast.LENGTH_LONG).show()
                    orderRecord(Integer.parseInt(deleteId))
                    viewRecord()
                }
            }else{
                Toast.makeText(activity,"Id cannot be blank", Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }
}
