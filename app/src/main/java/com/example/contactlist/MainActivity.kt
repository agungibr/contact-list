package com.example.contactlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.contactlist.activities.ContactDetailsActivity
import com.example.contactlist.databinding.ActivityMainBinding
import com.example.contactlist.models.Contact
import com.example.contactlist.models.ContactAdapter
import com.example.contactlist.models.ContactDatabase
import com.example.contactlist.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: ContactDatabase
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(
            applicationContext, ContactDatabase::class.java,
            "contact_database"
        ).allowMainThreadQueries().build()

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getContacts(database)

        contactAdapter = ContactAdapter(listOf<Contact>()) {
            val intent = Intent(this@MainActivity, ContactDetailsActivity::class.java)
            intent.run {
                putExtra("id", it.id)
                putExtra("name", it.name)
                putExtra("number", it.number)
            }
            startActivity(intent)
        }
        binding.contactsRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = contactAdapter
        }

        viewModel.contactsLiveData.observe(this,{ contacts ->
            contactAdapter.contacts=contacts
            contactAdapter.notifyDataSetChanged()

        })
        binding.saveButton.setOnClickListener(){
            val name = binding.contactName.text.toString()
            val number = binding.contactNumber.text.toString()
            saveContact(name,number)
        }

    }
    private fun saveContact(name: String, number: String) {
        val contact = Contact(id = 0, name, number)
        viewModel.addContact(database,contact)
    }
}
