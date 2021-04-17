package com.example.gendinghotelmanagement

import android.annotation.SuppressLint
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewWorkingSchedule : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_working_schedule)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val UserName = findViewById<TextView>(R.id.textView)
        val username=intent.getStringExtra("Username")
        UserName.text = username

        val monday = findViewById<TextView>(R.id.textMon)
        val tuesday = findViewById<TextView>(R.id.textTue)
        val wednesday = findViewById<TextView>(R.id.textWed)
        val thursday = findViewById<TextView>(R.id.textThu)
        val friday = findViewById<TextView>(R.id.textFri)
        val saturday = findViewById<TextView>(R.id.textSat)
        val sunday = findViewById<TextView>(R.id.textSun)

        val user = username.toString().replace('.','-').trim()

        val reff = user?.let { FirebaseDatabase.getInstance().getReference("Schedule").child(it) }!!;

        reff.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {

            }

            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val mon = snapshot.child("monday").getValue().toString();
                val tue = snapshot.child("tuesday").getValue().toString();
                val wed = snapshot.child("wednesday").getValue().toString();
                val thu = snapshot.child("thursday").getValue().toString();
                val fri = snapshot.child("friday").getValue().toString();
                val sat = snapshot.child("saturday").getValue().toString();
                val sun = snapshot.child("sunday").getValue().toString();

                monday.text= "Monday  : $mon"
                tuesday.text= "Tuesday  : $tue"
                wednesday.text= "Wednesday  : $wed"
                thursday.text= "Thursday  : $thu"
                friday.text= "Friday  : $fri"
                saturday.text= "Saturday  : $sat"
                sunday.text= "Sunday  : $sun"


            }


        });

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        val username=intent.getStringExtra("Username")
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@ViewWorkingSchedule,ManagerStaffPortal::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@ViewWorkingSchedule,OrderDetails::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@ViewWorkingSchedule,CustomerActivity::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@ViewWorkingSchedule,CheckRoomOccupancy::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@ViewWorkingSchedule,Login::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{You are successfully sign out",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}