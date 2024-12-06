package com.example.yb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth



class LogIn : Base() {
    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var signinButton: Button? = null
    private var signupButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in)
        inputEmail = findViewById(R.id.email)
        inputPassword = findViewById(R.id.password)
        signinButton = findViewById(R.id.loginButton)
        signupButton = findViewById(R.id.signUpButton)

        signinButton?.setOnClickListener {
            signIn()
        }
        signupButton?.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }

    }
    private fun validate(): Boolean {
        val email = inputEmail?.text.toString().trim { it <= ' ' }
        val password = inputPassword?.text.toString().trim { it <= ' ' }

        return when{
            email.isEmpty() -> {
                showErrorSnackBar("Please enter an email address", true)
                false
            }
            password.isEmpty() -> {
                showErrorSnackBar("Please enter a password", true)
                false
            }
            else -> {
                true
            }
        }

    }
    private fun signIn(){
        if(validate()){
            val email = inputEmail?.text.toString().trim { it <= ' ' }
            val password = inputPassword?.text.toString().trim { it <= ' ' }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showErrorSnackBar("You are logged in successfully", false)
                    } else {
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }

    }
//    open fun goToMainActivity(){
//        val intent = Intent(this, MainActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//        finish()
//    }
}