package com.example.goldenbeachhotelmanagementsystem

import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class CheckOuts : AppCompatActivity()  , DialogInterface.OnDismissListener{
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var curDate = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_outs)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Check Out"

        //format for textView display
        val currentViewDate = findViewById<View>(R.id.cur_view_check_outs_date) as TextView
        currentViewDate.text = SimpleDateFormat("dd MMMM yyyy").format(Date())

        //format for database query
        curDate = SimpleDateFormat("ddMMyyyy").format(Date())

        //onclick listener for date change
        val changeButton = findViewById<View>(R.id.change_button) as Button

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth)
            curDate = SimpleDateFormat("ddMMyyyy").format(calendar.time)
            currentViewDate.text = SimpleDateFormat("dd MMMM yyyy").format(calendar.time)
            loadTable()
        }, year, month, day)

        changeButton.setOnClickListener {
            dpd.show()
        }

        loadTable()
    }

    private fun loadTable() {
        val tableLayout = findViewById<TableLayout>(R.id.check_out_table)
        val database = FirebaseDatabase.getInstance()

        database.getReference("Bookings").get().addOnSuccessListener {
            tableLayout.removeAllViews()
            for (date in it.children) {
                for (room in date.children) {
                    for (customer in room.children) {
                        for (booking in customer.children) {
                            var fromDate = date.key.toString()
                            var custID = customer.key.toString()
                            var roomType = room.key.toString()
                            var bookingID = booking.key.toString()
                            var status = booking.child("status").value.toString()
                            var toDate = booking.child("to").value.toString()
                            var roomName = booking.child("room").value.toString()
                            var totalBill = booking.child("total").value.toString()
                            var custPhone: String
                            var custName: String
                            var periodOfStay: Int

                            if (toDate == curDate) {
                                val startDateString = booking.child("bookingDate").value.toString()
                                val endDateString = booking.child("to").value.toString()
                                val startDate = SimpleDateFormat("ddMMyyyy").parse(startDateString)
                                val endDate = SimpleDateFormat("ddMMyyyy").parse(endDateString)
                                val diff: Long = endDate.getTime() - startDate.getTime()
                                periodOfStay = java.util.concurrent.TimeUnit.DAYS.convert(
                                    diff,
                                    java.util.concurrent.TimeUnit.MILLISECONDS
                                ).toInt() + 1

                                database.getReference("Customers").child(custID).get()
                                    .addOnSuccessListener {
                                        custName =
                                            it.child("firstName").value.toString() + " " + it.child(
                                                "lastName"
                                            ).value.toString()
                                        custPhone = it.child("phone").value.toString()

                                        val tableRow = LayoutInflater.from(this).inflate(
                                            R.layout.check_ins_table_row,
                                            null
                                        ) as TableRow
                                        tableRow.findViewById<TextView>(R.id.custName).text =
                                            custName
                                        tableRow.findViewById<TextView>(R.id.roomType).text =
                                            roomType
                                        tableRow.findViewById<TextView>(R.id.roomName).text =
                                            roomName
                                        tableRow.findViewById<TextView>(R.id.roomStatus).text =
                                            status
                                        if (status == "Checked In") {
                                            tableRow.setOnClickListener {
                                                val dialog = CheckOutDialogFragment()
                                                val args = Bundle()
                                                args.putString("custID", custID)
                                                args.putString("roomName", roomName)
                                                args.putString("curDate", curDate)
                                                args.putString("custName", custName)
                                                args.putString("roomType", roomType)
                                                args.putString("custPhone", custPhone)
                                                args.putString("bookingID", bookingID)
                                                args.putString("total", totalBill)
                                                args.putString("fromDate", fromDate)
                                                args.putInt("periodOfStay", periodOfStay)
                                                dialog.arguments = args
                                                dialog.show(supportFragmentManager, "Name")
                                            }
                                        }
                                        tableLayout.addView(tableRow)
                                    }

                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDismiss(p0: DialogInterface?) {
        loadTable()
    }
}