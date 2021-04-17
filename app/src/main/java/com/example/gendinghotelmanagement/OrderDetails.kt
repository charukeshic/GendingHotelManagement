package com.example.gendinghotelmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gendinghotelmanagement.Model.CheckInModel
import com.example.gendinghotelmanagement.Model.CreateOrderModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

class OrderDetails : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var btnUpdateOrder: Button
    lateinit var txtStayingPeriod: TextView

    lateinit var txtName: EditText
    lateinit var txtIC: EditText
    lateinit var txtPhone: EditText
    lateinit var txtAddress: EditText
    lateinit var txtNumOfPeople: EditText
    lateinit var txtNumOfRoom: EditText
    lateinit var extraService: Spinner
    lateinit var checkInDate: DatePicker
    lateinit var checkOutDate: DatePicker
    lateinit var roomType: Spinner
    lateinit var databaseOrder: DatabaseReference
    lateinit var databaseCheckIn: DatabaseReference

    @ExperimentalTime
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
        val username=intent.getStringExtra("Username")

        txtName = findViewById<EditText>(R.id.txtName)
        txtIC = findViewById<EditText>(R.id.txtIC)
        txtPhone = findViewById<EditText>(R.id.txtPhone)
        txtAddress = findViewById<EditText>(R.id.txtAddress)
        txtNumOfPeople = findViewById<EditText>(R.id.txtNumOfPeople)
        txtNumOfRoom = findViewById<EditText>(R.id.txtNumOfRoom)
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
            val Indate = "$month/$day/$year"
            val msg = "You Selected: $day/$month/$year as check in date"
            Toast.makeText(this@OrderDetails, msg, Toast.LENGTH_SHORT).show()


        val datePicker1 = findViewById<DatePicker>(R.id.dpCheckOutDate)
        val today1 = Calendar.getInstance()
        datePicker1.init(today1.get(Calendar.YEAR), today1.get(Calendar.MONTH),
                today1.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
            val Outdate = "$month/$day/$year"
            val msg = "You Selected: $day/$month/$year as check out date"
            Toast.makeText(this@OrderDetails, msg, Toast.LENGTH_SHORT).show()



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
        val items1 = arrayOf("No Need","Include Breakfast", "Laundry Services", "Airport Pick Up/ Drop Off")
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
            val format = SimpleDateFormat("MM/dd/yyyy")
            val days = DurationUnit.DAYS.convert(
                    format.parse(Outdate).time - format.parse(Indate).time,
                    DurationUnit.MILLISECONDS
            )
            txtStayingPeriod = findViewById(R.id.txtStayingPeriod)
            txtStayingPeriod.text= days.toString()
            UpdateOrder()
            UpdateCheckIn()

        }


    }
        }
    }
    // saving order in database
    private fun UpdateOrder() {

        val name = txtName.text.toString().trim()
        val ic = txtIC.text.toString().trim()
        val phone = txtPhone.text.toString().trim()
        val address = txtAddress.text.toString().trim()
        val noOfPeople = txtNumOfPeople.text.toString().trim().toInt()
        val noOfRoom = txtNumOfRoom.text.toString().trim().toInt()
        val extraServices = extraService.selectedItem.toString().trim()
        val checkInDay = checkInDate.dayOfMonth.toString().trim()
        val checkInMonth = (checkInDate.month+ 1).toString().trim()
        val checkInYear = checkInDate.year.toString().trim()
        val checkOutDay = checkOutDate.dayOfMonth.toString().trim()
        val checkOutMonth = (checkOutDate.month + 1).toString().trim()
        val checkOutYear = checkOutDate.year.toString().trim()
        val roomType = roomType.selectedItem.toString().trim()

        val username=intent.getStringExtra("Username")

            if (name.isEmpty()) {
            txtName.error = "Please enter a name"
            return
            }


            databaseOrder = FirebaseDatabase.getInstance().getReference("Order");

            //var i: Int? = null

            val orderNO = databaseOrder.push().key

            //val order = OrderModel(CheckInDate,CheckOutDate,name,ic,extraServices,noOfPeople,OrderID,OrderStatus,QuantityOfRooms,RoomNo,RoomType,StaffName,Total)


            val mDialogView = layoutInflater.inflate(R.layout.confirm_dialog,null)
            val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setTitle("Confirmation")
                    .setCancelable(false)
            val mAlertDialog = mBuilder.show()
            val dialogConfirmBtn = mDialogView.findViewById<Button>(R.id.dialogConfirmBtn)
            val dialogCancelBtn = mDialogView.findViewById<Button>(R.id.dialogCancelBtn)

            dialogConfirmBtn.setOnClickListener {

                val order = CreateOrderModel(orderNO,name, ic, phone, address, noOfPeople, noOfRoom, extraServices, roomType, checkInDay, checkInMonth, checkInYear, checkOutDay, checkOutMonth, checkOutYear)
                if (orderNO != null) {
                    databaseOrder.child(orderNO).setValue(order).addOnCompleteListener {
                        Toast.makeText(applicationContext, "Data is saved", Toast.LENGTH_LONG).show()
                    }
                }
                val intent = Intent(this@OrderDetails, OrderConfirmation::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("OrderNO", orderNO)
                intent.putExtra("RoomType", roomType)
                intent.putExtra("numberOfRoom",txtNumOfRoom.text.toString())
                intent.putExtra("StayingPeriod",txtStayingPeriod.text)
                startActivity(intent);
                mAlertDialog.dismiss()

            }
            dialogCancelBtn.setOnClickListener{
            mAlertDialog.dismiss()

            }
        }

    private fun UpdateCheckIn() {

        val name = txtName.text.toString().trim()
        val ic = txtIC.text.toString().trim()
        val phone = txtPhone.text.toString().trim()
        val address = txtAddress.text.toString().trim()
        val noOfPeople = txtNumOfPeople.text.toString().trim().toInt()
        val noOfRoom = txtNumOfRoom.text.toString().trim().toInt()
        val extraServices = extraService.selectedItem.toString().trim()
        val checkInDay = checkInDate.dayOfMonth.toString().trim()
        val checkInMonth = (checkInDate.month+1).toString().trim()
        val checkInYear = checkInDate.year.toString().trim()
        val checkOutDay = checkOutDate.dayOfMonth.toString().trim()
        val checkOutMonth = checkOutDate.month.toString().trim()
        val checkOutYear = checkOutDate.year.toString().trim()
        val roomType = roomType.selectedItem.toString().trim()
        val staffName = "Unassigned"
        val roomKey = "Unassigned"
        val roomStatus = "Pending"


        databaseCheckIn = FirebaseDatabase.getInstance().getReference("CheckIn");

        //val orderNO = databaseOrder.push().key

        val order = CheckInModel(name,ic,phone,address,noOfPeople, noOfRoom, extraServices,roomType,checkInDay,checkInMonth,
                checkInYear,checkOutDay,checkOutMonth,checkOutYear,staffName,roomKey, roomStatus)
        intent.putExtra("RoomType", roomType)
        intent.putExtra("numberOfRoom",txtNumOfRoom.text.toString())

        if (ic != null) {
            databaseCheckIn.child(ic).setValue(order).addOnCompleteListener{
                //Toast.makeText(applicationContext,"Data is saved",Toast.LENGTH_LONG).show()

            }
        }
    }






    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        val username=intent.getStringExtra("Username")
        val roomType=intent.getStringExtra("RoomType")
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent (this@OrderDetails,ManagerStaffPortal::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"Profile clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent (this@OrderDetails,OrderDetails::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"Booking clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent (this@OrderDetails,CustomerActivity::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"Customer Activity clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent (this@OrderDetails,CheckRoomOccupancy::class.java)
                intent.putExtra("Username",username)
                intent.putExtra("RoomType", roomType)
                startActivity(intent);
                Toast.makeText(this,"Operation clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent (this@OrderDetails,Login::class.java)
                intent.putExtra("RoomType", roomType)
                intent.putExtra("Username",username)
                startActivity(intent);
                Toast.makeText(this,"You are successfully sign out",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}
