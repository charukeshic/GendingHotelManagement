package com.example.gendinghotelmanagement

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gendinghotelmanagement.Model.PaymentModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Payment : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var btnPayment: Button
    lateinit var paymentMethod: Spinner
    lateinit var databasePayment: DatabaseReference

    lateinit var txtBookingID: TextView
    lateinit var txtCheckInDay: TextView
    lateinit var txtCheckInMonth: TextView
    lateinit var txtCheckInYear: TextView
    lateinit var txtCheckOutDay: TextView
    lateinit var txtCheckOutMonth: TextView
    lateinit var txtCheckOutYear: TextView
    lateinit var txtCustName: TextView
    lateinit var txtToTal: TextView
    lateinit var reff: DatabaseReference

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
        val username=intent.getStringExtra("Username")
        val total=intent.getStringExtra("Total")

        txtBookingID=findViewById(R.id.txtBookingID);
        txtCheckInDay=findViewById(R.id.txtCheckInDay);
        txtCheckInMonth=findViewById(R.id.txtCheckInMonth);
        txtCheckInYear=findViewById(R.id.txtCheckInYear);
        txtCheckOutDay=findViewById(R.id.txtCheckOutDay);
        txtCheckOutMonth=findViewById(R.id.txtCheckOutMonth);
        txtCheckOutYear=findViewById(R.id.txtCheckOutYear);
        txtCustName=findViewById(R.id.txtCustName);
        txtToTal=findViewById(R.id.txtToTal);
        paymentMethod = findViewById<Spinner>(R.id.paymentMethod)

        val orderID=intent.getStringExtra("OrderNO")

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

        reff = orderID?.let { FirebaseDatabase.getInstance().getReference("Order").child(it) }!!;
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

//                Total = 1 * NoOfRoom;

//                val ToTal = snapshot.child("roomType").getValue().toString();
//                    val Newname = snapshot.child("customerName").value.toString()
//                    txtBookingID.text = Newname
                txtBookingID.setText("Order ID : "+orderID);
                txtCheckInDay.setText("Check In Date : "+checkInDay);
                txtCheckInMonth.setText(checkInMonth);
                txtCheckInYear.setText(checkInYear);
                txtCheckOutDay.setText("Check Out Date : "+checkOutDay);
                txtCheckOutMonth.setText(checkOutMonth);
                txtCheckOutYear.setText(checkOutYear);
                txtCustName.setText("Customer Name : "+CustName);
                txtToTal.setText("Total : RM "+total);

            }



        });



        btnPayment = findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener { // Do some work here

            val mDialogView = layoutInflater.inflate(R.layout.confirm_dialog,null)
            val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setTitle("Confirmation")
                    .setCancelable(false)
            val mAlertDialog = mBuilder.show()
            val dialogConfirmBtn = mDialogView.findViewById<Button>(R.id.dialogConfirmBtn)
            val dialogCancelBtn = mDialogView.findViewById<Button>(R.id.dialogCancelBtn)

            dialogConfirmBtn.setOnClickListener {

                UpdatePayment()
                val intent = Intent (this@Payment,Receipt::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("OrderNO", orderID)
                startActivity(intent);
                mAlertDialog.dismiss()

            }
            dialogCancelBtn.setOnClickListener{
                mAlertDialog.dismiss()

            }

        }
    }
    private fun UpdatePayment() {

        val PaymentMethod = paymentMethod.selectedItem.toString().trim()
        val orderID=intent.getStringExtra("OrderNO")

        //val order = OrderModel(checkIn, checkOut, ic, extraServices, noOfPeople, orderID, status, qtyOfRooms, roomNO, roomType)

        databasePayment = FirebaseDatabase.getInstance().getReference("Payment");

        //val payID = databasePayment.push().key
        val total=intent.getStringExtra("Total").toString().toInt()
        //val order = OrderModel(CheckInDate,CheckOutDate,name,ic,extraServices,noOfPeople,OrderID,OrderStatus,QuantityOfRooms,RoomNo,RoomType,StaffName,Total)
        val payment = PaymentModel(orderID,PaymentMethod,total)
        if (orderID != null) {

            databasePayment.child(orderID).setValue(payment).addOnCompleteListener{
                Toast.makeText(applicationContext,"Payment Data is saved",Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        val username=intent.getStringExtra("Username")
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@Payment,ManagerStaffPortal::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@Payment,OrderDetails::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@Payment,CustomerActivity::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@Payment,CheckRoomOccupancy::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@Payment,Login::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{You are successfully sign out",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}