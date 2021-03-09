package com.example.goldenbeachhotelmanagementsystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.AdapterView



class AddNewBooking : Fragment(R.layout.fragment_add_new_booking) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_new_booking, container, false)
        val typeOfRoomSpinner = view?.findViewById<Spinner>(R.id.typeOfRoomSpinner)
        activity?.let {
            ArrayAdapter.createFromResource(it, R.array.type_of_room, android.R.layout.simple_spinner_item).also { typeOfRoomAdapter ->
            typeOfRoomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            typeOfRoomSpinner?.adapter = typeOfRoomAdapter
        }
        }
        typeOfRoomSpinner?.onItemSelectedListener =object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()

            }

        }
        return view
    }
}
