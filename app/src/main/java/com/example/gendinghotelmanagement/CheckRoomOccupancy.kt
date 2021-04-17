package com.example.gendinghotelmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class CheckRoomOccupancy : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var btnCheckRoomOccupancy: Button
    lateinit var roomType: Spinner
    var roomtype: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_room_occupancy)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val username=intent.getStringExtra("Username")

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        roomType = findViewById<Spinner>(R.id.roomType)
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

//        roomtype = dropdown.selectedItem.toString().trim();



        btnCheckRoomOccupancy = findViewById(R.id.btnCheckRoomOccupancy);
        btnCheckRoomOccupancy.setOnClickListener { // Do some work here
            val intent = Intent (this@CheckRoomOccupancy,RoomOccupancyStatus::class.java)
            intent.putExtra("Username",username)
            intent.putExtra("RoomType", roomType.selectedItem.toString())
            startActivity(intent);
        }

    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        val username=intent.getStringExtra("Username")
        val roomType=intent.getStringExtra("RoomType")
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@CheckRoomOccupancy,ManagerStaffPortal::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"{Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@CheckRoomOccupancy,OrderDetails::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"{Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@CheckRoomOccupancy,CustomerActivity::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"{Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@CheckRoomOccupancy,CheckRoomOccupancy::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"{Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@CheckRoomOccupancy,Login::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"{You are successfully sign out",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}