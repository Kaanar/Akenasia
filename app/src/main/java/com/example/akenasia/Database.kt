package com.example.akenasia

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.akenasia.databinding.DatabaseBinding
import kotlinx.android.synthetic.main.database.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class Database : Fragment() {

    private var _binding: DatabaseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var thiscontext: Context? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            thiscontext = container.getContext()
        };
        _binding = DatabaseBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.bSave.setOnClickListener {
            saveRecord()
        }
        binding.bView.setOnClickListener {
            viewRecord()
        }
        binding.bDelete.setOnClickListener {
            deleteRecord()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveRecord() {
        val id = u_id.text.toString()
        val name = u_name.text.toString()

        val databaseHandler: DatabaseHandler = DatabaseHandler(thiscontext!!)
        if (id.trim() != "" && name.trim() != "") {
            val status = databaseHandler.addEmployee(User(Integer.parseInt(id), name))
            if (status > -1) {
                Toast.makeText(activity, "record save", Toast.LENGTH_LONG).show()
                u_id.text.clear()
                u_name.text.clear()

            }
        } else {
            Toast.makeText(
                activity,
                "id or name or email cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun viewRecord(){
        //creating the instance of DatabaseHandler class
        val databaseHandler: DatabaseHandler= DatabaseHandler(thiscontext!!)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val emp: List<User> = databaseHandler.viewEmployee()
        val empArrayId = Array<String>(emp.size){"0"}
        val empArrayName = Array<String>(emp.size){"null"}

        var index = 0
        for(e in emp){
            empArrayId[index] = e.userId.toString()
            empArrayName[index] = e.userName

            index++
        }
        //creating custom ArrayAdapter
        val myListAdapter = MyListAdapter(this.requireActivity(),empArrayId,empArrayName)
        listView.adapter = myListAdapter
    }
    private fun deleteRecord(){
        //creating AlertDialog for taking user id
        val dialogBuilder = AlertDialog.Builder(thiscontext!!)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val dltId = dialogView.findViewById(R.id.deleteId) as EditText
        dialogBuilder.setTitle("Delete Record")
        dialogBuilder.setMessage("Enter id below")
        dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->

            val deleteId = dltId.text.toString()
            //creating the instance of DatabaseHandler class
            val databaseHandler: DatabaseHandler= DatabaseHandler(thiscontext!!)
            if(deleteId.trim()!=""){
                //calling the deleteEmployee method of DatabaseHandler class to delete record
                val status = databaseHandler.deleteEmployee(User(Integer.parseInt(deleteId),""))
                if(status > -1){
                    Toast.makeText(activity,"record deleted", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(activity,"id or name or email cannot be blank", Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }
}