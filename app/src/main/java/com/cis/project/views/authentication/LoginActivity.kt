package com.cis.project.views.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cis.project.MainActivity
import com.cis.project.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener(){
            if(validateLogin()){
                var email = inputEmail.text.toString()
                var password = inputPassword.text.toString()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){
                            task ->
                        if (task.isSuccessful){
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("email",email)
                            startActivity(intent)
                            Toast.makeText(this,"Login SuccessFul !", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this,"Invalid user details", Toast.LENGTH_SHORT).show()
                        }
                    }

            }else{
                Toast.makeText(this.applicationContext, "Please enter user details", Toast.LENGTH_SHORT).show()
            }
        }



        regBtn.setOnClickListener(){
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }


    }

    private fun validateLogin():Boolean {
        if(inputEmail.text.toString().trim{it<=' '}.isNotEmpty() &&
            inputPassword.text.toString().trim{it<= ' '}.isNotEmpty()) {
            return true
        }
        return false
    }
}