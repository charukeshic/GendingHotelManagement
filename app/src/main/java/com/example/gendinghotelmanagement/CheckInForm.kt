package com.example.gendinghotelmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gendinghotelmanagement.Model.CreateOrderModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CheckInForm : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var btnCheckInForm: Button

    lateinit var txtName: EditText
    lateinit var txtIC: EditText
    lateinit var txtPhone: EditText
    lateinit var databaseOrder: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in_form)

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

        btnCheckInForm = findViewById(R.id.btnCheckInForm);
        btnCheckInForm.setOnClickListener { // Do some work here

            val intent = Intent (this@CheckInForm, CheckInDetails::class.java)

            txtName = findViewById(R.id.txtName)
            txtIC = findViewById(R.id.txtIC)
            txtPhone = findViewById(R.id.txtPhone)
            val custIC = txtIC.text.toString()
            intent.putExtra("Username",username)
            intent.putExtra("CustomerID", custIC)
            startActivity(intent);


        }




    }



    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        val username=intent.getStringExtra("Username")
        val roomType=intent.getStringExtra("RoomType")
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@CheckInForm, ManagerStaffPortal::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@CheckInForm, OrderDetails::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@CheckInForm, CustomerActivity::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@CheckInForm, CheckRoomOccupancy::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@CheckInForm, Login::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"You are successfully sign out",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}