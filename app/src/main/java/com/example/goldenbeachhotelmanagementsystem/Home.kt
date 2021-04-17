package com.example.goldenbeachhotelmanagementsystem

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.helperclasses.Helper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class Home : AppCompatActivity() {
    private var pressedOnce = false
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var homeNavView: NavigationView
    private var helper = Helper()
    private lateinit var bottomNav:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        //Navigation Drawer
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.home_layout)
        homeNavView = findViewById(R.id.homeNavView)
        setSupportActionBar(toolbar)

        homeNavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    replaceFragment(null)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    bottomNav.setSelectedItemId(R.id.home)
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(Profile())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    bottomNav.setSelectedItemId(R.id.profile)
                    true
                }
                R.id.nav_booking -> {
                    val intent = Intent(this, Booking::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_addNewbooking -> {
                    fabOnClick(findViewById(R.id.fabAddBooking))
                    true
                }
                R.id.nav_checkInOut -> {
                    val intent = Intent(this, CheckInCheckOutMenu::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_checkIn -> {
                    val intent = Intent(this, CheckIns::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_checkOut -> {
                    val intent = Intent(this, CheckIns::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_roomManagement -> {
                    val intent = Intent(this, RoomManagement::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_roomService -> {
                    val intent = Intent(this, RoomService::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        helper.changeNavIconAndTitle(
            supportActionBar,
            true,
            toolbar,
            drawerLayout,
            this,
            getString(R.string.hotel_name)
        )

        var type: String? = ""
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            type = bundle.getString("type")
        }
        replaceFragment(null)

        bottomNav = findViewById<BottomNavigationView>(R.id.bottomAppBarNav)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    replaceFragment(null)
                    true
                }
                R.id.profile -> {
                    replaceFragment(Profile())
                    true
                }
                else -> false
            }
        }
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.logout -> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }

    fun fabOnClick(v: View) {
        val intent = Intent(this, AddNewBooking::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        var backToast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT)
        val myFragment: Fragment? =
            supportFragmentManager.findFragmentByTag("MY_FRAGMENT") as Fragment?
        if (myFragment != null && myFragment.isVisible()) {
            bottomNav.setSelectedItemId(R.id.home)
            replaceFragment(null)
            return
        } else if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (pressedOnce) {
                backToast.cancel()
                finish()
                return
            }
            pressedOnce = true
            backToast.show()
            Handler().postDelayed(Runnable { pressedOnce = false }, 2000)
        }


    }

    override fun onResume() {
        super.onResume()
        replaceFragment(null)
    }

    private fun replaceFragment(fragment: Fragment?) {
        val database = FirebaseDatabase.getInstance()
        val auth = FirebaseAuth.getInstance()
        val manager = supportFragmentManager
        if (fragment == null){
            database.getReference("Users").child(auth.uid.toString()).get().addOnSuccessListener {
                val type = it.child("accessLevel").value.toString()
                if(type == "Manager"){
                    val fragment = home_menu_manager()
                    val transaction = manager.beginTransaction()
                    fragment.arguments = intent.extras
                    transaction.replace(R.id.homeView, fragment)
                    transaction.commit()
                }
                else{
                    val fragment = home_menu_staff()
                    val transaction = manager.beginTransaction()
                    fragment.arguments = intent.extras
                    transaction.replace(R.id.homeView, fragment)
                    transaction.commit()
                }
            }
        }
        else{
            val transaction = manager.beginTransaction()
            fragment.arguments = intent.extras
            transaction.replace(R.id.homeView, fragment,"MY_FRAGMENT").addToBackStack("MY_FRAGMENT").commit();
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.navigation_drawer, menu)

        return true
    }

}