package com.example.contactlist.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDAO {
    @Query("SELECT * FROM contact ")
    fun getAllContacts():List<Contact>

    @Query("SELECT * FROM contact WHERE id = :id")
    fun getContactByID(id:Int):Contact

    @Insert
    fun addContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

}