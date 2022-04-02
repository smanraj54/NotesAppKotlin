package com.example.mynotes.persistence.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynotes.datamodels.NoteInfo
import com.example.mynotes.persistence.dao.NoteDAO

@Database(entities = [NoteInfo::class], version = 2)
abstract class MyNotesDatabase : RoomDatabase() {

    abstract fun noteDAO() : NoteDAO

    // Singleton design pattern - Build the database, and returns it back to the ViewModel
    companion object {
        @Volatile
        private var INSTANCE : MyNotesDatabase? = null

        fun getDatabase(context: Context) : MyNotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, MyNotesDatabase::class.java, "mynotes_database").build()
                INSTANCE = instance
                instance
            }
        }

    }

}