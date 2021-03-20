package com.example.goldenbeachhotelmanagementsystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Profile : Fragment(R.layout.fragment_profile) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val database = FirebaseDatabase.getInstance()
        val auth = FirebaseAuth.getInstance()
        val fullName = view?.findViewById<TextView>(R.id.txtFullName)
        val dob = view?.findViewById<TextView>(R.id.txtDOB)
        val gender = view?.findViewById<TextView>(R.id.txtGender)
        val ic = view?.findViewById<TextView>(R.id.staff_txtIC)
        val email = view?.findViewById<TextView>(R.id.staff_txtEmail)
        val phone = view?.findViewById<TextView>(R.id.staff_txtPhone)
        val address = view?.findViewById<TextView>(R.id.txtAddress)
        val position = view?.findViewById<TextView>(R.id.txtPosition)

        database.getReference("Users").child(auth.uid.toString()).get().addOnSuccessListener {
            fullName?.text = it.child("name").value.toString()
            dob?.text = it.child("dob").value.toString()
            gender?.text = it.child("gender").value.toString()
            ic?.text = it.child("ic").value.toString()
            email?.text = auth.currentUser.email
            phone?.text = it.child("phone").value.toString()
            address?.text = it.child("address").value.toString()
            position?.text = it.child("accessLevel").value.toString()
        }
        return view
    }


}