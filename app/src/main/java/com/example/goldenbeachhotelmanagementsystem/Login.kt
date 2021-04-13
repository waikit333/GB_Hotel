package com.example.goldenbeachhotelmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btn = findViewById<Button>(R.id.btnLogin)

    }

    fun btnLoginOnClick(v: View){
        val loading = findViewById<ProgressBar>(R.id.loading)
        val txtEmail = findViewById<EditText>(R.id.txtLoginEmail)
        val txtPassword = findViewById<TextView>(R.id.txtPassword)
        val email = txtEmail.text.toString().trim()
        val password = txtPassword.text.toString().trim()
        val auth = FirebaseAuth.getInstance()

        if (email.isNullOrEmpty()){
            txtEmail.error = "Email is required"
        }
        if (password.isNullOrEmpty()){
            txtEmail.error = "Password is required"
        }
        if(!email.isNullOrEmpty() && !password.isNullOrEmpty()){
            loading.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){ task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"Login successfully",Toast.LENGTH_SHORT)
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                } else {
                    loading.visibility = View.INVISIBLE
                    Toast.makeText(baseContext, "Login failed. Please ensure that you enter the correct email and password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}