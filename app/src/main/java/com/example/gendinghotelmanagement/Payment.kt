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
import com.example.gendinghotelmanagement.Model.PaymentModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Payment : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var btnPayment: Button
    lateinit var paymentMethod: Spinner
    lateinit var databasePayment: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

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

        paymentMethod = findViewById<Spinner>(R.id.paymentMethod)

        val dropdown = findViewById<Spinner>(R.id.paymentMethod)
        //create a list of items for the spinner.
//create a list of items for the spinner.
        val items = arrayOf("Cash Payment", "Credit Card Payment", "Touch N Go")
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        val adapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
//set the spinners adapter to the previously created one.
//set the spinners adapter to the previously created one.
        dropdown.adapter = adapter

        btnPayment = findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener { // Do some work here
            UpdatePayment()
            val intent = Intent (this@Payment,Receipt::class.java)
            startActivity(intent);
        }
    }
    private fun UpdatePayment() {

        val PaymentMethod = paymentMethod.selectedItem.toString().trim()


        //val order = OrderModel(checkIn, checkOut, ic, extraServices, noOfPeople, orderID, status, qtyOfRooms, roomNO, roomType)

        databasePayment = FirebaseDatabase.getInstance().getReference("Payment");

        val payID = databasePayment.push().key

        //val order = OrderModel(CheckInDate,CheckOutDate,name,ic,extraServices,noOfPeople,OrderID,OrderStatus,QuantityOfRooms,RoomNo,RoomType,StaffName,Total)
        val payment = PaymentModel(payID,PaymentMethod)
        if (payID != null) {

            databasePayment.child(payID).setValue(payment).addOnCompleteListener{
                Toast.makeText(applicationContext,"Payment Data is saved",Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@Payment,ManagerStaffPortal::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@Payment,OrderDetails::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@Payment,CustomerActivity::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@Payment,CheckRoomOccupancy::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@Payment,Login::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Sign out clicked",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}