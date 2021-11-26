package com.example.contactlist.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ContactDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityContactDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run{
            contactID.text=intent.getIntExtra("id",0).toString()
            contactDisplay.text=intent.getStringExtra("name")
            phoneNumberDisplay.text=intent.getStringExtra("number")

        }
    }
}
