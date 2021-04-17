package com.example.gendinghotelmanagement

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gendinghotelmanagement.Model.CheckInModel
import com.example.gendinghotelmanagement.Model.CreateOrderModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class AllOrderHistory : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var txtBookingID: TextView
    lateinit var order_list: RecyclerView
    lateinit var mDatabase:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_order_history)

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


        //txtBookingID = findViewById(R.id.txtBookingID)

        mDatabase = FirebaseDatabase.getInstance().getReference("Order")
        order_list = findViewById(R.id.order_list)
        order_list.setHasFixedSize(true);
        order_list.setLayoutManager(LinearLayoutManager(this));
        logRecyclerView()

    }
    private fun logRecyclerView(){

        var FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<CreateOrderModel,OrderViewHolder>(
                CreateOrderModel::class.java,
                R.layout.list_item_single,
                OrderViewHolder::class.java,
                mDatabase
        ){
            override fun populateViewHolder(viewHolder:OrderViewHolder?,model:CreateOrderModel?,position:Int) {


                //val orderID=intent.getStringExtra("OrderID")
                if (model != null) {
                    if (viewHolder != null) {
                        viewHolder.txtBookingID.setText("Order ID : " + model.OrderID)
                        viewHolder.txtCheckInDay.setText("Check In Date : " + model.CheckInDay)
                        viewHolder.txtCheckOutDay.setText("Check Out Date : " + model.CheckOutDay)
                        viewHolder.txtCustName.setText("Customer Name : " + model.CustomerName)
                        viewHolder.txtCustPhone.setText("Phone : " + model.Phone)
                        viewHolder.txtCustIC.setText("Customer ID : " + model.CustomerID)
                        viewHolder.txtRoomType.setText("Room Type : " + model.RoomType)
                        viewHolder.txtNoOfPerson.setText("No Of Person : " + model.NoOfPerson.toString())
                        viewHolder.txtNoOfRoom.setText("No Of Room : " + model.NoOfRoom.toString())
//                        model.NoOfPerson?.let { viewHolder.txtNoOfPerson.setText(it) }
//                        model.NoOfRoom?.let { viewHolder.txtNoOfRoom.setText(it) }
                        viewHolder.txtExtraServices.setText("Extra Services : " + model.ExtraServices)
                        viewHolder.txtCustAddress.setText("Address : " + model.Address)
                        viewHolder.txtCheckInMonth.setText(model.CheckInMonth)
                        viewHolder.txtCheckInYear.setText(model.CheckInYear)
                        viewHolder.txtCheckOutMonth.setText(model.CheckOutMonth)
                        viewHolder.txtCheckOutYear.setText(model.CheckOutYear)
//                        viewHolder.txtStaffName.setText("Staff Name : " + model.StaffName)
//                        viewHolder.txtRoomKey.setText("Room Key : " + model.RoomKey)
//                        viewHolder.txtRoomStatus.setText("Room Status : " + model.RoomStatus)

                    }
                }

            }

        }
        order_list.adapter = FirebaseRecyclerAdapter
    }
    class OrderViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {


        val txtBookingID = mView.findViewById(R.id.txtBookingID) as TextView
        val txtCheckInDay = mView.findViewById(R.id.txtCheckInDay) as TextView
        val txtCheckOutDay = mView.findViewById(R.id.txtCheckOutDay) as TextView
        val txtCustName = mView.findViewById(R.id.txtCustName) as TextView
        val txtCustPhone = mView.findViewById(R.id.txtCustPhone) as TextView
        val txtCustIC = mView.findViewById(R.id.txtCustIC) as TextView
        val txtRoomType = mView.findViewById(R.id.txtRoomType) as TextView
        val txtNoOfPerson = mView.findViewById(R.id.txtNoOfPerson) as TextView
        val txtNoOfRoom = mView.findViewById(R.id.txtNoOfRoom) as TextView
        val txtExtraServices = mView.findViewById(R.id.txtExtraServices) as TextView
        val txtCustAddress = mView.findViewById(R.id.txtCustAddress) as TextView
        val txtCheckInMonth = mView.findViewById(R.id.txtCheckInMonth) as TextView
        val txtCheckInYear = mView.findViewById(R.id.txtCheckInYear) as TextView
        val txtCheckOutMonth = mView.findViewById(R.id.txtCheckOutMonth) as TextView
        val txtCheckOutYear = mView.findViewById(R.id.txtCheckOutYear) as TextView
//        val txtStaffName = mView.findViewById(R.id.txtStaffName) as TextView
//        val txtRoomKey = mView.findViewById(R.id.txtRoomKey) as TextView
//        val txtRoomStatus = mView.findViewById(R.id.txtRoomStatus) as TextView

    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        val username=intent.getStringExtra("Username")
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@AllOrderHistory, ManagerStaffPortal::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@AllOrderHistory, OrderDetails::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@AllOrderHistory, CustomerActivity::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@AllOrderHistory, CheckRoomOccupancy::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@AllOrderHistory, Login::class.java)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"{You are successfully sign out",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}