package com.example.goldenbeachhotelmanagementsystem

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.util.*


class AddNewBooking : Fragment(R.layout.fragment_add_new_booking) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_new_booking, container, false)
        val typeOfRoomSpinner = view?.findViewById<Spinner>(R.id.typeOfRoomSpinner)
        val numOfGuestSpinner = view?.findViewById<Spinner>(R.id.numOfGuestSpinner)
        val toolbar = view?.findViewById<Toolbar>(R.id.toolbar)
        val btnProfileImage = view?.findViewById<ImageButton>(R.id.btnProfileImage)
        val btnFrom = view?.findViewById<Button>(R.id.btnFrom)
        val btnTo = view?.findViewById<Button>(R.id.btnTo)
        var type =""
        var arrNumOfGuest = mutableListOf<Int>()
        var numOfGuest = 0
        val cal = Calendar.getInstance()
        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DAY_OF_MONTH)
        var fromDate:String
        var toDate:String
        toolbar.setNavigationIcon(R.drawable.back_arrow)
        btnProfileImage.visibility = View.INVISIBLE
        activity?.let {
            btnFrom.setOnClickListener(){
                DatePickerDialog(requireContext(),DatePickerDialog.OnDateSetListener { it, mYear, mMonth, mDayOfMonth ->
                    fromDate =  mDayOfMonth.toString().padStart(2, '0') + (mMonth + 1).toString().padStart(2, '0') + mYear
                    println(fromDate)
                },year, month, day).show()
            }
            btnTo.setOnClickListener() {
                DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { it, mYear, mMonth, mDayOfMonth ->
                    toDate = mDayOfMonth.toString() + mMonth.toString() + mYear.toString()
                }, year, month, day).show()
            }
        }
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
                type = parent?.getItemAtPosition(position).toString()
                arrNumOfGuest.clear()
                when (type) {
                    "Single Room" -> {
                        arrNumOfGuest.add(1)
                    }
                    "Double Room" -> {
                        for (i in 0..1) {
                            arrNumOfGuest.add(i + 1)
                        }
                    }
                    "Triple Room" -> {
                        for (i in 0..2  ) {
                            arrNumOfGuest.add(i + 1)
                        }
                    }
                    "Quad Room" ->{
                        for (i in 0..3) {
                            arrNumOfGuest.add(i + 1)
                        }
                    }
                }
                activity?.let {
                    val numOfGuestAdapter = ArrayAdapter<Int>(it, android.R.layout.simple_spinner_item, arrNumOfGuest)
                    numOfGuestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    numOfGuestSpinner?.adapter = numOfGuestAdapter
                }
                numOfGuestSpinner?.onItemSelectedListener =object :AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        numOfGuest = parent?.getItemAtPosition(position).toString().toInt()
                    }
                }
            }
        }

        return view
    }
}
