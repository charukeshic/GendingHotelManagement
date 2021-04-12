package com.example.gendinghotelmanagement

import android.app.Activity
import android.app.Instrumentation
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SignUp : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var btnSignUp: Button
    lateinit var txtStaffID: EditText
    lateinit var txtPassword: EditText
    lateinit var txtConPassword: EditText
    lateinit var staffRole: RadioGroup
    lateinit var signin: TextView
    lateinit var databaseUser: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)



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


        staffRole = findViewById<RadioGroup>(R.id.staffRole)
        txtStaffID = findViewById<EditText>(R.id.txtStaffID)
        txtPassword = findViewById<EditText>(R.id.txtPassword)
        txtConPassword = findViewById<EditText>(R.id.txtConPassword)
        signin = findViewById<TextView>(R.id.signin)

        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener { // Do some work here
            SignupFun()
        }
    }

                private fun SignupFun(){
            val email = txtStaffID.getText().toString().trim();
            val password = txtPassword.getText().toString().trim();
            val conPassword = txtConPassword.getText().toString().trim();
            val role = staffRole.getCheckedRadioButtonId().toString().trim();

        if(email.isEmpty()){

            txtStaffID.error ="Please enter an email"
            return

        }


                    databaseUser = FirebaseDatabase.getInstance().getReference("User");

                    val userID = databaseUser.push().key

                    val user = UserModel(userID,email,password,conPassword, role)
                    if (userID != null) {
                        databaseUser.child(userID).setValue(user).addOnCompleteListener{
                            Toast.makeText(applicationContext,"Account is successfully created",Toast.LENGTH_LONG).show()
                        }
                    }
}

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
        when (item.itemId) {
            R.id.sign_in -> {
                Toast.makeText(this, "Sign in clicked", Toast.LENGTH_SHORT).show()
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
