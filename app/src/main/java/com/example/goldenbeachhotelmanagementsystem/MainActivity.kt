package com.example.goldenbeachhotelmanagementsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //this is a comment
        Toast.makeText(applicationContext, "Firebase connection successfully", Toast.LENGTH_SHORT).show()
    }
}