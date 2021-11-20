package com.example.akenasia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.telephony.RadioAccessSpecifier
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.historique.*
import kotlinx.android.synthetic.main.reglages.*
import android.widget.Toast

import android.R




class Reglages: Fragment(com.example.akenasia.R.layout.reglages) {

    private lateinit var radioGroup: RadioGroup
    private lateinit var light: RadioButton
    private lateinit var dark: RadioButton
    private var thiscontext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            thiscontext = container.getContext()
        }
        return inflater.inflate(com.example.akenasia.R.layout.reglages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RetourBT.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            this.startActivity(intent)
        }

        ThemeRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            changeTheme(
                group,
                checkedId
            )
        })
    }

    private fun changeTheme(group: RadioGroup?, checkedId: Int) {
        val checkedRadioId = group!!.checkedRadioButtonId

        if (checkedRadioId == LightRadioButton.id) {
            Toast.makeText(context, "Light Theme activé", Toast.LENGTH_SHORT)
                .show()
        } else if (checkedRadioId == DarkRadioButton.id) {
            Toast.makeText(context, "Dark Theme activé", Toast.LENGTH_SHORT)
                .show()
        }
    }


}