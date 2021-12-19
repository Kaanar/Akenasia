package com.example.akenasia.openworld

import ItemTypeAdapter
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.akenasia.R
import com.example.akenasia.database.Item
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.database.ListItems
import kotlinx.android.synthetic.main.bag.*
import kotlinx.android.synthetic.main.item_dialog.view.*
import java.io.Serializable

class ItemDialog : DialogFragment() {
    private lateinit var itemHandler : ItemHandler
    private lateinit var title : String
    private lateinit var type : Serializable



    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, saveInstanceState : Bundle?) :
            View {
        val rootView= inflater.inflate(R.layout.item_dialog, container, false)
        itemHandler = ItemHandler(context!!)
        rootView.ItemInfos.text=title

        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val emp: List<Item> = itemHandler.viewByType(type)
        val empArrayId = Array<String>(emp.size){"0"}
        val empArrayName = Array<String>(emp.size){"null"}
        val empArrayDesc = Array<String>(emp.size){"null"}
        val empArrayAtt = Array<String>(emp.size){"null"}
        val empArrayDef = Array<String>(emp.size){"null"}


        var index = 0
        for(e in emp){
            empArrayId[index] = e.Itemid.toString()
            empArrayName[index] = e.ItemName
            empArrayDesc[index] = e.ItemDesc
            empArrayAtt[index] = e.ItemAtt.toString()
            empArrayDef[index] = e.ItemDef.toString()
            index++
        }
        //creating custom ArrayAdapter
        val myListAdapter = ItemTypeAdapter(this.requireActivity(),empArrayId,empArrayName, empArrayDesc, empArrayAtt, empArrayDef)
        ListViewItem?.adapter = myListAdapter
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        type= requireArguments().getSerializable("type")!!
        return super.onCreateDialog(savedInstanceState)
    }



    fun setText(text: String){
        title=text
    }


    fun viewRecord(type: ListItems) {
        //creating the instance of DatabaseHandler class
        //val databaseHandler = dbHandler
        //calling the viewPlace method of DatabaseHandler class to read the records

    }



}


