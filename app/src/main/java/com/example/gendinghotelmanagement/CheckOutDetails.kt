package com.example.gendinghotelmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CheckOutDetails : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var txtCustName: TextView
    lateinit var txtCustIC: TextView
    lateinit var txtCustPhone: TextView
    lateinit var txtRoomType: TextView
    lateinit var txtNoOfRoom: TextView
    lateinit var txtExtraServices: TextView
    lateinit var txtRoomStatus: TextView
    lateinit var txtRoomNum: TextView

    lateinit var btnCheckOutRoomDetails: Button
    lateinit var checkInData: DatabaseReference
    lateinit var roomData: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out_details)

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

        btnCheckOutRoomDetails = findViewById(R.id.btnCheckOutRoomDetails);
        btnCheckOutRoomDetails.setOnClickListener { // Do some work here


            val mDialogView = layoutInflater.inflate(R.layout.confirm_check_out_dialog,null)
            val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setTitle("Confirmation")
                    .setCancelable(false)
            val mAlertDialog = mBuilder.show()
            val dialogConfirmBtn = mDialogView.findViewById<Button>(R.id.dialogConfirmBtn)
            val dialogCancelBtn = mDialogView.findViewById<Button>(R.id.dialogCancelBtn)

            dialogConfirmBtn.setOnClickListener {

                val customer = intent.getStringExtra("CustomerID")
                customer?.let { FirebaseDatabase.getInstance().getReference("CheckIn").child(it).removeValue() }!!

                UpdateRoom()

                mAlertDialog.dismiss()

                Toast.makeText(baseContext, "Room Status Updated", Toast.LENGTH_SHORT).show()

                val intent = Intent (this@CheckOutDetails,CustomerActivity::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);

            }
            dialogCancelBtn.setOnClickListener{
                mAlertDialog.dismiss()

            }


        }



        txtCustName = findViewById(R.id.txtCustName)
        txtCustIC = findViewById(R.id.txtCustIC)
        txtCustPhone = findViewById(R.id.txtCustPhone)
        txtRoomType = findViewById(R.id.txtRoomType)
        txtNoOfRoom = findViewById(R.id.txtNoOfRoom)
        txtExtraServices = findViewById(R.id.txtExtraServices)
        txtRoomStatus = findViewById(R.id.txtRoomStatus)
        txtRoomNum = findViewById(R.id.txtRoomNum)

        val customer = intent.getStringExtra("CustomerID")

        //checkInData = FirebaseDatabase.getInstance().getReference("CheckIn").child(customer)

        checkInData = customer?.let { FirebaseDatabase.getInstance().getReference("CheckIn").child(it) }!!
        checkInData.addValueEventListener(object: ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("customerName").getValue().toString()
                val ic = snapshot.child("customerID").getValue().toString()
                val phone = snapshot.child("phone").getValue().toString()
                val roomType = snapshot.child("roomType").getValue().toString()
                intent.putExtra("typeOfRoom",roomType)
                val noOfRoom = snapshot.child("noOfRoom").getValue().toString()
                val extraServices = snapshot.child("extraServices").getValue().toString()
                val roomStatus = snapshot.child("roomStatus").getValue().toString()
                val roomNum = snapshot.child("roomKey").getValue().toString()

                txtCustName.setText("Customer Name : " + name)
                txtCustIC.setText("Customer ID : " +  ic)
                txtCustPhone.setText("Contact No. : " + phone)
                txtRoomType.setText("Room Type : " + roomType)
                txtNoOfRoom.setText("No Of Room : "+ noOfRoom)
                txtExtraServices.setText("Extra Services : "+ extraServices)
                txtRoomStatus.setText("Room Status : "+ roomStatus)
                txtRoomNum.setText("Room No. : "+ roomNum)



            }


        })


    }

    private fun UpdateRoom() {

        val roomType = intent.getStringExtra("typeOfRoom")
        roomData = roomType?.let { FirebaseDatabase.getInstance().getReference("Room").child(it) }!!
        roomData.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val available = snapshot.child("Available").getValue().toString().toInt()
                val currentAvailable = available.plus(1)
                val occupied = snapshot.child("Occupied").getValue().toString().toInt()
                val currentOccupied = occupied.minus(1)

                roomType?.let { FirebaseDatabase.getInstance().getReference("Room").child(it).child("Available").setValue(currentAvailable) }!!
                roomType?.let { FirebaseDatabase.getInstance().getReference("Room").child(it).child("Occupied").setValue(currentOccupied) }!!



            }


        })


    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        val username=intent.getStringExtra("Username")
        val roomType=intent.getStringExtra("RoomType")
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@CheckOutDetails,ManagerStaffPortal::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"{Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@CheckOutDetails,OrderDetails::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"{Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@CheckOutDetails,CustomerActivity::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"{Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@CheckOutDetails,CheckRoomOccupancy::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"{Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@CheckOutDetails,Login::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"{You are successfully sign out",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}