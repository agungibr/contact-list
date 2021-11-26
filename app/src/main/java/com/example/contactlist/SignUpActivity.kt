package com.example.contactlist

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_main)

        auth = FirebaseAuth.getInstance()

        val signUpButton: Button = findViewById(R.id.signUp_btn)
        val emailAddress: EditText = findViewById(R.id.email2)
        val passWord: EditText = findViewById(R.id.password2)
        val passWord2: EditText = findViewById(R.id.password3)

        signUpButton.setOnClickListener {
            val email: String = emailAddress.text.toString().trim()
            val password: String = passWord.text.toString().trim()
            val password2: String = passWord2.text.toString().trim()

            if(email.isEmpty()) {
                emailAddress.error = "Email harus diisi"
                emailAddress.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailAddress.error = "Email tidak valid"
                emailAddress.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty() && password.length < 8) {
                passWord.error = "Password harus lebih dari 8 karakter"
                passWord.requestFocus()
                return@setOnClickListener
            }

            if(password2.isEmpty() && password2.length < 8) {
                passWord2.error = "Konfirmasi ulang password anda"
                passWord2.requestFocus()
                return@setOnClickListener
            }
            registerUser(email, password, password2)

            val signupClick: TextView = findViewById(R.id.signin_click)
            signupClick.setOnClickListener{
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun registerUser(email: String, password: String, password2: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Intent(this@SignUpActivity, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}


