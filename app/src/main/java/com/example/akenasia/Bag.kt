package com.example.akenasia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.akenasia.databinding.ActivityBagBinding
import com.example.akenasia.databinding.ChronometreBinding

class Bag : Fragment() {
    private var _binding: ActivityBagBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            val id= this.arguments?.getInt("id")
        }

        _binding = ActivityBagBinding.inflate(inflater, container, false)
        return _binding!!.root

    }
}