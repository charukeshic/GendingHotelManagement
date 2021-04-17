package com.example.gendinghotelmanagement

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
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

class EditProfile : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var btnUpdateProfile: Button

    lateinit var txtEmail: EditText
    lateinit var txtRole: EditText
    lateinit var txtName: EditText
    lateinit var txtAddress: EditText
    lateinit var txtPhone: EditText

    lateinit var reff: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

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

        txtEmail = findViewById(R.id.txtEmail)
        txtRole = findViewById(R.id.txtRole)
        txtName = findViewById(R.id.txtName)
        txtAddress = findViewById(R.id.txtAddress)
        txtPhone = findViewById(R.id.txtPhone)

        val findUser = username.toString().replace('.', '-')

        reff = findUser?.let { FirebaseDatabase.getInstance().getReference("User").child(it) }!!;

        reff.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val email = snapshot.child("email").getValue().toString();
                val role = snapshot.child("role").getValue().toString();
                val name = snapshot.child("name").getValue().toString();
                val address = snapshot.child("address").getValue().toString();
                val phone = snapshot.child("phone").getValue().toString();

                txtEmail.setText(email);
                txtRole.setText(role);
                txtName.setText(name);
                txtAddress.setText(address);
                txtPhone.setText(phone);
            }


        });

        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        btnUpdateProfile.setOnClickListener { // Do some work here
            saveProfile();

        }
    }

    private fun saveProfile(){
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtRole = findViewById<EditText>(R.id.txtRole)
        val txtName = findViewById<TextView>(R.id.txtName)
        val txtAddress = findViewById<TextView>(R.id.txtAddress)
        val txtPhone = findViewById<TextView>(R.id.txtPhone)

        val email = txtEmail.text.toString().trim();
        val role = txtRole.text.toString().trim();
        val name = txtName.text.toString().trim();
        val address = txtAddress.text.toString().trim();
        val phone = txtPhone.text.toString().trim();

        if(email.isEmpty()){
            txtEmail.error ="Please enter an email"
            return
        }else if(role.isEmpty()){
            txtRole.error ="Please enter an email"
            return
        }else if(name.isEmpty()){
            txtName.error ="Please enter a name"
            return
        }else if(address.isEmpty()){
            txtAddress.error ="Please enter a address"
            return
        }else if(phone.isEmpty()){
            txtPhone.error ="Please enter a phone number"
            return
        }else{
            val username=intent.getStringExtra("Username")
            reff.child("email").setValue(email)
            reff.child("role").setValue(role)
            reff.child("name").setValue(name)
            reff.child("address").setValue(address)
            reff.child("phone").setValue(phone)
            val intent = Intent(this@EditProfile, ManagerStaffPortal::class.java)
            intent.putExtra("Username", username)
            startActivity(intent);
        }

    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        val username=intent.getStringExtra("Username")
        val roomType=intent.getStringExtra("RoomType")
        when (item.itemId){
            R.id.ic_profile -> {
                val intent = Intent(this@EditProfile, ManagerStaffPortal::class.java)
                intent.putExtra("RoomType", roomType)
                intent.putExtra("Username", username)
                startActivity(intent);
                Toast.makeText(this, "{Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_booking -> {
                val intent = Intent(this@EditProfile, OrderDetails::class.java)
                intent.putExtra("RoomType", roomType)
                intent.putExtra("Username", username)
                startActivity(intent);
                Toast.makeText(this, "{Booking clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_activity -> {
                val intent = Intent(this@EditProfile, CustomerActivity::class.java)
                intent.putExtra("RoomType", roomType)
                intent.putExtra("Username", username)
                startActivity(intent);
                Toast.makeText(this, "{Customer Activity clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_operation -> {
                val intent = Intent(this@EditProfile, CheckRoomOccupancy::class.java)
                intent.putExtra("RoomType", roomType)
                intent.putExtra("Username", username)
                startActivity(intent);
                Toast.makeText(this, "{Operation clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut();
                val intent = Intent(this@EditProfile, Login::class.java)
                intent.putExtra("RoomType", roomType)
                intent.putExtra("Username", username)
                startActivity(intent);
                Toast.makeText(this, "{You are successfully sign out", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}