package com.example.gendinghotelmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class NormalStaffPortal : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var btnCreateOrder: Button
    lateinit var btnCheckRoom: Button
    lateinit var btnManageProfile: Button
    lateinit var btnOrderHistory: Button
    lateinit var btnCustomerActivity: Button
    lateinit var btnViewShift: Button
    lateinit var UserName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_staff_portal)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        //val username=intent.getStringExtra("Username")

        val orderID=intent.getStringExtra("OrderID")
        UserName = findViewById(R.id.UserName)
        val username=intent.getStringExtra("Username")
        UserName.text = username

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        val roomType=intent.getStringExtra("RoomType")

        btnCreateOrder = findViewById(R.id.btnCreateOrder);
        btnCreateOrder.setOnClickListener { // Do some work here
            val intent = Intent (this@NormalStaffPortal,OrderDetails::class.java)
            intent.putExtra("RoomType", roomType)
            intent.putExtra("Username",username)
            startActivity(intent);
        }
        btnCheckRoom = findViewById(R.id.btnCheckRoom);
        btnCheckRoom.setOnClickListener { // Do some work here
            val intent = Intent (this@NormalStaffPortal,CheckRoomOccupancy::class.java)
            intent.putExtra("RoomType", roomType)
            intent.putExtra("Username",username)
            startActivity(intent);
        }
        btnManageProfile = findViewById(R.id.btnManageProfile);
        btnManageProfile.setOnClickListener { // Do some work here
            val intent = Intent (this@NormalStaffPortal,EditProfile::class.java)
            intent.putExtra("RoomType", roomType)
            intent.putExtra("Username",username)
            startActivity(intent);
        }
        btnOrderHistory = findViewById(R.id.btnOrderHistory);
        btnOrderHistory.setOnClickListener { // Do some work here
            val intent = Intent (this@NormalStaffPortal, AllOrderHistory::class.java)
            intent.putExtra("RoomType", roomType)
            intent.putExtra("Username",username)
            startActivity(intent);
        }
        btnCustomerActivity = findViewById(R.id.btnCustomerActivity);
        btnCustomerActivity.setOnClickListener { // Do some work here
            val intent = Intent (this@NormalStaffPortal,CustomerActivity::class.java)
            intent.putExtra("RoomType", roomType)
            intent.putExtra("Username",username)
            startActivity(intent);
        }
        btnViewShift = findViewById(R.id.btnViewShift);
        btnViewShift.setOnClickListener { // Do some work here
            val intent = Intent (this@NormalStaffPortal,ViewWorkingSchedule::class.java)
            intent.putExtra("RoomType", roomType)
            intent.putExtra("Username",username)
            startActivity(intent);
        }
    }
    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        val username=intent.getStringExtra("Username")
        val roomType=intent.getStringExtra("RoomType")
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@NormalStaffPortal,ManagerStaffPortal::class.java)
                startActivity(intent);
                intent.putExtra("RoomType", roomType)
                intent.putExtra("Username",username)
                Toast.makeText(this,"{Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@NormalStaffPortal,OrderDetails::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"{Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@NormalStaffPortal,CustomerActivity::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"{Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@NormalStaffPortal,CheckRoomOccupancy::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"{Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@NormalStaffPortal,Login::class.java)
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