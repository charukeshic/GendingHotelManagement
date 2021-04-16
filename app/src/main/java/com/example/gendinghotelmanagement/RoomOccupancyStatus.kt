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
import com.google.firebase.database.*

class RoomOccupancyStatus : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var txtRoom: TextView
    lateinit var availableUnit: TextView
    lateinit var OccupiedUnit: TextView
    lateinit var CleaningUnit: TextView
    lateinit var TotalUnit: TextView
    lateinit var DamagedUnit: TextView
    lateinit var reff: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_occupancy_status)

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

        txtRoom = findViewById(R.id.txtRoom)
        availableUnit = findViewById(R.id.availableUnit)
        OccupiedUnit = findViewById(R.id.OccupiedUnit)
        CleaningUnit = findViewById(R.id.CleaningUnit)
        TotalUnit = findViewById(R.id.TotalUnit)
        DamagedUnit = findViewById(R.id.DamagedUnit)

        val roomType=intent.getStringExtra("RoomType")
        txtRoom.text = roomType

//        reff = FirebaseDatabase.getInstance().getReference("Room")
        reff = (roomType?.let { FirebaseDatabase.getInstance().getReference("Room").child(it) } )!!;
        reff.equalTo(roomType)
        reff.addValueEventListener(object: ValueEventListener {

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val availableUnit1 = snapshot.child("Available" ).getValue().toString();
                val OccupiedUnit1 = snapshot.child("Occupied").getValue().toString();
                val CleaningUnit1 = snapshot.child("Cleaning").getValue().toString();
                val TotalUnit1 = snapshot.child("Total" ).getValue().toString();
                val DamagedUnit1 = snapshot.child("Damaged").getValue().toString();

                availableUnit.setText(availableUnit1);
                OccupiedUnit.setText(OccupiedUnit1);
                CleaningUnit.setText(CleaningUnit1);
                TotalUnit.setText(TotalUnit1);
                DamagedUnit.setText(DamagedUnit1);
            }



        });
    }


    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        val username=intent.getStringExtra("Username")
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@RoomOccupancyStatus,ManagerStaffPortal::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@RoomOccupancyStatus,OrderDetails::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@RoomOccupancyStatus,CustomerActivity::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@RoomOccupancyStatus,CheckRoomOccupancy::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@RoomOccupancyStatus,Login::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{You are successfully sign out",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}