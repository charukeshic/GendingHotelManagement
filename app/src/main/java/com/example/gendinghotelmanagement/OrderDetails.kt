package com.example.gendinghotelmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class OrderDetails : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var btnUpdateOrder: Button

    lateinit var txtName: EditText
    lateinit var txtIC: EditText
    lateinit var txtPhone: EditText
    lateinit var txtAddress: EditText
    lateinit var txtNumOfPeople: EditText
    lateinit var extraService: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

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

        btnUpdateOrder = findViewById(R.id.btnUpdateOrder);
        btnUpdateOrder.setOnClickListener { // Do some work here
            val intent = Intent (this@OrderDetails,OrderConfirmation::class.java)
            startActivity(intent);
            saveOrder()
        }

        //get the spinner from the xml.
        val dropdown = findViewById<Spinner>(R.id.extraService)
        //create a list of items for the spinner.
        //create a list of items for the spinner.
        val items = arrayOf("Include Breakfast", "Laundry Services", "Airport Pick Up/ Drop Off")
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
            //set the spinners adapter to the previously created one.
            //set the spinners adapter to the previously created one.
        dropdown.adapter = adapter

        txtName = findViewById(R.id.txtName)
        txtIC = findViewById(R.id.txtIC)
        txtPhone = findViewById(R.id.txtPhone)
        txtAddress = findViewById(R.id.txtAddress)
        txtNumOfPeople = findViewById(R.id.txtNumOfPeople)
        extraService = findViewById(R.id.extraService)

    }

    private fun saveOrder() {
        var checkIn = "2/3/2020"
        val checkOut = "2/3/2020"
        val name = txtName.text.toString().trim()
        val ic = txtIC.text.toString().trim()
        val phone = txtPhone.text.toString().trim()
        val address = txtAddress.text.toString().trim()
        val noOfPeople = txtNumOfPeople.text.toString().trim().toInt()
        val extraServices = extraService.toString().trim()


        if(name.isEmpty()){
            txtName.error = "Please enter a name"
            return
        }

        //val order = OrderModel(checkIn, checkOut, ic, extraServices, noOfPeople, orderID, status, qtyOfRooms, roomNO, roomType)

        val ref = FirebaseDatabase.getInstance().getReference("orders")

        val orderID = ref.push().key

    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@OrderDetails,ManagerStaffPortal::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@OrderDetails,OrderDetails::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@OrderDetails,CustomerActivity::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@OrderDetails,CheckRoomOccupancy::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@OrderDetails,Login::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Sign out clicked",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}