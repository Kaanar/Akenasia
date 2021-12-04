package com.example.akenasia.openworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import com.example.akenasia.R
import com.example.akenasia.database.DatabaseHandler
import kotlinx.android.synthetic.main.item_dialog.view.*

class ItemDialog : DialogFragment(), AdapterView.OnItemClickListener {
    private lateinit var dbHandler : DatabaseHandler
    private lateinit var title : String
    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, saveInstanceState : Bundle?) :
            View {
        val rootView= inflater.inflate(R.layout.item_dialog, container, false)
        dbHandler = DatabaseHandler(context!!)
        rootView.ItemInfos.text=title


        return rootView
    }

    fun setText(text: String){
        title=text
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }
}