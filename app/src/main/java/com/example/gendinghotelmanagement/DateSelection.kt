package com.example.gendinghotelmanagement

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.util.*


class DateSelection : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var btnCheckOccupancy: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_selection)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        btnCheckOccupancy = findViewById(R.id.btnCheckOccupancy);
        btnCheckOccupancy.setOnClickListener { // Do some work here
            val intent = Intent (this@DateSelection,OrderDetails::class.java)
            startActivity(intent);
        }

        val datePicker = findViewById<DatePicker>(R.id.dpCheckInDate)
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year as check in date"
            Toast.makeText(this@DateSelection, msg, Toast.LENGTH_SHORT).show()
        }

        val datePicker1 = findViewById<DatePicker>(R.id.dpCheckOutDate)
        val today1 = Calendar.getInstance()
        datePicker1.init(today1.get(Calendar.YEAR), today1.get(Calendar.MONTH),
                today1.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year as check out date"
            Toast.makeText(this@DateSelection, msg, Toast.LENGTH_SHORT).show()
        }

        //get the spinner from the xml.
        val dropdown = findViewById<Spinner>(R.id.roomType)
        //create a list of items for the spinner.
//create a list of items for the spinner.
        val items = arrayOf("Single Room", "Double Room", "Suite", "Villa")
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
//set the spinners adapter to the previously created one.
//set the spinners adapter to the previously created one.
        dropdown.adapter = adapter


    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@DateSelection,ManagerStaffPortal::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@DateSelection,DateSelection::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@DateSelection,CustomerActivity::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@DateSelection,CheckRoomOccupancy::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                val intent = Intent (this@DateSelection,Login::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Sign out clicked",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}