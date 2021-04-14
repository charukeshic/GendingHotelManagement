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
import com.example.gendinghotelmanagement.Model.CreateOrderModel
import com.example.gendinghotelmanagement.Model.OrderModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

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
    lateinit var checkInDate: DatePicker
    lateinit var checkOutDate: DatePicker
    lateinit var roomType: Spinner
    lateinit var databaseOrder: DatabaseReference

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

        txtName = findViewById<EditText>(R.id.txtName)
        txtIC = findViewById<EditText>(R.id.txtIC)
        txtPhone = findViewById<EditText>(R.id.txtPhone)
        txtAddress = findViewById<EditText>(R.id.txtAddress)
        txtNumOfPeople = findViewById<EditText>(R.id.txtNumOfPeople)
        extraService = findViewById<Spinner>(R.id.extraService)
        checkInDate = findViewById<DatePicker>(R.id.dpCheckInDate)
        checkOutDate = findViewById<DatePicker>(R.id.dpCheckOutDate)
        roomType = findViewById<Spinner>(R.id.roomType)

        val datePicker = findViewById<DatePicker>(R.id.dpCheckInDate)
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year as check in date"
            Toast.makeText(this@OrderDetails, msg, Toast.LENGTH_SHORT).show()
        }

        val datePicker1 = findViewById<DatePicker>(R.id.dpCheckOutDate)
        val today1 = Calendar.getInstance()
        datePicker1.init(today1.get(Calendar.YEAR), today1.get(Calendar.MONTH),
                today1.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year as check out date"
            Toast.makeText(this@OrderDetails, msg, Toast.LENGTH_SHORT).show()
        }

        //get the spinner from the xml.
        val dropdown = findViewById<Spinner>(R.id.roomType)
        //create a list of items for the spinner.
//create a list of items for the spinner.
        val items = arrayOf("Single Room", "Double Room", "Suite", "Villa")
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        val adapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
//set the spinners adapter to the previously created one.
//set the spinners adapter to the previously created one.
        dropdown.adapter = adapter

        //get the spinner from the xml.
        val dropdown1 = findViewById<Spinner>(R.id.extraService)
        //create a list of items for the spinner.
        //create a list of items for the spinner.
        val items1 = arrayOf("Include Breakfast", "Laundry Services", "Airport Pick Up/ Drop Off")
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        val adapter1 =
                ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items1)
        //set the spinners adapter to the previously created one.
        //set the spinners adapter to the previously created one.
        dropdown1.adapter = adapter1

        btnUpdateOrder = findViewById(R.id.btnUpdateOrder);
        btnUpdateOrder.setOnClickListener { // Do some work here
            UpdateOrder()
            val intent = Intent (this@OrderDetails,OrderConfirmation::class.java)
            startActivity(intent);
        }



//        txtName = findViewById(R.id.txtName)
//        txtIC = findViewById(R.id.txtIC)
//        txtPhone = findViewById(R.id.txtPhone)
//        txtAddress = findViewById(R.id.txtAddress)
//        txtNumOfPeople = findViewById(R.id.txtNumOfPeople)
//        extraService = findViewById(R.id.extraService)

    }

    // saving order in database
    private fun UpdateOrder() {
        val name = txtName.text.toString().trim()
        val ic = txtIC.text.toString().trim()
        val phone = txtPhone.text.toString().trim()
        val address = txtAddress.text.toString().trim()
        val noOfPeople = txtNumOfPeople.text.toString().trim().toInt()
        val extraServices = extraService.selectedItem.toString().trim()
        val checkInDate = checkInDate.minDate.toString().trim()
        val checkOutDate = checkOutDate.minDate.toString().trim()
        val roomType = roomType.selectedItem.toString().trim()


        if(name.isEmpty()){
            txtName.error = "Please enter a name"
            return
        }

        //val order = OrderModel(checkIn, checkOut, ic, extraServices, noOfPeople, orderID, status, qtyOfRooms, roomNO, roomType)

        databaseOrder = FirebaseDatabase.getInstance().getReference("Order");

        val orderNO = databaseOrder.push().key

        //val order = OrderModel(CheckInDate,CheckOutDate,name,ic,extraServices,noOfPeople,OrderID,OrderStatus,QuantityOfRooms,RoomNo,RoomType,StaffName,Total)
        val order = CreateOrderModel(name,ic,phone,address,noOfPeople,extraServices,roomType,checkInDate,checkOutDate)
        if (orderNO != null) {
            databaseOrder.child(orderNO).setValue(order).addOnCompleteListener{
                Toast.makeText(applicationContext,"Data is saved",Toast.LENGTH_LONG).show()
            }
        }
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