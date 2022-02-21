package com.example.akenasia.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.akenasia.openworld.OpenWorld
import com.example.akenasia.R
import com.example.akenasia.database.Position
import com.example.akenasia.databinding.HomepageBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment


class HomePage : Fragment(){
    private var _binding: HomepageBinding? = null
    private lateinit var pos: Position

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var thiscontext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pos = Position(this.requireActivity())
        pos.refreshLocation()
        if (container != null) {
            thiscontext = container.getContext()
        }
        _binding = HomepageBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.HomepageJouerBT.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.homepageOpenWorld.setOnClickListener{
            val intent = Intent(context, OpenWorld::class.java)
            activity?.startActivity(intent)
        }

        binding.settings.setOnClickListener{
            val intent = Intent(context, Reglages::class.java).apply {
            }
            activity?.startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}