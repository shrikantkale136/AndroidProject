package com.cis.project.views.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cis.project.R
import com.cis.project.views.authentication.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()
        Log.i("CurrentUser: "," - "+auth.currentUser?.email)
        val currentUser=auth.currentUser?.email

        // retrieve user email from session using sharedPreference
        val sharedPref = getSharedPreferences("userData", MODE_PRIVATE)
        val sessionData = sharedPref.edit()
        sessionData.putString("email", currentUser).commit()

        //logout
        logoutButton.setOnClickListener() {
            sessionData.clear().commit()
            auth.signOut()
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(this,"Logout Successfully", Toast.LENGTH_SHORT)
        }

        var email=intent.getStringExtra("email")
        if(email!=null)
        {
            setText(email)
        }
        else{
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun setText(email:String?)
    {
        db= FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener {
                        tasks->
                        var name = userName.text.toString().plus(tasks.get("Name").toString())
                        userName.text = "Welcome $name"
                }
        }

    }
}