package com.example.contactlist

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_main)
        auth = FirebaseAuth.getInstance()

        val signInBtn: Button = findViewById(R.id.signin_btn)
        val email1: EditText = findViewById(R.id.email1)
        val password1: EditText = findViewById(R.id.password1)
        signInBtn.setOnClickListener {
            val email: String = email1.text.toString().trim()
            val password: String = password1.text.toString().trim()

            if (email.isEmpty()) {
                email1.error = "Email harus diisi"
                email1.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                email1.error = "Email tidak valid"
                email1.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() && password.length < 8) {
                password1.error = "Password harus lebih dari 8 karakter"
                password1.requestFocus()
                return@setOnClickListener
            }
            loginUser(email, password)
        }

        val signinClick: TextView = findViewById(R.id.signup_click)
        signinClick.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Intent(this@SignInActivity, MainActivity::class.java).also { intent ->
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            Intent(this@SignInActivity, MainActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}




