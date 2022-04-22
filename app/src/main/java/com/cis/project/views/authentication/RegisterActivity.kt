package com.cis.project.views.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.cis.project.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        registeruserBtn.setOnClickListener {
            var uemail = Email.text.toString()
            var upassword = Password.text.toString()
            createAccout(uemail, upassword)
        }

        LoginButton.setOnClickListener(){
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createAccout(email: String, password: String) {
        if (validateUserDetails()) {
            var name = Name.text.toString()
            var email = Email.text.toString()
            var phone = PhoneNumber.text.toString()

            val user = hashMapOf(
                "Name" to name,
                "Email" to email,
                "Phone" to phone

            )

            val users = db.collection("USERS")
            val query =users.whereEqualTo("Email",email).get()
                .addOnSuccessListener {
                        task->
                    if(task.isEmpty)
                    {
                        auth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(this){
                                    task->
                                if(task.isSuccessful)
                                {
                                    Log.d("SUCCESS", "User created successfully !")
                                    Toast.makeText(this, "Register Successful !!", Toast.LENGTH_SHORT).show()
                                    users.document(email).set(user)
                                    val intent= Intent(this,LoginActivity::class.java)
                                    intent.putExtra("email",email)
                                    startActivity(intent)
                                    finish()
                                }
                                else
                                {
                                    Toast.makeText(this,"Authentication Failed", Toast.LENGTH_LONG).show()
                                }
                            }
                    }
                    else
                    {
                        Toast.makeText(this,"User Already Registered", Toast.LENGTH_LONG).show()
                        Email.setError("Email id already registered")

                    }
                }
        }
        else{
            Toast.makeText(this,"Enter the Details", Toast.LENGTH_LONG).show()
        }
    }


    private fun validateUserDetails(): Boolean {
        if (Name.text.toString().trim { it <= ' ' }.isNotEmpty() &&
            PhoneNumber.text.toString().trim { it <= ' ' }.isNotEmpty() &&
            Email.text.toString().trim { it <= ' ' }.isNotEmpty() &&
            Password.text.toString().trim { it <= ' ' }.isNotEmpty()
        ) {
            return true
        }
        return false
    }

}