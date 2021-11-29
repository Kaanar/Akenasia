import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.akenasia.R


class ItemAdapter (private val context: Activity, private val id: Array<String>, private val name: Array<String>, private val desc: Array<String> ) : ArrayAdapter<String>(context, R.layout.custom_list, id) {

    lateinit var inflater: LayoutInflater

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.items_listview, null, true)

        val idItem = rowView.findViewById<View>(R.id.ItemId) as TextView
        val nomItem = rowView.findViewById<View>(R.id.ItemName) as TextView
        val descrItem = rowView.findViewById<View>(R.id.ItemDescription) as TextView

        idItem.text = id[position]
        nomItem.text = name[position]
        descrItem.text = desc[position]

        return rowView
    }
}

