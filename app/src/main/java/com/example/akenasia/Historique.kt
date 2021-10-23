package com.example.akenasia

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.akenasia.databinding.HistoriqueBinding
import kotlinx.android.synthetic.main.database.*
import java.util.ArrayList

class Historique: Fragment() {
    private var _binding: HistoriqueBinding? = null
    private lateinit var pos: Position
    private lateinit var positions: ArrayList<PositionTable>
    private lateinit var dbHandler: DatabaseHandler
    private lateinit var positionListView: ListView
    private lateinit var positionAdapter: PositionAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var thiscontext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pos = Position(this.requireActivity())
        if (container != null) {
            thiscontext = container.getContext()
            dbHandler = DatabaseHandler(thiscontext!!)

        }
        _binding = HistoriqueBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewRecord()
    }

    private fun viewRecord() {
        //creating the instance of DatabaseHandler class
        val databaseHandler: DatabaseHandler = DatabaseHandler(thiscontext!!)
        //calling the viewPlace method of DatabaseHandler class to read the records
        val emp: List<PositionTable> = databaseHandler.viewPosition(1)
        val empArrayId = Array<String>(emp.size) { "0" }
        val empArrayLat = Array<String>(emp.size) { "null" }
        val empArrayLong = Array<String>(emp.size) { "null" }
        val empArrayPartie = Array<String>(emp.size) { "null" }

        var index = 0
        for (e in emp) {
            empArrayId[index] = e.getposId().toString()
            empArrayLat[index] = e.getposLat().toString()
            empArrayLong[index] = e.getposLong().toString()
            empArrayPartie[index] = e.getpartie().toString()

            index++
        }
        //creating custom ArrayAdapter
        val myPositionAdapter = PositionAdapter(
            this.requireActivity(),
            empArrayId,
            empArrayLat,
            empArrayLong,
            empArrayPartie
        )
        listView.adapter = myPositionAdapter
    }
}