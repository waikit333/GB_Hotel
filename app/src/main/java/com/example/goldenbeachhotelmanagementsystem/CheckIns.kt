package com.example.goldenbeachhotelmanagementsystem

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class CheckIns : AppCompatActivity() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var curDate = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check__ins)

        //format for textView display
        val currentViewDate = findViewById<View>(R.id.cur_view_check_ins_date) as TextView
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
            calendar.set(year,monthOfYear,dayOfMonth)
            curDate = SimpleDateFormat("ddMMyyyy").format(calendar.time)
            currentViewDate.text = SimpleDateFormat("dd MMMM yyyy").format(calendar.time)
            loadTable()
        }, year, month, day)


        changeButton.setOnClickListener{
            dpd.show()
        }

        loadTable()
    }

    private fun loadTable(){
        val tableLayout = findViewById<TableLayout>(R.id.check_in_table)

        tableLayout.removeAllViews()
        database.getReference("Bookings").get().addOnSuccessListener { bookings ->
            var custID: String
            var custName: String
            var roomType: String
            var status: String

            for (room in bookings.child(curDate).children) {
                for (customer in room.children) {
                    for (booking in customer.children) {
                        custID = customer.key.toString()
                        roomType = room.key.toString()
                        status = booking.child("status").value.toString()

                        database.getReference("Customers").child(custID).get().addOnSuccessListener {
                            custName = it.child("firstName").value.toString() + " " + it.child("lastName").value.toString()

                            val tableRow = LayoutInflater.from(this).inflate(
                                R.layout.check_ins_table_row,
                                null
                            ) as TableRow
                            tableRow.findViewById<TextView>(R.id.custName).text = custName
                            tableRow.findViewById<TextView>(R.id.roomName).text = roomType
                            tableRow.findViewById<TextView>(R.id.roomStatus).text = status
                            tableRow.setOnClickListener{
                                val dialog = CheckInDialogFragment()
                                val args = Bundle()
//                              args.putString("name", item.name)
//                              dialog.arguments = args
                                dialog.show(supportFragmentManager, "Name")
                            }
                            tableLayout.addView(tableRow)
                        }
                    }
                }
            }
        }
    }
}