package com.example.gendinghotelmanagement

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
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

class CheckInDetails : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView


    lateinit var txtCustName: TextView
    lateinit var txtCustIC: TextView
    lateinit var txtCustPhone: TextView
    lateinit var txtRoomType: TextView

    lateinit var btnCheckInRoomDetails: Button
    lateinit var checkInData: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in_details)

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

        btnCheckInRoomDetails = findViewById(R.id.btnCheckInRoomDetails);
        btnCheckInRoomDetails.setOnClickListener { // Do some work here
            //val intent = Intent (this@CheckInDetails, CustomerActivity::class.java)
            //startActivity(intent);

            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.check_in_dialog,null)
            val staffName = dialogView.findViewById<EditText>(R.id.dialogStaffName)
            val roomKey = dialogView.findViewById<EditText>(R.id.dialogRoomKey)
            dialog.setView(dialogView)
            dialog.setCancelable(false)
            dialog.setPositiveButton("Confirm",{dialogInterface: DialogInterface, i:Int -> })
            val customDialog = dialog.create()
            customDialog.show()
            customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                Toast.makeText(baseContext, "Room Status Updated", Toast.LENGTH_SHORT).show()
                customDialog.dismiss()
            }

        }



        txtCustName = findViewById<EditText>(R.id.txtCustName)
        txtCustIC = findViewById<EditText>(R.id.txtCustIC)
        txtCustPhone = findViewById<EditText>(R.id.txtCustPhone)
        txtRoomType = findViewById<EditText>(R.id.txtRoomType)

        val customer = txtCustIC.text.toString().trim()

        checkInData = FirebaseDatabase.getInstance().getReference("CheckIn").child(customer)
        checkInData.addValueEventListener(object: ValueEventListener {

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("customerName").getValue().toString()
                val ic = snapshot.child("customerID").getValue().toString()
                val phone = snapshot.child("phone").getValue().toString()
                val roomType = snapshot.child("roomType").getValue().toString()
//                    val Newname = snapshot.child("customerName").value.toString()
//                    txtBookingID.text = Newname
                txtCustName.setText(name)
                txtCustIC.setText(ic)
                txtCustPhone.setText(phone)
                txtRoomType.setText(roomType)
            }


//            val intent = Intent (this@OrderConfirmation,Payment::class.java)
//            startActivity(intent);
        })





    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@CheckInDetails, ManagerStaffPortal::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@CheckInDetails, OrderDetails::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@CheckInDetails, CustomerActivity::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@CheckInDetails, CheckRoomOccupancy::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@CheckInDetails, Login::class.java)
                startActivity(intent);
                Toast.makeText(this,"{Sign out clicked",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}