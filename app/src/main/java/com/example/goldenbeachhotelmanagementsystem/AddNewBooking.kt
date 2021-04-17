package com.example.goldenbeachhotelmanagementsystem

import android.app.DatePickerDialog
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.goldenbeachhoteldataclasses.DataClassBooking
import com.example.goldenbeachhoteldataclasses.DataClassCustomer
import com.example.helperclasses.*
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


@Suppress("DEPRECATION")
class AddNewBooking : AppCompatActivity() {
    private var other = ""
    val HOTEL_MEAL_PRICE = 90
    private var firstName = ""
    private var lastName = ""
    private var ic = ""
    private var phone = ""
    private var email = ""
    private var numOfGuest = 0
    private var fromDate = ""
    private var toDate = ""
    var custID = ""
    var total = 0.00
    private var type = ""
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var reference: DatabaseReference
    private lateinit var txtBtnFrom: TextView
    private lateinit var txtBtnTo: TextView
    private lateinit var customer: DataClassCustomer
    private lateinit var txtIC: TextInputLayout
    private lateinit var txtPhone: TextInputLayout
    private lateinit var txtEmail: TextInputLayout
    private lateinit var txtLastName: TextInputLayout
    private lateinit var txtFirstName: TextInputLayout
    private lateinit var typeOfRoomSpinner: Spinner
    private lateinit var numOfGuestSpinner: Spinner
    private lateinit var txtOtherGuest: TextInputLayout
    private lateinit var cbxMeal: CheckBox

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_booking)
        //show action bar with back arrow
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.addNewBoookingDL)
        setSupportActionBar(toolbar)
        val helper = Helper()
        helper.changeNavIconAndTitle(
            supportActionBar,
            false,
            toolbar,
            drawerLayout,
            this,
            getString(R.string.addNewBooking)
        )
        //Spinner
        typeOfRoomSpinner = findViewById<Spinner>(R.id.typeOfRoomSpinner)
        numOfGuestSpinner = findViewById<Spinner>(R.id.numOfGuestSpinner)
        //TextInputLayout
        txtFirstName = findViewById(R.id.txtFirstName)
        txtLastName = findViewById(R.id.txtLastName)
        txtIC = findViewById(R.id.txtIC)
        txtPhone = findViewById(R.id.txtPhone)
        txtEmail = findViewById(R.id.txtEmail)
        txtOtherGuest = findViewById(R.id.txtOthers)
        //TextView
        txtBtnFrom = findViewById(R.id.txtBtnFrom)!!
        txtBtnTo = findViewById(R.id.txtBtnTo)!!
        //Variable
        var arrNumOfGuest = mutableListOf<Int>()

        //Checkbox
        cbxMeal = findViewById(R.id.cbxMeal)

        txtFirstName.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateFirstName()
            }
        })

        txtLastName.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateLastName()
            }
        })
        txtIC.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateIC()
            }
        })
        txtPhone.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePhone()
            }
        })
        txtEmail.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail()
            }
        })
        txtOtherGuest.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateOtherGuest()
            }
        })
        typeOfRoomSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                type = parent?.getItemAtPosition(position).toString()
                cbxMeal.isEnabled = true
                arrNumOfGuest.clear()
                readPrice()
                when (type) {
                    "Single Room" -> {
                        arrNumOfGuest.add(1)
                    }
                    "Double Room" -> {
                        arrNumOfGuest.add(1)
                        arrNumOfGuest.add(2)
                    }
                    "Triple Room" -> {
                        arrNumOfGuest.add(1)
                        arrNumOfGuest.add(2)
                        arrNumOfGuest.add(3)
                    }
                    "Quad Room" -> {
                        arrNumOfGuest.add(1)
                        arrNumOfGuest.add(2)
                        arrNumOfGuest.add(3)
                        arrNumOfGuest.add(4)
                    }
                }
                val numOfGuestAdapter = ArrayAdapter<Int>(
                    this@AddNewBooking,
                    android.R.layout.simple_spinner_item,
                    arrNumOfGuest
                )
                numOfGuestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                numOfGuestSpinner?.adapter = numOfGuestAdapter

                numOfGuestSpinner?.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }

                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            numOfGuest = parent?.getItemAtPosition(position).toString().toInt()
                            txtOtherGuest?.editText?.isEnabled = numOfGuest > 1
                            readPrice()
                        }
                    }
            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.type_of_room,
            android.R.layout.simple_spinner_item
        ).also { typeOfRoomAdapter ->
            typeOfRoomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            typeOfRoomSpinner?.adapter = typeOfRoomAdapter
        }

        cbxMeal.setOnCheckedChangeListener { _, _ ->
            readPrice()
        }

    }

    fun btnAddOnClick(v: View) {
        confirm()
    }


    fun btnToOnClick(v: View) {
        toDate = ""
        val cal = Calendar.getInstance()
        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(
            this,
            { _, mYear, mMonth, mDayOfMonth ->
                toDate = mDayOfMonth.toString().padStart(2, '0') + (mMonth + 1).toString()
                    .padStart(2, '0') + mYear
                val from = Calendar.getInstance()
                val to = Calendar.getInstance()
                to.set(mYear, mMonth, mDayOfMonth)
                val currentDate = Calendar.getInstance()
                if (to.before(currentDate)) {
                    txtBtnTo.text = getString(R.string.less_than_today_error)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        txtBtnTo.setTextAppearance(R.style.errorText)
                    }
                } else {
                    txtBtnTo.text = toDate
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        txtBtnTo.setTextAppearance(R.style.palaBrownText)
                    }
                    readPrice()
                }

            },
            year,
            month,
            day
        ).show()

    }

    fun btnFromOnClick(v: View) {
        fromDate = ""
        val cal = Calendar.getInstance()
        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(
            this,
            { _, mYear, mMonth, mDayOfMonth ->
                fromDate = mDayOfMonth.toString().padStart(2, '0') + (mMonth + 1).toString()
                    .padStart(2, '0') + mYear
                val from = Calendar.getInstance()
                from.set(mYear, mMonth, mDayOfMonth)
                val currentDate = Calendar.getInstance()
                if (from.before(currentDate)) {
                    txtBtnFrom.text = getString(R.string.less_than_today_error)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        txtBtnFrom.setTextAppearance(R.style.errorText)
                    }
                } else {
                    txtBtnFrom.text = fromDate
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        txtBtnFrom.setTextAppearance(R.style.palaBrownText)
                    }
                    readPrice()
                }
            },
            year,
            month,
            day
        ).show()
    }

    fun btnSearchOnClick(v: View) {
        validateIC()
        assignCustDetails()
    }

    private fun assignCustDetails() {
        val context = this
        reference = database.getReference("Customers")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    if (child.child("ic").getValue(String::class.java).equals(ic)) {
                        assignCustID(child.key.toString())
                    }
                }
                reference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var isExist = false
                        for (cust in snapshot.children) {
                            if (cust.key.equals(custID)) {
                                isExist = true
                                txtEmail.editText?.setText(
                                    cust.child("email").getValue(String::class.java).toString()
                                )
                                txtFirstName.editText?.setText(
                                    cust.child("firstName").getValue(String::class.java)
                                        .toString()
                                )
                                txtLastName.editText?.setText(
                                    cust.child("lastName").getValue(String::class.java)
                                        .toString()
                                )
                                txtPhone.editText?.setText(
                                    cust.child("phone").getValue(String::class.java).toString()
                                )
                            }
                        }
                        if (!isExist){
                            Toast.makeText(
                                context, "No record found! Please fill in all the customer details",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })


            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("cancel", "Failed to load customer data", error.toException())
            }
        })

    }

    private fun isLetters(string: String): Boolean {
        return string.matches("^[ A-Za-z]+\$".toRegex())
    }

    private fun validateFromDate(): Boolean {
        if (fromDate.isEmpty()) {
            txtBtnFrom.text = getString(R.string.required_error)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                txtBtnFrom.setTextAppearance(R.style.errorText)
            }
            return false
        }
        return true
    }

    private fun validateToDate(): Boolean {
        var to: Date? = null
        var from: Date? = null
        if (!toDate.isEmpty() && !fromDate.isEmpty()) {
            to = SimpleDateFormat("ddMMyyyy").parse(toDate)
            from = SimpleDateFormat("ddMMyyyy").parse(fromDate)
        }
        if (toDate.isEmpty()) {
            txtBtnTo.text = getString(R.string.required_error)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                txtBtnTo.setTextAppearance(R.style.errorText)
            }
            return false
        } else if (to?.before(from) == true && !toDate.isEmpty() && !fromDate.isEmpty()) {

            txtBtnTo.text = getString(R.string.less_than_from_error)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                txtBtnTo.setTextAppearance(R.style.errorText)
            }
            return false
        }
        return true
    }

    private fun validateFirstName(): Boolean {
        firstName = txtFirstName?.editText?.text.toString().trim()
        if (firstName.isEmpty()) {
            txtFirstName?.error = getString(R.string.required_error)
            return false
        } else if (!isLetters(firstName)) {
            txtFirstName?.error = getString(R.string.alphabet_error)
            return false
        } else {
            txtFirstName?.error = ""
            return true
        }
    }

    private fun validateLastName(): Boolean {
        lastName = txtLastName?.editText?.text.toString().trim()
        if (lastName.isEmpty()) {
            txtLastName?.error = getString(R.string.required_error)
            return false
        } else if (!isLetters(lastName)) {
            txtLastName?.error = getString(R.string.alphabet_error)
            return false
        } else {
            txtLastName?.error = ""
            return true
        }
    }

    private fun validateIC(): Boolean {
        ic = txtIC?.editText?.text.toString().trim()
        if (ic.isEmpty()) {
            txtIC?.error = getString(R.string.required_error)
            return false
        } else if (!ic.matches("(([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01]))-([0-9]{2})-([0-9]{4})".toRegex())) {
            txtIC?.error = getString(R.string.ic_error)
            return false
        } else {
            txtIC?.error = ""
            return true
        }
    }

    private fun validatePhone(): Boolean {
        phone = txtPhone?.editText?.text.toString().trim()
        if (phone.isEmpty()) {
            txtPhone?.error = getString(R.string.required_error)
            return false
        } else if (!phone.matches("^01[0|1|2|3|4|6|7|8|9]*[0-9]{7,8}\$".toRegex())) {
            txtPhone?.error = getString(R.string.phone_error)
            return false
        } else {
            txtPhone?.error = ""
            return true
        }
    }

    private fun validateEmail(): Boolean {
        email = txtEmail?.editText?.text.toString().trim()
        if (email.isEmpty()) {
            txtEmail?.error = getString(R.string.required_error)
            return false
        } else {
            txtEmail?.error = ""
            return true
        }
    }

    private fun validateOtherGuest(): Boolean {
        other = txtOtherGuest?.editText?.text.toString().trim()
        val otherGuestList: List<String> = other.split(",").map { it -> it.trim() }
        otherGuestList.forEach { it ->
            println(it)
        }
        if (other.isEmpty() && numOfGuest > 1) {
            txtOtherGuest?.error = getString(R.string.required_error)
            return false
        } else if (otherGuestList.size != (numOfGuest - 1) && numOfGuest > 1) {
            txtOtherGuest?.error = getString(R.string.other_guest_error)
            return false
        } else {
            txtOtherGuest?.error = ""
            return true
        }
    }

    private fun confirm() {
        if (txtEmail.editText?.text.isNullOrEmpty() || txtFirstName.editText?.text.isNullOrEmpty() || txtLastName.editText?.text.isNullOrEmpty()
            || txtPhone.editText?.text.isNullOrEmpty()
        ) {
            assignCustDetails()
        }
        var available = true
        var validEmail = validateEmail()
        var validFirstName = validateFirstName()
        var validIC = validateIC()
        var validLastName = validateLastName()
        var validOtherGuest = validateOtherGuest()
        var validPhone = validatePhone()
        var validFrom = validateFromDate()
        var validTo = validateToDate()

        val ref = database.getReference("Bookings")
        ref.child(fromDate).child(type).get().addOnSuccessListener {
            var counter = 0
            for (cust in it.children) {
                for (booking in cust.children) {
                    counter++
                }
            }
            when (type) {
                "Single Room" -> {
                    if (counter >= 10)
                        available = false
                }
                "Double Room" -> {
                    if (counter >= 8)
                        available = false
                }
                "Triple Room" -> {
                    if (counter >= 6)
                        available = false
                }
                "Quad Room" -> {
                    if (counter >= 4)
                        available = false
                }
            }
            if (available) {
                reference = database.getReference("Customers")
                if (validEmail && validFirstName && validIC && validLastName && validOtherGuest && validPhone && validFrom && validTo) {
                    customer = DataClassCustomer(email, firstName, ic, lastName, phone)
                    if (custID.isEmpty()) {
                        var custRef = reference.push()
                        var newCustID = custRef.key.toString()
                        reference.child(newCustID).setValue(customer)
                        writeBooking(newCustID)
                    } else {
                        writeBooking(custID)
                    }
                    val pLinear = findViewById<LinearLayout>(R.id.linearProgress)
                    val async = MyAsyncTask(pLinear)
                    async.execute()
                    finish()
                }
            } else {
                availabilityDialog()
            }
        }


    }

    private fun readPrice() {
        var roomRef = database.getReference("Rooms")
        roomRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (fromDate.isNullOrEmpty() || toDate.isNullOrEmpty()) {
                    total = snapshot.child(type).child("price").getValue(Double::class.java)!!
                    val txtTotal = findViewById<TextView>(R.id.txtTotal)
                    if (total != null) {
                        if (cbxMeal.isChecked) {
                            total += (HOTEL_MEAL_PRICE * numOfGuest)
                        } else {
                            total =
                                snapshot.child(type).child("price").getValue(Double::class.java)!!
                        }
                        txtTotal?.text = "RM " + String.format("%.2f", total)
                    }
                } else {
                    total = snapshot.child(type).child("price")
                        .getValue(Double::class.java)!! * (toDate.substring(0, 2)
                        .toInt() - fromDate.substring(0, 2).toInt())
                    val txtTotal = findViewById<TextView>(R.id.txtTotal)
                    if (total != null) {
                        if (cbxMeal.isChecked) {
                            total += (HOTEL_MEAL_PRICE * numOfGuest)
                        } else {
                            val sdf = SimpleDateFormat("ddMMyyyy")
                            val to = sdf.parse(toDate)
                            val from = sdf.parse(fromDate)
                            val diff: Long = to.getTime() - from.getTime()
                            val numOfDay = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
                            total = snapshot.child(type).child("price")
                                .getValue(Double::class.java)!! * numOfDay
                        }
                        txtTotal?.text = "RM " + String.format("%.2f", total)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun writeBooking(newCustID: String) {
        val sdf = SimpleDateFormat("ddMMyyyy")
        val currentDate = sdf.format(Calendar.getInstance().time)
        readPrice()

        var booking = DataClassBooking(
            cbxMeal.isChecked,
            numOfGuest,
            toDate,
            total,
            other,
            currentDate.toString()
        )
        reference = database.getReference("Bookings")
        reference.child(fromDate).child(type).child(newCustID).push()
            .setValue(booking)
            .addOnSuccessListener {
                Toast.makeText(this, "New booking is added!", Toast.LENGTH_LONG).show()
            }
    }


    private fun assignCustID(id: String) {
        custID = id
    }


    private fun availabilityDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selected room type is fully booked!")
        builder.setMessage("$type on $fromDate is fully booked!")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Change") { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("Dismiss") { dialogInterface, which ->
            finish()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    inner class MyAsyncTask(pLinear: LinearLayout) : AsyncTask<Void, Void, String>() {
        private var linear: LinearLayout = pLinear
        override fun doInBackground(vararg params: Void?): String? {
            val senderEmail = "bait2073mad@gmail.com"
            val password = "bait2073"
            val sender = GmailSender(senderEmail, password)
            val subject = "Your booking on $fromDate is confirmed!"
            val message = "Dear $lastName, \n" +
                    "Thank you for booking at " + getString(R.string.hotel_name) +
                    "\nWe have received your booking on $fromDate to $toDate."
            try {
                sender.sendMail(subject, message, senderEmail, email)
            } catch (ex: Exception) {
                Log.d("Email Sending Exception", ex.toString())
            }
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
            linear.visibility = View.VISIBLE

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            linear.visibility = View.GONE
        }
    }
}