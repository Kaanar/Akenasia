package com.example.akenasia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.akenasia.databinding.ActivityOpenworldBinding
import com.example.akenasia.databinding.BagCardviewBinding
import com.example.akenasia.databinding.ChronometreBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

class Bag : AppCompatActivity() {
    private lateinit var binding: BagCardviewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BagCardviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}