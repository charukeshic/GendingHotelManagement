package com.example.gendinghotelmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class ManagerStaffPortal : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var btnCreateOrder: Button
    lateinit var btnCheckRoom: Button
    lateinit var btnManageProfile: Button
    lateinit var btnOrderHistory: Button
    lateinit var btnCustomerActivity: Button
    lateinit var btnManageStaff: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_staff_portal)


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



        btnCreateOrder = findViewById(R.id.btnCreateOrder);
        btnCreateOrder.setOnClickListener { // Do some work here
            val intent = Intent (this@ManagerStaffPortal,OrderDetails::class.java)
            startActivity(intent);
        }
        btnCheckRoom = findViewById(R.id.btnCheckRoom);
        btnCheckRoom.setOnClickListener { // Do some work here
            val intent = Intent (this@ManagerStaffPortal,CheckRoomOccupancy::class.java)
            startActivity(intent);
        }
        btnManageProfile = findViewById(R.id.btnManageProfile);
        btnManageProfile.setOnClickListener { // Do some work here
            val intent = Intent (this@ManagerStaffPortal,EditProfile::class.java)
            startActivity(intent);
        }
        btnOrderHistory = findViewById(R.id.btnOrderHistory);
        btnOrderHistory.setOnClickListener { // Do some work here
            val intent = Intent (this@ManagerStaffPortal, AllOrderHistory::class.java)
            startActivity(intent);
        }
        btnCustomerActivity = findViewById(R.id.btnCustomerActivity);
        btnCustomerActivity.setOnClickListener { // Do some work here
            val intent = Intent (this@ManagerStaffPortal,CustomerActivity::class.java)
            startActivity(intent);
        }
        btnManageStaff = findViewById(R.id.btnManageStaff);
        btnManageStaff.setOnClickListener { // Do some work here
            val intent = Intent (this@ManagerStaffPortal,StaffManagement::class.java)
            startActivity(intent);
        }
    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@ManagerStaffPortal,ManagerStaffPortal::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@ManagerStaffPortal,OrderDetails::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@ManagerStaffPortal,CustomerActivity::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@ManagerStaffPortal,CheckRoomOccupancy::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@ManagerStaffPortal,Login::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Sign out clicked",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}