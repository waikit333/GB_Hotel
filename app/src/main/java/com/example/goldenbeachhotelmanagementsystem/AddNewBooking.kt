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
import com.example.goldenbeachhoteldataclasses.DataClassBooking
import com.example.goldenbeachhoteldataclasses.DataClassCustomer
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.util.*
import kotlin.math.max


class AddNewBooking : Fragment(R.layout.fragment_add_new_booking) {
    private var other = ""
    private var firstName = ""
    private var lastName = ""
    private var ic = ""
    private var phone = ""
    private var email = ""
    private var numOfGuest = 0
    private var fromDate= ""
    private var toDate= ""
    private var  type =""
    private var minRoom = 0
    private  var maxRoom = 0
    private val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var reference: DatabaseReference
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
        val btnAddBooking = view?.findViewById<Button>(R.id.btnAddBooking)
        val txtBtnFrom = view?.findViewById<Button>(R.id.txtBtnFrom)
        val txtBtnTo = view?.findViewById<Button>(R.id.txtBtnTo)
        var arrNumOfGuest = mutableListOf<Int>()
        val cal = Calendar.getInstance()
        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DAY_OF_MONTH)

        toolbar.setNavigationIcon(R.drawable.back_arrow)
        btnProfileImage.visibility = View.INVISIBLE
        activity?.let {
            btnFrom.setOnClickListener(){
                DatePickerDialog(requireContext(),DatePickerDialog.OnDateSetListener { it, mYear, mMonth, mDayOfMonth ->
                    fromDate =  mDayOfMonth.toString().padStart(2, '0') + (mMonth + 1).toString().padStart(2, '0') + mYear
                    txtBtnFrom.visibility = View.INVISIBLE
                },year, month, day).show()
            }
            btnTo.setOnClickListener() {
                DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { it, mYear, mMonth, mDayOfMonth ->
                    fromDate =  mDayOfMonth.toString().padStart(2, '0') + (mMonth + 1).toString().padStart(2, '0') + mYear
                    txtBtnTo.visibility = View.INVISIBLE
                }, year, month, day).show()
            }
            btnAddBooking.setOnClickListener(){
                if (fromDate.isEmpty()){
                    txtBtnFrom.visibility = View.VISIBLE
                }
                if (toDate.isEmpty()){
                    txtBtnTo.visibility = View.VISIBLE
                }

            }
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
                        minRoom = 1
                        maxRoom = 10
                    }
                    "Double Room" -> {
                        for (i in 0..1) {
                            arrNumOfGuest.add(i + 1)
                            minRoom = 10
                            maxRoom = 18
                        }
                    }
                    "Triple Room" -> {
                        for (i in 0..2  ) {
                            arrNumOfGuest.add(i + 1)
                            minRoom = 19
                            maxRoom = 24
                        }
                    }
                    "Quad Room" ->{
                        for (i in 0..3) {
                            arrNumOfGuest.add(i + 1)
                            minRoom = 25
                            maxRoom = 28
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
                        val txtOtherGuest = view?.findViewById<TextInputLayout>(R.id.txtOthers)
                        txtOtherGuest?.editText?.isEnabled = numOfGuest != 1
                    }
                }
            }
        }

        return view
    }

    private fun validateFirstName():Boolean{
        val txtFirstName = view?.findViewById<TextInputLayout>(R.id.txtFirstName)
        firstName = txtFirstName?.editText?.text.toString().trim()
        if (firstName.isEmpty()){
            txtFirstName?.error = R.string.required_error.toString()
            return false
        }
        else{
            txtFirstName?.error = ""
            return true
        }
    }

    private fun validateLastName():Boolean{
        val txtLastName = view?.findViewById<TextInputLayout>(R.id.txtLastName)
        lastName = txtLastName?.editText?.text.toString().trim()
        if (lastName.isEmpty()){
            txtLastName?.error = R.string.required_error.toString()
            return false
        }
        else{
            txtLastName?.error = ""
            return true
        }
    }

    private fun validateIC():Boolean{
        val txtIC = view?.findViewById<TextInputLayout>(R.id.txtIC)
        ic = txtIC?.editText?.text.toString().trim()
        if (ic.isEmpty()){
            txtIC?.error = R.string.required_error.toString()
            return false
        }
        else{
            txtIC?.error = ""
            return true
        }
    }

    private fun validatePhone():Boolean{
        val txtPhone = view?.findViewById<TextInputLayout>(R.id.txtPhone)
        phone = txtPhone?.editText?.text.toString().trim()
        if (phone.isEmpty()){
            txtPhone?.error = R.string.required_error.toString()
            return false
        }
        else{
            txtPhone?.error = ""
            return true
        }
    }

    private fun validateEmail():Boolean{
        val txtEmail = view?.findViewById<TextInputLayout>(R.id.txtEmail)
        email = txtEmail?.editText?.text.toString().trim()
        if (email.isEmpty()){
            txtEmail?.error = R.string.required_error.toString()
            return false
        }
        else{
            txtEmail?.error = ""
            return true
        }
    }

    private fun validateOtherGuest():Boolean{
        val txtOthers = view?.findViewById<TextInputLayout>(R.id.txtOthers)
        other = txtOthers?.editText?.text.toString().trim()
        val otherGuestList: List<String> = other.split(",").map { it -> it.trim() }
        otherGuestList.forEach { it ->
            println(it)
        }
        if (other.isEmpty()){
            txtOthers?.error = R.string.required_error.toString()
            return false
        }
        else if(otherGuestList.size != (numOfGuest - 1)){
            txtOthers?.error = R.string.other_guest_error.toString()
            return false
        }
        else{
            txtOthers?.error = ""
            return true
        }
    }

    private fun confirm(){
        if(validateEmail() && validateFirstName() && validateIC() && validateLastName() && validateOtherGuest() && validatePhone()){
            val customer = DataClassCustomer(email,firstName,ic,lastName,phone)


        }
    }

    private fun checkDuplicate(cust:DataClassCustomer){

    }

    private fun assignRoom(){
        val floor = (0..20).random()
        val room = (minRoom..maxRoom).random()

    }
}
