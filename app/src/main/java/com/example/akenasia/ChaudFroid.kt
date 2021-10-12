package com.example.akenasia


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.akenasia.databinding.ChaudFroidBinding
import kotlinx.android.synthetic.main.chaud_froid.*
import com.example.akenasia.Game.*

class ChaudFroid: Fragment() {
    private var _binding: ChaudFroidBinding? = null
    private lateinit var pos: Position
    private var essais =10
    private var firstDistance: Double = 0.0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var thiscontext: Context? = null
    private lateinit var dbHandler : DatabaseHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pos = Position(this.requireActivity())
        if (container != null) {
            thiscontext = container.getContext()
        }
        _binding = ChaudFroidBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHandler = DatabaseHandler(thiscontext!!)

       /* binding.CfessaisTV.text="Il vous reste"+essais+" essais"

        firstDistance=pos.calcul_distance(pos.getLatitude(),
            pos.getLongitude(),
            Cfgoal_Y.text.toString().toDouble(),
            Cfgoal_X.text.toString().toDouble())

            CfRefreshBT.setOnClickListener {
            if(essais==0){
                Toast.makeText(context, "Vous avez perdu!",Toast.LENGTH_LONG).show()
                val intent = Intent(context, MainActivity::class.java)
                activity?.startActivity(intent)
            }
            else{
                pos.refreshLocation()
                binding.CfcurrentX.text=pos.getLatitude().toString()
                binding.CfcurrentY.text = pos.getLongitude().toString()
                val distance : Double =pos.calcul_distance(pos.getLatitude(),
                    pos.getLongitude(),
                    Cfgoal_X.text.toString().toDouble(),
                    Cfgoal_Y.text.toString().toDouble())
                if(distance<1500){
                    Toast.makeText(context, "Vous avez gagné!",Toast.LENGTH_LONG).show()
                    val intent = Intent(context, MainActivity::class.java)
                    activity?.startActivity(intent)
                }
                else{
                    if(distance<firstDistance){
                        Toast.makeText(context, "Vous chauffez!",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(context, "Vous refroidissez!",Toast.LENGTH_LONG).show()
                    }
                }
                essais--
                binding.CfessaisTV.text="Il vous reste"+essais+" essais"
            }
        }
    */}
}