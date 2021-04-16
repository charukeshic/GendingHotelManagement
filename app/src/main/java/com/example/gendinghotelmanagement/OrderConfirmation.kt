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
import com.google.firebase.database.*


class OrderConfirmation : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView


    lateinit var btnOrderCons: Button
    lateinit var txtBookingID: TextView
    lateinit var txtCheckInDay: TextView
    lateinit var txtCheckInMonth: TextView
    lateinit var txtCheckInYear: TextView
    lateinit var txtCheckOutDay: TextView
    lateinit var txtCheckOutMonth: TextView
    lateinit var txtCheckOutYear: TextView
    lateinit var txtCustName: TextView
    lateinit var txtCustIC: TextView
    lateinit var txtCustPhone: TextView
    lateinit var txtCustAddress: TextView
    lateinit var txtRoomType: TextView
    lateinit var txtNoOfPerson: TextView
    lateinit var txtNoOfRoom: TextView
    lateinit var txtExtraServices: TextView
    lateinit var txtToTal: TextView
    var Total: Int? = null
    lateinit var reff: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation)

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


        txtBookingID=findViewById(R.id.txtBookingID);
        txtCheckInDay=findViewById(R.id.txtCheckInDay);
        txtCheckInMonth=findViewById(R.id.txtCheckInMonth);
        txtCheckInYear=findViewById(R.id.txtCheckInYear);
        txtCheckOutDay=findViewById(R.id.txtCheckOutDay);
        txtCheckOutMonth=findViewById(R.id.txtCheckOutMonth);
        txtCheckOutYear=findViewById(R.id.txtCheckOutYear);
        txtCustName=findViewById(R.id.txtCustName);
        txtCustIC=findViewById(R.id.txtCustIC);
        txtCustPhone=findViewById(R.id.txtCustPhone);
        txtCustAddress=findViewById(R.id.txtCustAddress);
        txtRoomType=findViewById(R.id.txtRoomType);
        txtNoOfPerson=findViewById(R.id.txtNoOfPerson);
        txtNoOfRoom=findViewById(R.id.txtNoOfRoom);
        txtExtraServices=findViewById(R.id.txtExtraServices);
        txtToTal=findViewById(R.id.txtToTal);
        val maxid=intent.getStringExtra("OrderNO")


        reff = maxid?.let { FirebaseDatabase.getInstance().getReference("Order").child(it) }!!;
        reff.addValueEventListener(object: ValueEventListener {

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
//                val orderID = snapshot.child("customerName").getValue().toString();
                val checkInDay = snapshot.child("checkInDay" ).getValue().toString();
                val checkInMonth = snapshot.child("checkInMonth").getValue().toString();
                val checkInYear = snapshot.child("checkInYear").getValue().toString();
                val checkOutDay = snapshot.child("checkOutDay" ).getValue().toString();
                val checkOutMonth = snapshot.child("checkOutMonth").getValue().toString();
                val checkOutYear = snapshot.child("checkOutYear").getValue().toString();
                val CustName = snapshot.child("customerName").getValue().toString();
                val CustIC = snapshot.child("customerID").getValue().toString();
                val CustPhone = snapshot.child("phone").getValue().toString();
                val CustAddress = snapshot.child("address").getValue().toString();
                val RoomType = snapshot.child("roomType").getValue().toString();
                val NoOfPerson = snapshot.child("noOfPerson").getValue().toString();
                val NoOfRoom = snapshot.child("noOfRoom").getValue().toString();
                val ExtraServices = snapshot.child("extraServices").getValue().toString();
//                Total = 1 * NoOfRoom;

//                val ToTal = snapshot.child("roomType").getValue().toString();
//                    val Newname = snapshot.child("customerName").value.toString()
//                    txtBookingID.text = Newname
                txtBookingID.setText("Order ID : "+maxid);
                txtCheckInDay.setText("Check In Date : "+checkInDay);
                txtCheckInMonth.setText(checkInMonth);
                txtCheckInYear.setText(checkInYear);
                txtCheckOutDay.setText("Check Out Date : "+checkOutDay);
                txtCheckOutMonth.setText(checkOutMonth);
                txtCheckOutYear.setText(checkOutYear);
                txtCustName.setText("Customer Name : "+CustName);
                txtCustIC.setText("Customer ID : "+CustIC);
                txtCustPhone.setText("Customer Phone : "+CustPhone);
                txtCustAddress.setText("Customer Address : "+CustAddress);
                txtRoomType.setText("Room Type : "+RoomType);
                txtNoOfPerson.setText("No Of Person : "+NoOfPerson);
                txtNoOfRoom.setText("No Of Room : "+NoOfRoom);
                txtExtraServices.setText("Extra Services : "+ExtraServices);

            }



        });

        btnOrderCons = findViewById(R.id.btnOrderCons);
        btnOrderCons.setOnClickListener { // Do some work here
            val intent = Intent(this@OrderConfirmation, Payment::class.java)
            intent.putExtra("Username",username)
            intent.putExtra("OrderNO", maxid)
            startActivity(intent);

        }
    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        val username=intent.getStringExtra("Username")
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@OrderConfirmation,ManagerStaffPortal::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@OrderConfirmation,OrderDetails::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@OrderConfirmation,CustomerActivity::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@OrderConfirmation,CheckRoomOccupancy::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@OrderConfirmation,Login::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"You are successfully sign out",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}


