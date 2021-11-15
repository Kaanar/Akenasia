package com.example.akenasia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.marker_dialog.*
import kotlinx.android.synthetic.main.marker_dialog.view.*


class MarkerDialog : DialogFragment () {
    private lateinit var title : String
    private lateinit var info : String

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, saveInstanceState : Bundle?) :
            View? {
        var rootView: View = inflater.inflate(R.layout.marker_dialog, container, false)
        rootView.MarkerTitle.text = title
        rootView.MarkerInfo.text = info
        rootView.MarkerBtn.setOnClickListener() {
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
