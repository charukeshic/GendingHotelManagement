package com.example.gendinghotelmanagement

import android.accounts.NetworkErrorException
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class Login : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    private var txtStaffID: EditText? = null;
    private var txtPassword: EditText? = null;
    private var txtstaffID: String? = null;
    private var txtpassword: String? = null;
    private var btnLogin: Button? = null
    private var registerUser: TextView? = null;
    private var forgotPassword: TextView? = null
    var progressDialog: ProgressDialog? = null
    private var mAuth: FirebaseAuth? = null

    val register = registerUser
    val forgotpwd = forgotPassword
    val loginsubmit = btnLogin

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

        txtStaffID = findViewById<EditText>(R.id.txtStaffID)
        txtPassword = findViewById<EditText>(R.id.txtPassword)
        btnLogin = findViewById<Button>(R.id.btnLogin)
        registerUser = findViewById<TextView>(R.id.registerUser)
        forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        mAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)

        if (register != null) {
            register.setOnClickListener(View.OnClickListener {
                startActivity(
                    Intent(
                        this@Login,
                        SignUp::class.java
                    )
                )
            })
        }

        if (forgotpwd != null) {
            forgotpwd.setOnClickListener(View.OnClickListener {
                startActivity(
                    Intent(
                        this@Login,
                        ForgotPassword::class.java
                    )
                )
            })
        }

        if (loginsubmit != null) {
            loginsubmit.setOnClickListener(View.OnClickListener { LogInFun() })
        }

    }

    fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun LogInFun() {
        progressDialog?.setTitle("Login Account")
        progressDialog?.setMessage("Please wait Signing !!!")
        progressDialog?.show()
        txtstaffID = txtStaffID?.getText().toString()
        txtpassword = txtPassword?.getText().toString()
        if (txtstaffID == "" || !isEmailValid(txtstaffID)) {
            txtStaffID?.setError("Enter the Valid Email")
            progressDialog?.dismiss()
            return
        }
        if (txtpassword == "") {
            txtPassword?.setError("Password Required")
            progressDialog?.dismiss()
            return
        }
        LogInUser(txtstaffID!!, txtpassword!!)
    }

    open fun LogInUser(txtstaffID: String, txtpassword: String) {
        mAuth?.signInWithEmailAndPassword(txtstaffID, txtpassword)
            ?.addOnSuccessListener(OnSuccessListener<AuthResult> { authResult ->
                if (authResult.user != null) {
                    val Key = authResult.user!!.uid
                    startActivity(Intent(this@Login, MainActivity::class.java))
                    finish()
                    progressDialog?.dismiss()
                }
            })?.addOnFailureListener(OnFailureListener { e ->
                if (e is FirebaseAuthInvalidCredentialsException) {
                    txtPassword?.setError("Invalid Password")
                    progressDialog?.dismiss()
                } else if (e is FirebaseAuthEmailException) {
                    txtStaffID?.setError("Invalid Email")
                    progressDialog?.dismiss()
                } else if (e is NetworkErrorException) {
                    Toast.makeText(this@Login, "Network Error", Toast.LENGTH_SHORT).show()
                    progressDialog?.dismiss()
                }
                Toast.makeText(this@Login, "Network Error" + e.message, Toast.LENGTH_SHORT)
                    .show()
                progressDialog?.dismiss()
            })


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
