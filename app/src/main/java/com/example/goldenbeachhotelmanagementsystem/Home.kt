package com.example.goldenbeachhotelmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.widget.ButtonBarLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.goldenbeachhoteldataclasses.DataClassRoom
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Home : AppCompatActivity(){
    private lateinit var database: FirebaseDatabase
    private lateinit var roomRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        var type :String? = ""
        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            type = bundle.getString("type")
        }

        if(type!! == "Manager"){
            replaceFragment(home_menu_manager())
        }
        else if(type == "Admin"){
            replaceFragment(home_menu_admin())
        }
        else{
            replaceFragment(home_menu_staff())
        }
    }

    fun fabOnClick(v: View){
        val downtool = findViewById<BottomAppBar>(R.id.bottomAppBar)
        downtool.visibility = View.INVISIBLE
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        var fragment = AddNewBooking()
        transaction.replace(R.id.homeView,fragment)
        transaction.commit()
    }

    private fun replaceFragment(fragment: Fragment){
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        fragment.arguments = intent.extras
        transaction.replace(R.id.homeView,fragment)
        transaction.commit()
    }
}