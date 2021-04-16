package com.example.gendinghotelmanagement

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gendinghotelmanagement.Model.UserModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    var btnSignUp: Button? = null
    var txtStaffID: EditText? = null
    var txtPassword: EditText? = null
    var txtConPassword: EditText? = null
    var staffRole: EditText? = null
    //    private lateinit var txtStaffID:EditText
//    private lateinit var txtPassword:EditText
//    private lateinit var txtConPassword:EditText
//    private lateinit var staffRole:RadioGroup
    private lateinit var mAuth:FirebaseAuth
    private lateinit var refUsers:DatabaseReference
    private var firebaseUserID:String = ""
    lateinit var databaseUser: DatabaseReference
    lateinit var signin: TextView



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

        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val signin = findViewById<TextView>(R.id.signin)

        mAuth = FirebaseAuth.getInstance()
        signin.setOnClickListener(View.OnClickListener { startActivity(Intent(this@SignUp, Login::class.java)) })
        btnSignUp.setOnClickListener{
            signUpUser()
            saveUser()

        }
    }

    private fun signUpUser(){
        //val email:String = txtStaffID?.text.toString();
        val txtStaffID = findViewById<TextView>(R.id.txtStaffID)
        val txtPassword = findViewById<TextView>(R.id.txtPassword)
        val txtConPassword = findViewById<TextView>(R.id.txtConPassword)
        val staffRole = findViewById<TextView>(R.id.staffRole)
        val email = txtStaffID?.getText().toString().trim();
        val pwd = txtPassword?.getText().toString().trim();
        val conPwd = txtConPassword?.getText().toString().trim();
        val role = staffRole?.getText().toString().trim();



        if(email.equals("")){
            Toast.makeText(this@SignUp, "Email is required!", Toast.LENGTH_LONG)
                    .show()

        }else if (pwd.equals("")){
            Toast.makeText(this@SignUp, "pwd is required!", Toast.LENGTH_LONG)
                    .show()

        }else if (conPwd.equals("")){
            Toast.makeText(this@SignUp, "con pwd is required!", Toast.LENGTH_LONG)
                    .show()

        }else if (role.equals("")){
            Toast.makeText(this@SignUp, "role is required!", Toast.LENGTH_LONG)
                    .show()

        }else{
            mAuth.createUserWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener{task ->
                        if (task.isSuccessful){

                            firebaseUserID = mAuth.currentUser!!.uid
                            refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)
                            val userHashMap = HashMap<String,Any>()
                            userHashMap["uid"] = firebaseUserID
                            userHashMap["email"] = txtStaffID
                            userHashMap["pwd"] = txtPassword
                            userHashMap["conPwd"] = txtConPassword
                            userHashMap["role"] = staffRole

                            refUsers.updateChildren(userHashMap)
                                    .addOnCompleteListener{
                                        if (task.isSuccessful){
                                            val intent = Intent (this@SignUp, Login::class.java)
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                            startActivity(intent)
                                            finish()
                                        }

                                    }

                        }else{
                            Toast.makeText(this@SignUp, "Error Message:"+ task.exception!!.message.toString(), Toast.LENGTH_LONG)
                                    .show()
                        }
                    }

        }


    }
    //use for saving data in realtime database
    private fun saveUser(){
        val txtStaffID = findViewById<TextView>(R.id.txtStaffID)
        val txtPassword = findViewById<TextView>(R.id.txtPassword)
        val txtConPassword = findViewById<TextView>(R.id.txtConPassword)
        val staffRole = findViewById<TextView>(R.id.staffRole)
        val email = txtStaffID.text.toString().trim();
        val password = txtPassword.text.toString().trim();
        val conPassword = txtConPassword.text.toString().trim();
        val role = staffRole.text.toString().trim();

        //firebaseUserID.trim()
//        firebaseUserID = mAuth.currentUser!!.uid
//        val currentFirebaseUser = firebaseUserID
        //firebaseUserID = uid
        if(email.isEmpty()){

            txtStaffID.error ="Please enter an email"
            return

        }
        //val currentFirebaseUser = mAuth.getCurrentUser().getUid();

        databaseUser = FirebaseDatabase.getInstance().getReference("User");

        val userID = databaseUser.push().key

        val user = UserModel(conPassword, email, password, role, userID)
        if (userID != null) {
            databaseUser.child(userID).setValue(user).addOnCompleteListener{
                Toast.makeText(this@SignUp,"Account is successfully created",Toast.LENGTH_LONG).show()
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
