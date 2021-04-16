package com.example.gendinghotelmanagement

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class AllOrderHistory : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var txtBookingID: TextView
    lateinit var reff: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_order_history)

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
        txtBookingID = findViewById(R.id.txtBookingID)

        reff = FirebaseDatabase.getInstance().reference.child("Order")
        reff.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val childrenCount = dataSnapshot.childrenCount.toInt()
                        val filterRefs = arrayOfNulls<DatabaseReference>(childrenCount)
                        val iterator: Iterator<DataSnapshot> = dataSnapshot.children.iterator()
                        for (i in 0 until childrenCount) {
                            filterRefs[i] = iterator.next().key?.let { reff.child(it) }
                        }
                        return;
//                        for (snapshot in dataSnapshot.children) {
//                            val orderID = snapshot.getValue().toString();
//                            //txtBookingID.text = orderID
//                            println(orderID)
//                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                })

    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        val username=intent.getStringExtra("Username")
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@AllOrderHistory, ManagerStaffPortal::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@AllOrderHistory, OrderDetails::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@AllOrderHistory, CustomerActivity::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@AllOrderHistory, CheckRoomOccupancy::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@AllOrderHistory, Login::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{You are successfully sign out",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}