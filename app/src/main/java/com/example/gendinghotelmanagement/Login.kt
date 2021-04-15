package com.example.gendinghotelmanagement

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
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

class Login : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView


    private var registerUser: TextView? = null
    private var forgotPassword: TextView? = null
    var progressDialog: ProgressDialog? = null
    private lateinit var mAuth:FirebaseAuth
//    private lateinit var txtStaffID: EditText
//    private lateinit var txtPassword: EditText
    //private lateinit var registerUser: TextView
    //private lateinit var forgotPassword: TextView
    //lateinit var btnLogin: Button
//    var mAuth:FirebaseAuth? = null
    var btnLogin: Button? = null
    var txtStaffID: Button? = null
    var txtPassword: Button? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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


        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val registerUser = findViewById<TextView>(R.id.registerUser)
        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)

        mAuth = FirebaseAuth.getInstance()

        registerUser.setOnClickListener(View.OnClickListener { startActivity(Intent(this@Login, SignUp::class.java)) })
        forgotPassword.setOnClickListener(View.OnClickListener { startActivity(Intent(this@Login, ForgotPassword::class.java)) })
        btnLogin.setOnClickListener{
            loginUser()
        }


    }


    fun loginUser() {

        val txtStaffID = findViewById<TextView>(R.id.txtStaffID)
        val txtPassword = findViewById<TextView>(R.id.txtNumOfPeople)
        val email:String = txtStaffID?.text.toString();
        val pwd:String = txtPassword?.text.toString();

        if(email.equals("")){
            Toast.makeText(this@Login, "Email is required!", Toast.LENGTH_LONG)
                    .show()

        }else if (pwd.equals("")){
            Toast.makeText(this@Login, "pwd is required!", Toast.LENGTH_LONG)
                    .show()

        }else{
            mAuth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        val intent = Intent (this@Login,ManagerStaffPortal::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("Username",email)
                        startActivity(intent)
                        Toast.makeText(this@Login, "You are successfully login!", Toast.LENGTH_LONG)
                            .show()
                        finish()


                    }else{
                        Toast.makeText(this@Login, "Error Message:"+ task.exception!!.message.toString(), Toast.LENGTH_LONG)
                            .show()
                    }

                }

        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sign_in -> {
                Toast.makeText(this, "Sign in clicked", Toast.LENGTH_SHORT).show()
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
