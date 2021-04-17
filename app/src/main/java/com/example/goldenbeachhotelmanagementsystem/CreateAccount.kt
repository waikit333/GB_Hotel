package com.example.goldenbeachhotelmanagementsystem

import android.app.DatePickerDialog
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.goldenbeachhoteldataclasses.DataClassUser
import com.example.helperclasses.Helper
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
    private lateinit var radioGender: RadioGroup
    private var firstName = ""
    private var lastName = ""
    private var ic = ""
    private var phone = ""
    private var email = ""
    private var dob = ""
    private var add = ""
    private var confirm = ""
    private var password = ""
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
        positionSpinner = findViewById<Spinner>(R.id.position)
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
        //Radio Group
        radioGender = findViewById(R.id.radioGender)
        ArrayAdapter.createFromResource(
            this,
            R.array.type_of_position,
            android.R.layout.simple_spinner_item
        ).also { positionAdapter ->
            positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            positionSpinner?.adapter = positionAdapter
        }

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
        txtPw.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword()
            }
        })
        txtConfirmPw.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword()
            }
        })
        txtAddress.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateAddress()
            }
        })
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

    fun btnDOBOnClick(v: View) {
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
                val selectedDob = Calendar.getInstance()
                selectedDob.set(mYear, mMonth , mDayOfMonth)
                val currentDate = Calendar.getInstance()
                if (selectedDob.after(currentDate)) {
                    txtDob.text = getString(R.string.dobError)
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

    private fun validatePassword(): Boolean {
        confirm = txtConfirmPw.editText?.text.toString().trim()
        password = txtPw.editText?.text.toString().trim()
        if (confirm.isEmpty() || password.isEmpty()) {
            if (confirm.isEmpty()) {
                txtConfirmPw?.error = getString(R.string.required_error)
            }
            if (password.isEmpty()) {
                txtPw?.error = getString(R.string.required_error)
            }
            return false
        }

        if (confirm.equals(password)) {
            if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{4,}$".toRegex())) {
                txtPw?.error = getString(R.string.password_format_error)
                txtConfirmPw?.error = getString(R.string.password_format_error)
                return false
            } else {
                txtPw?.error = ""
                txtConfirmPw?.error = ""
                return true
            }
        } else
            txtPw?.error = getString(R.string.password_not_match)
        txtConfirmPw?.error = getString(R.string.password_not_match)
        return false

    }

    private fun validateAddress(): Boolean {
        add = txtAddress.editText?.text.toString().trim()
        return if (add.isEmpty()) {
            txtAddress?.error = getString(R.string.required_error)
            false
        } else
            true
    }

    private fun validateGender(): Boolean {
        val gender = findViewById<RadioButton>(radioGender.checkedRadioButtonId)
        return gender.isChecked
    }

    fun btnRegisterOnClick(v: View) {
        val progressBar = findViewById<ProgressBar>(R.id.registerProgressBar)
        progressBar.visibility = View.VISIBLE
        val validDob = validateDob()
        val validFirstName = validateFirstName()
        val validLastName = validateLastName()
        val validIC = validateIC()
        val validPhone = validatePhone()
        val validEmail = validateEmail()
        val validPassword = validatePassword()
        val validAddress = validateAddress()
        val validGender = validateGender()
        if (validDob && validFirstName && validLastName && validIC && validPhone
            && validEmail && validPassword && validAddress && validGender
        ) {
            val mAuth = FirebaseAuth.getInstance()
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        val uid = user.uid
                        val gender =
                            findViewById<RadioButton>(radioGender.checkedRadioButtonId).text.toString()
                        val newUser = DataClassUser(
                            positionSpinner.selectedItem.toString(),
                            add,
                            dob,
                            gender,
                            ic,
                            firstName + " " + lastName,
                            phone
                        )
                        val database = FirebaseDatabase.getInstance()
                        val reference = database.getReference("Users")
                        reference.child(uid).setValue(newUser)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "New user is successfully registered!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        progressBar.visibility = View.INVISIBLE
                        MyApplication.name = firstName + " " + lastName
                        finish()
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Failed to register new user!",
                            Toast.LENGTH_LONG
                        ).show()
                        progressBar.visibility = View.INVISIBLE
                    }
                }

        }
        progressBar.visibility = View.INVISIBLE
    }
}