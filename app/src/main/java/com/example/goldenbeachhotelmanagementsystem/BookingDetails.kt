package com.example.goldenbeachhotelmanagementsystem

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.helperclasses.Helper
import com.google.firebase.database.FirebaseDatabase

class BookingDetails : AppCompatActivity() {
    private lateinit var txtFirstName:TextView
    private lateinit var txtLastName :TextView
    private lateinit var txtIC :TextView
    private lateinit var txtPhone :TextView
    private lateinit var txtEmail :TextView
    private lateinit var txtType :TextView
    private lateinit var txtNum :TextView
    private lateinit var txtFrom :TextView
    private lateinit var txtTo :TextView
    private lateinit var txtOthers :TextView
    private lateinit var txtRoomID: TextView
    private lateinit var cbxMeal :CheckBox
    private lateinit var txtTotal :TextView
    private lateinit var txtStatus :TextView
    private lateinit var txtBookingDate :TextView
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)
        //show action bar with back arrow
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.bookingDetailsDrawerLayout)
        setSupportActionBar(toolbar)
        val helper = Helper()
        helper.changeNavIconAndTitle(
            supportActionBar,
            false,
            toolbar,
            drawerLayout,
            this,
            getString(R.string.booking_details)
        )
        txtFirstName = findViewById(R.id.details_txtFirstName)
        txtLastName = findViewById(R.id.details_txtLastName)
        txtIC = findViewById(R.id.details_txtIC)
        txtPhone = findViewById(R.id.details_txtPhone)
        txtEmail = findViewById(R.id.details_txtEmail)
        txtType = findViewById(R.id.details_txtTypeOfRoom)
        txtNum = findViewById(R.id.details_txtNumOfGuest)
        txtFrom = findViewById(R.id.details_txtfromDate)
        txtTo = findViewById(R.id.details_txtToDate)
        txtOthers = findViewById(R.id.details_txtOthers)
        cbxMeal = findViewById(R.id.details_cbxMeal)
        txtTotal = findViewById(R.id.details_txtTotal)
        txtStatus = findViewById(R.id.details_txtStatus)
        txtBookingDate = findViewById(R.id.details_bookingDate)
        txtRoomID = findViewById(R.id.details_roomID)
        database = FirebaseDatabase.getInstance()
        loadCustData()
        loadBookingData()
    }

    private fun loadBookingData(){
        val ref = database.getReference("Bookings")
        val intent = intent
        val from = intent.getStringExtra("from").toString()
        val custID = intent.getStringExtra("custID").toString()
        val bookingID = intent.getStringExtra("bookingID").toString()
        val type = intent.getStringExtra("type").toString()
        ref.child(from).child(type).child(custID).child(bookingID).get().addOnSuccessListener{
            txtType.text = type
            txtNum.text = it.child("numOfGuest").value.toString()
            txtFrom.text = from
            txtTo.text = it.child("to").value.toString()
            var other = it.child("otherGuest").value.toString()
            if (!other.isNullOrEmpty()){
                txtOthers.text = other
            }
            val total = it.child("total").value
            txtTotal.text = "RM " +  String.format("%.2f", total.toString().toFloat())
            txtStatus.text = it.child("status").value.toString()
            txtBookingDate.text = it.child("bookingDate").value.toString()
            cbxMeal.isChecked = it.child("hotelMeal").value.toString().toBoolean()
            txtRoomID.text = it.child("room").value.toString()
        }
    }

    private fun loadCustData(){
        val ref = database.getReference("Customers")
        val intent = intent
        val custID = intent.getStringExtra("custID").toString()
        ref.child(custID).get().addOnSuccessListener {
            txtFirstName.text = it.child("firstName").value.toString()
            txtLastName.text = it.child("lastName").value.toString()
            txtIC.text = it.child("ic").value.toString()
            txtPhone.text = it.child("phone").value.toString()
            txtEmail.text = it.child("email").value.toString()
        }
    }

    fun btnCancelOnClick(v: View){
        val status = txtStatus.text
        if (!status.equals("Checked In") && !status.equals("Checked Out")){
            val ref = database.getReference("Bookings")
            val intent = intent
            val from = intent.getStringExtra("from").toString()
            val custID = intent.getStringExtra("custID").toString()
            val bookingID = intent.getStringExtra("bookingID").toString()
            val type = intent.getStringExtra("type").toString()

            ref.child(from).child(type).child(custID).child(bookingID).child("status").setValue("Cancelled").addOnSuccessListener{
                txtStatus.text = "Cancelled"
                Snackbar.make(v, "The booking has been cancelled", Snackbar.LENGTH_LONG)
                    .setAction("UNDO"){
                        ref.child(from).child(type).child(custID).child(bookingID).child("status").setValue(status)
                        txtStatus.text = status
                    }.show()
            }
        }else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Invalid Booking Status")
            builder.setMessage("The checked in or out booking can't be cancelled")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("OK") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }
}