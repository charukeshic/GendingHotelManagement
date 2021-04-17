package com.example.gendinghotelmanagement

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gendinghotelmanagement.Model.ScheduleModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ArrangeShift : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var btnArrangeShift: Button

    lateinit var monSpinner: Spinner
    lateinit var tueSpinner: Spinner
    lateinit var wedSpinner: Spinner
    lateinit var thuSpinner: Spinner
    lateinit var friSpinner: Spinner
    lateinit var satSpinner: Spinner
    lateinit var sunSpinner: Spinner

    lateinit var name: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrange_shift)

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
        val username=intent.getStringExtra("Username")

        monSpinner = findViewById(R.id.ddlShift1)
        tueSpinner = findViewById(R.id.ddlShift2)
        wedSpinner = findViewById(R.id.ddlShift3)
        thuSpinner = findViewById(R.id.ddlShift4)
        friSpinner = findViewById(R.id.ddlShift5)
        satSpinner = findViewById(R.id.ddlShift6)
        sunSpinner = findViewById(R.id.ddlShift7)
        createSpinner(monSpinner)
        createSpinner(tueSpinner)
        createSpinner(wedSpinner)
        createSpinner(thuSpinner)
        createSpinner(friSpinner)
        createSpinner(satSpinner)
        createSpinner(sunSpinner)

        name = FirebaseDatabase.getInstance().getReference("User")

        name.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                val emails: MutableList<String> = ArrayList()
                for (areaSnapshot in dataSnapshot.children) {
                    val email = areaSnapshot.child("email").getValue(String::class.java)!!
                    emails.add(email)
                }
                val emailSpinner = findViewById<Spinner>(R.id.ddlStaffName)
                val emailsAdapter = ArrayAdapter(this@ArrangeShift, android.R.layout.simple_spinner_dropdown_item, emails)
                emailsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                emailSpinner.adapter = emailsAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        });

        btnArrangeShift = findViewById(R.id.btnArrangeShift);
        btnArrangeShift.setOnClickListener { // Do some work here
            saveShift()
            val intent = Intent(this@ArrangeShift, ArrangeShift::class.java)
            intent.putExtra("Username", username)
            startActivity(intent);
        }
    }

    private fun saveShift(){
        val email = findViewById<Spinner>(R.id.ddlStaffName).selectedItem.toString().trim()
        val emailID = email.replace('.', '-').trim()
        val mon = findViewById<Spinner>(R.id.ddlShift1).selectedItem.toString().trim()
        val tue = findViewById<Spinner>(R.id.ddlShift2).selectedItem.toString().trim()
        val wed = findViewById<Spinner>(R.id.ddlShift3).selectedItem.toString().trim()
        val thu = findViewById<Spinner>(R.id.ddlShift4).selectedItem.toString().trim()
        val fri = findViewById<Spinner>(R.id.ddlShift5).selectedItem.toString().trim()
        val sat = findViewById<Spinner>(R.id.ddlShift6).selectedItem.toString().trim()
        val sun = findViewById<Spinner>(R.id.ddlShift7).selectedItem.toString().trim()

        val schedule = ScheduleModel(email, mon, tue, wed, thu, fri, sat, sun)

        val databaseSchedule = FirebaseDatabase.getInstance().getReference("Schedule");

        databaseSchedule.child(emailID).setValue(schedule).addOnCompleteListener {
            Toast.makeText(this@ArrangeShift, "Shift is successfully updated", Toast.LENGTH_LONG).show()
        }
        /*databaseSchedule.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    val exist = it.child("StaffEmail").getValue(String::class.java)!!
                    //val exist = data.toString().trim()
                    //val exist = it.child("staffEmail").getValue(String::class.java)!!
                    if (exist == emailID) {
                        databaseSchedule.child("Monday").setValue(mon)
                        databaseSchedule.child("Tuesday").setValue(tue)
                        databaseSchedule.child("Wednesday").setValue(wed)
                        databaseSchedule.child("Thursday").setValue(thu)
                        databaseSchedule.child("Friday").setValue(fri)
                        databaseSchedule.child("Saturday").setValue(sat)
                        databaseSchedule.child("Sunday").setValue(sun)
                        Toast.makeText(this@ArrangeShift, "Shift is successfully updated", Toast.LENGTH_LONG).show()
                    } else {
                        databaseSchedule.child(emailID).setValue(schedule).addOnCompleteListener {
                            Toast.makeText(this@ArrangeShift, "Shift is successfully created", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                /*databaseSchedule.child("Monday").setValue(mon)
                databaseSchedule.child("Tuesday").setValue(tue)
                databaseSchedule.child("Wednesday").setValue(wed)
                databaseSchedule.child("Thursday").setValue(thu)
                databaseSchedule.child("Friday").setValue(fri)
                databaseSchedule.child("Saturday").setValue(sat)
                databaseSchedule.child("Sunday").setValue(sun)
                Toast.makeText(this@ArrangeShift, "Shift is successfully updated", Toast.LENGTH_LONG).show()*/


            override fun onCancelled(databaseError: DatabaseError) {}
        });*/







    }

    private fun createSpinner(spinner: Spinner){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                this,
                R.array.shift,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        val username=intent.getStringExtra("Username")
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent(this@ArrangeShift, ManagerStaffPortal::class.java)
                intent.putExtra("Username", username)
                startActivity(intent);
                Toast.makeText(this, "{Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent(this@ArrangeShift, OrderDetails::class.java)
                intent.putExtra("Username", username)
                startActivity(intent);
                Toast.makeText(this, "{Booking clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent(this@ArrangeShift, CustomerActivity::class.java)
                intent.putExtra("Username", username)
                startActivity(intent);
                Toast.makeText(this, "{Customer Activity clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent(this@ArrangeShift, CheckRoomOccupancy::class.java)
                intent.putExtra("Username", username)
                startActivity(intent);
                Toast.makeText(this, "{Operation clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent(this@ArrangeShift, Login::class.java)
                intent.putExtra("Username", username)
                startActivity(intent);
                Toast.makeText(this, "{You are successfully sign out", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
