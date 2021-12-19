import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.akenasia.R
import com.example.akenasia.database.DatabaseHandler
import com.example.akenasia.database.ItemHandler
import com.example.akenasia.database.PersonnageHandler
import com.example.akenasia.database.PersonnageTable
import com.example.akenasia.game.Game
import com.example.akenasia.openworld.Personnage
import kotlinx.coroutines.processNextEventInCurrentThread


class ItemTypeAdapter (private val context: Activity, private val id: Array<String>, private val name: Array<String>, private val desc: Array<String>, private val att: Array<String>
                   ,private val def: Array<String> ) : ArrayAdapter<String>(context, R.layout.custom_list, id) {

    lateinit var inflater: LayoutInflater

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.itemtype_listview, null, true)

        val idItem = rowView.findViewById<View>(R.id.ItemId) as TextView
        val nomItem = rowView.findViewById<View>(R.id.ItemName) as TextView
        val descrItem = rowView.findViewById<View>(R.id.ItemDesc) as TextView
        val attItem = rowView.findViewById<View>(R.id.ItemAtt) as TextView
        val defItem = rowView.findViewById<View>(R.id.ItemDef) as TextView

        val choisirBtn = rowView.findViewById<View>(R.id.choisir) as Button

        idItem.text = id[position]
        nomItem.text ="Nom: "+ name[position]
        descrItem.text ="Description: "+ desc[position]
        attItem.text="ATT: "+ att[position]
        defItem.text="DEF: "+def[position]

        choisirBtn.setOnClickListener{
            val personnageHandler= PersonnageHandler(context)
            val itemHandler= ItemHandler(context)
            personnageHandler.update(itemHandler.get(id[position].toInt()))

            val intent = Intent(context, Personnage::class.java)
            context.startActivity(intent)
        }
        return rowView
    }
}

