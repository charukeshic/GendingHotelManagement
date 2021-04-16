package com.example.gendinghotelmanagement

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


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
    lateinit var UserName: TextView
    private lateinit var mAuth:FirebaseAuth


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

        val orderID=intent.getStringExtra("OrderID")
        UserName = findViewById(R.id.UserName)
        val username=intent.getStringExtra("Username")
        UserName.text = username

//        val user = FirebaseAuth.getInstance().currentUser
//        if (user != null) {
//            // User is signed in
//            UserName.text = user.toString()
//            // No user is signed in
//        }
//        val userID = mAuth!!.currentUser!!.uid
//        if (userID != null) {
//            FirebaseDatabase.getInstance().reference.child("User").child(userID)
//                    .addValueEventListener(object:ValueEventListener{
//                        override fun onCancelled(error: DatabaseError) {
//
//                        }
//
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            val userName = snapshot.child("email").getValue().toString();
//                        //val userName1 = snapshot.value.toString()
//                            UserName.text = userName
//                        }
//
//
//                    })
//        }


        btnCreateOrder = findViewById(R.id.btnCreateOrder);
        btnCreateOrder.setOnClickListener { // Do some work here
            val intent = Intent (this@ManagerStaffPortal,OrderDetails::class.java)
            intent.putExtra("Username",username)
            intent.putExtra("OrderID",orderID)
            startActivity(intent);
        }
        btnCheckRoom = findViewById(R.id.btnCheckRoom);
        btnCheckRoom.setOnClickListener { // Do some work here
            val intent = Intent (this@ManagerStaffPortal,CheckRoomOccupancy::class.java)
            intent.putExtra("Username",username)
            intent.putExtra("OrderID",orderID)
            startActivity(intent);
        }
        btnManageProfile = findViewById(R.id.btnManageProfile);
        btnManageProfile.setOnClickListener { // Do some work here
            val intent = Intent (this@ManagerStaffPortal,EditProfile::class.java)
            intent.putExtra("Username",username)
            intent.putExtra("OrderID",orderID)
            startActivity(intent);
        }
        btnOrderHistory = findViewById(R.id.btnOrderHistory);
        btnOrderHistory.setOnClickListener { // Do some work here
            val intent = Intent (this@ManagerStaffPortal, AllOrderHistory::class.java)
            intent.putExtra("Username",username)
            intent.putExtra("OrderID",orderID)
            startActivity(intent);
        }
        btnCustomerActivity = findViewById(R.id.btnCustomerActivity);
        btnCustomerActivity.setOnClickListener { // Do some work here
            val intent = Intent (this@ManagerStaffPortal,CustomerActivity::class.java)
            intent.putExtra("Username",username)
            intent.putExtra("OrderID",orderID)
            startActivity(intent);
        }
        btnManageStaff = findViewById(R.id.btnManageStaff);
        btnManageStaff.setOnClickListener { // Do some work here
            val intent = Intent (this@ManagerStaffPortal,ManageSchedule::class.java)
            intent.putExtra("Username",username)
            intent.putExtra("OrderID",orderID)
            startActivity(intent);
        }
    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        val orderID=intent.getStringExtra("OrderID")
        val username=intent.getStringExtra("Username")
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@ManagerStaffPortal,ManagerStaffPortal::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("OrderID",orderID)
                startActivity(intent);
                Toast.makeText(this,"{Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@ManagerStaffPortal,OrderDetails::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("OrderID",orderID)
                startActivity(intent);
                Toast.makeText(this,"{Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@ManagerStaffPortal,CustomerActivity::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("OrderID",orderID)
                startActivity(intent);
                Toast.makeText(this,"{Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@ManagerStaffPortal,CheckRoomOccupancy::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("OrderID",orderID)
                startActivity(intent);
                Toast.makeText(this,"{Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@ManagerStaffPortal,Login::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("OrderID",orderID)
                startActivity(intent);
                Toast.makeText(this,"{You are successfully sign out",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}