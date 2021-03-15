package com.example.goldenbeachhotelmanagementsystem

import android.app.DatePickerDialog
import android.content.RestrictionEntry.TYPE_NULL
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.goldenbeachhoteldataclasses.DataClassBooking
import com.example.goldenbeachhoteldataclasses.DataClassCustomer
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*
import java.util.concurrent.TimeUnit


class AddNewBooking : Fragment(R.layout.fragment_add_new_booking) {
    private var other = ""
    val HOTEL_MEAL_PRICE = 90
    private var firstName = ""
    private var lastName = ""
    private var ic = ""
    private var phone = ""
    private var email = ""
    private var numOfGuest = 0
    private var fromDate= ""
    private var toDate= ""
    var custID = ""
    var total = 0.00
    private var  type =""
    private var minRoom = 0
    private  var maxRoom = 0
    private val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var reference: DatabaseReference
    private lateinit var txtBtnFrom:TextView
    private lateinit var txtBtnTo:TextView
    private lateinit var customer:DataClassCustomer

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_new_booking, container, false)
        //Spinner
        val typeOfRoomSpinner = view?.findViewById<Spinner>(R.id.typeOfRoomSpinner)
        val numOfGuestSpinner = view?.findViewById<Spinner>(R.id.numOfGuestSpinner)
        //Button
        val btnSearch = view?.findViewById<Button>(R.id.btnSearchCust)
        val btnFrom = view?.findViewById<Button>(R.id.btnFrom)
        val btnTo = view?.findViewById<Button>(R.id.btnTo)
        val btnAddBooking = view?.findViewById<Button>(R.id.btnAddBooking)
        //TextInputLayout
        val txtFirstName = view?.findViewById<TextInputLayout>(R.id.txtFirstName)
        val txtLastName = view?.findViewById<TextInputLayout>(R.id.txtLastName)
        val txtIC = view?.findViewById<TextInputLayout>(R.id.txtIC)
        val txtPhone = view?.findViewById<TextInputLayout>(R.id.txtPhone)
        val txtEmail = view?.findViewById<TextInputLayout>(R.id.txtEmail)
        val txtOthers = view?.findViewById<TextInputLayout>(R.id.txtOthers)
        //TextView
        txtBtnFrom = view?.findViewById(R.id.txtBtnFrom)!!
        txtBtnTo = view?.findViewById(R.id.txtBtnTo)!!
        //Variable
        var arrNumOfGuest = mutableListOf<Int>()
        val cal = Calendar.getInstance()
        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DAY_OF_MONTH)
        //Checkbox
        var cbxMeal = view?.findViewById<CheckBox>(R.id.cbxMeal)

        typeOfRoomSpinner?.onItemSelectedListener =object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                type = parent?.getItemAtPosition(position).toString()
                arrNumOfGuest.clear()
                readPrice(cbxMeal)
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
                        if(numOfGuest > 1){
                            txtOtherGuest?.editText?.isEnabled = true
                            txtOtherGuest?.editText?.isFocusableInTouchMode = true
                            txtOtherGuest?.editText?.inputType = InputType.TYPE_CLASS_TEXT
                        }
                        else{
                            txtOtherGuest?.editText?.isEnabled = false
                            txtOtherGuest?.editText?.isFocusableInTouchMode = false
                            txtOtherGuest?.editText?.inputType = TYPE_NULL
                        }


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
                confirm(txtEmail,txtFirstName,txtIC,txtLastName,txtOthers,txtPhone,cbxMeal)
            }

            btnSearch.setOnClickListener(){
                validateIC(txtIC)
                readCustID()
                if (!custID.isEmpty()){
                    reference.addListenerForSingleValueEvent(object: ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (cust in snapshot.children){
                                if(cust.key.equals(custID)) {
                                    txtEmail.editText?.setText(cust.child("email").getValue(String::class.java).toString())
                                    txtFirstName.editText?.setText(cust.child("firstName").getValue(String::class.java).toString())
                                    txtLastName.editText?.setText(cust.child("lastName").getValue(String::class.java).toString())
                                    txtPhone.editText?.setText(cust.child("phone").getValue(String::class.java).toString())
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
                }
                else{
                    Toast.makeText(activity,"No record found! Please fill in all the customer details",Toast.LENGTH_LONG).show()
                }
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

    private fun validateFirstName(txtFirstName:TextInputLayout):Boolean{
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

    private fun validateLastName(txtLastName:TextInputLayout):Boolean{
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

    private fun validateIC(txtIC:TextInputLayout):Boolean{
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

    private fun validatePhone(txtPhone:TextInputLayout):Boolean{
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

    private fun validateEmail(txtEmail:TextInputLayout):Boolean{
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

    private fun validateOtherGuest(txtOthers:TextInputLayout):Boolean{
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

    private fun confirm(txtEmail: TextInputLayout,txtFirstName:TextInputLayout,txtIC: TextInputLayout,txtLastName: TextInputLayout,txtOthers: TextInputLayout,txtPhone: TextInputLayout,cbxMeal:CheckBox){
        var validEmail = validateEmail(txtEmail)
        var validFirstName = validateFirstName(txtFirstName)
        var validIC = validateIC(txtIC)
        var validLastName = validateLastName(txtLastName)
        var validOtherGuest = validateOtherGuest(txtOthers)
        var validPhone = validatePhone(txtPhone)
        var validFrom = validateFromDate()
        var validTo = validateToDate()
        reference = database.getReference("Customers")
        if(validEmail && validFirstName && validIC && validLastName && validOtherGuest && validPhone && validFrom && validTo){
            customer = DataClassCustomer(email, firstName, ic, lastName, phone)
            if (custID.isEmpty()){
                var custRef = reference.push()
                var newCustID = custRef.key.toString()
                reference.child(newCustID).setValue(customer)

            }
            writeBooking(cbxMeal)
        }
    }

    private fun readPrice(cbxMeal: CheckBox){
        var roomRef = database.getReference("Rooms")
        roomRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                total = snapshot.child(type).child("price").getValue(Double::class.java)!!
                val txtTotal = view?.findViewById<TextView>(R.id.txtTotal)
                if (total != null) {
                    if (cbxMeal.isChecked) {
                        total += (HOTEL_MEAL_PRICE * numOfGuest)
                        txtTotal?.text = "RM " + String.format("%.2f", total)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun writeBooking(cbxMeal: CheckBox) {
        val sdf = SimpleDateFormat("ddMMyyyy")
        val currentDate = sdf.format(Calendar.getInstance().time)
        readPrice(cbxMeal)
        var booking = DataClassBooking(fromDate, cbxMeal.isChecked, numOfGuest, toDate, total, other, currentDate.toString())
        reference = database.getReference("Bookings")
        reference.child(fromDate).child(type.split(" ")[0]).child(custID).push().setValue(booking).addOnSuccessListener {
            Toast.makeText(activity, "New booking is added!", Toast.LENGTH_LONG).show()
        }
    }


    private fun readCustID(){
        reference = database.getReference("Customers")
        reference.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children){
                    if (child.child("ic").getValue(String::class.java).equals(ic)){
                        assignCustID(child.key.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("cancel", "Failed to load customer data", error.toException())
            }
        })
    }
    private fun assignCustID(id:String){
        custID = id
    }

    override fun onPause() {

        super.onPause()
    }

}
