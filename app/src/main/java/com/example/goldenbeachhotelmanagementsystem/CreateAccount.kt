package com.example.goldenbeachhotelmanagementsystem

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.helperclasses.Helper
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class CreateAccount : AppCompatActivity() {
    private lateinit var positionSpinner: Spinner
    private lateinit var txtIC: TextInputLayout
    private lateinit var txtPhone: TextInputLayout
    private lateinit var txtEmail: TextInputLayout
    private lateinit var txtLastName: TextInputLayout
    private lateinit var txtFirstName: TextInputLayout
    private lateinit var txtPw: TextInputLayout
    private lateinit var txtConfirmPw: TextInputLayout
    private lateinit var txtAddress: TextInputLayout
    private lateinit var txtDob: TextView
    private var firstName = ""
    private var lastName = ""
    private var ic = ""
    private var phone = ""
    private var email = ""
    private var dob = ""
    private var add = ""
    private var staffPosition = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        //show action bar with back arrow
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.createAccDrawerLayout)
        setSupportActionBar(toolbar)
        val helper = Helper()
        helper.changeNavIconAndTitle(
            supportActionBar,
            false,
            toolbar,
            drawerLayout,
            this,
            getString(R.string.create_acc)
        )
        //Spinner
        positionSpinner = findViewById<Spinner>(R.id.typeOfRoomSpinner)
        //TextInputLayout
        txtFirstName = findViewById(R.id.createTxtFirstName)
        txtLastName = findViewById(R.id.createTxtLastName)
        txtIC = findViewById(R.id.createTxtIC)
        txtPhone = findViewById(R.id.createTxtPhone)
        txtEmail = findViewById(R.id.createTxtEmail)
        txtPw = findViewById(R.id.createTxtPw)
        txtConfirmPw = findViewById(R.id.createTxtConfirmPw)
        txtAddress = findViewById(R.id.createTxtAdd)
        //TextView
        txtDob = findViewById(R.id.createDOB)!!

        ArrayAdapter.createFromResource(
            this,
            R.array.type_of_position,
            android.R.layout.simple_spinner_item
        ).also { positionAdapter ->
            positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            positionSpinner?.adapter = positionAdapter
        }
        positionSpinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    staffPosition = parent?.getItemAtPosition(position).toString()
                }
            }

    }
    private fun isLetters(string: String): Boolean {
        return string.all { it.isLetter() }
    }

    private fun validateDob(): Boolean {
        if (dob.isEmpty()) {
            txtDob.text = getString(R.string.required_error)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                txtDob.setTextAppearance(R.style.errorText)
            }
            return false
        }
        return true
    }

    fun btnDOBOnClick(v: View){
        dob = ""
        val cal = Calendar.getInstance()
        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(
            this,
            { _, mYear, mMonth, mDayOfMonth ->
                dob = mDayOfMonth.toString().padStart(2, '0') + (mMonth + 1).toString()
                    .padStart(2, '0') + mYear
                val from = Calendar.getInstance()
                val to = Calendar.getInstance()
                to.set(mYear, mMonth, mDayOfMonth)
                val currentDate = Calendar.getInstance()
                if (to.before(currentDate)) {
                    txtDob.text = getString(R.string.less_than_today_error)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        txtDob.setTextAppearance(R.style.errorText)
                    }
                } else {
                    txtDob.text = dob
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        txtDob.setTextAppearance(R.style.palaBrownText)
                    }
                }

            },
            year,
            month,
            day
        ).show()
    }

    fun btnRegisterOnClick(v: View){

    }
}