package com.example.goldenbeachhotelmanagementsystem

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.goldenbeachhoteldataclasses.DataClassCustomer
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*
import java.util.concurrent.TimeUnit


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
    private lateinit var txtBtnFrom:TextView
    private lateinit var txtBtnTo:TextView

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_new_booking, container, false)
        val typeOfRoomSpinner = view?.findViewById<Spinner>(R.id.typeOfRoomSpinner)
        val numOfGuestSpinner = view?.findViewById<Spinner>(R.id.numOfGuestSpinner)

        val btnFrom = view?.findViewById<Button>(R.id.btnFrom)
        val btnTo = view?.findViewById<Button>(R.id.btnTo)
        val btnAddBooking = view?.findViewById<Button>(R.id.btnAddBooking)
        txtBtnFrom = view?.findViewById(R.id.txtBtnFrom)!!
        txtBtnTo = view?.findViewById(R.id.txtBtnTo)!!
        var arrNumOfGuest = mutableListOf<Int>()
        val cal = Calendar.getInstance()
        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DAY_OF_MONTH)

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
                        for (i in 0..2) {
                            arrNumOfGuest.add(i + 1)
                            minRoom = 19
                            maxRoom = 24
                        }
                    }
                    "Quad Room" -> {
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
                        txtOtherGuest?.editText?.isEnabled = numOfGuest > 1
                    }
                }
            }
        }
        activity?.let {
            btnFrom.setOnClickListener(){
                DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { it, mYear, mMonth, mDayOfMonth ->
                    fromDate = mDayOfMonth.toString().padStart(2, '0') + (mMonth + 1).toString().padStart(2, '0') + mYear
                    val from = Calendar.getInstance()
                    from.set(mYear,mMonth,mDayOfMonth)
                    val currentDate = Calendar.getInstance()
                    if (from.before(currentDate)) {
                        txtBtnFrom.text = getString(R.string.less_than_today_error)
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            txtBtnFrom.setTextAppearance(R.style.errorText)
                        }
                    }
                    else{
                        txtBtnFrom.text = fromDate
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            txtBtnFrom.setTextAppearance(R.style.palaBrownText)
                        }
                    }
                }, year, month, day).show()
            }
            btnTo.setOnClickListener() {
                DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { it, mYear, mMonth, mDayOfMonth ->
                    toDate = mDayOfMonth.toString().padStart(2, '0') + (mMonth + 1).toString().padStart(2, '0') + mYear
                    val from = Calendar.getInstance()
                    val to = Calendar.getInstance()
                    to.set(mYear,mMonth,mDayOfMonth)
                    val currentDate = Calendar.getInstance()
                    if (to.before(currentDate)) {
                        txtBtnTo.text = getString(R.string.less_than_today_error)
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            txtBtnTo.setTextAppearance(R.style.errorText)
                        }
                    }else{
                        txtBtnTo.text = toDate
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            txtBtnTo.setTextAppearance(R.style.palaBrownText)
                        }
                    }

                }, year, month, day).show()
            }
            btnAddBooking.setOnClickListener(){
                confirm()
            }
            ArrayAdapter.createFromResource(it, R.array.type_of_room, android.R.layout.simple_spinner_item).also { typeOfRoomAdapter ->
                typeOfRoomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                typeOfRoomSpinner?.adapter = typeOfRoomAdapter
            }
        }
        return view
    }

    private fun isLetters(string: String): Boolean {
        return string.all { it.isLetter() }
    }

    private fun validateFromDate():Boolean{
        if (fromDate.isEmpty()){
            txtBtnFrom.text = getString(R.string.required_error)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                txtBtnFrom.setTextAppearance(R.style.errorText)
            }
            return false
        }
        return true
    }
    private fun validateToDate():Boolean{
        val to = SimpleDateFormat("ddMMyyyy").parse(toDate)
        val from = SimpleDateFormat("ddMMyyyy").parse(fromDate)
        if (toDate.isEmpty()){
            txtBtnTo.text = getString(R.string.required_error)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                txtBtnTo.setTextAppearance(R.style.errorText)
            }
            return false
        }
        else if(to.before(from)){
            txtBtnTo.text = getString(R.string.less_than_from_error)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                txtBtnTo.setTextAppearance(R.style.errorText)
            }
            return false
        }
        return true
    }
    private fun validateFirstName():Boolean{
        val txtFirstName = view?.findViewById<TextInputLayout>(R.id.txtFirstName)
        firstName = txtFirstName?.editText?.text.toString().trim()
        if (firstName.isEmpty()){
            txtFirstName?.error = getString(R.string.required_error)
            return false
        }
        else if(!isLetters(firstName)){
            txtFirstName?.error = getString(R.string.alphabet_error)
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
            txtLastName?.error = getString(R.string.required_error)
            return false
        }
        else if(!isLetters(lastName)){
            txtLastName?.error = getString(R.string.alphabet_error)
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
            txtIC?.error = getString(R.string.required_error)
            return false
        }
        else if(!ic.matches("(([1-9]{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01]))-([0-9]{2})-([0-9]{4})".toRegex())){
            txtIC?.error = getString(R.string.ic_error)
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
            txtPhone?.error = getString(R.string.required_error)
            return false
        }
        else if(!phone.matches("^01[0|1|2|3|4|6|7|8|9]*[0-9]{7,8}\$".toRegex())){
            txtPhone?.error = getString(R.string.phone_error)
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
            txtEmail?.error = getString(R.string.required_error)
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
        if (other.isEmpty() && numOfGuest > 1){
            txtOthers?.error = getString(R.string.required_error)
            return false
        }
        else if(otherGuestList.size != (numOfGuest - 1) && numOfGuest > 1){
            txtOthers?.error = getString(R.string.other_guest_error)
            return false
        }
        else{
            txtOthers?.error = ""
            return true
        }
    }

    private fun confirm(){
        var validEmail = validateEmail()
        var validFirstName = validateFirstName()
        var validIC = validateIC()
        var validLastName = validateLastName()
        var validOtherGuest = validateOtherGuest()
        var validPhone = validatePhone()
        var validFrom = validateFromDate()
        var validTo = validateToDate()
        if(validEmail && validFirstName && validIC && validLastName && validOtherGuest && validPhone && validFrom && validTo){
            val customer = DataClassCustomer(email, firstName, ic, lastName, phone)

        }
    }

    private fun checkDuplicate(customer: DataClassCustomer):Boolean{
        return false
    }

}
