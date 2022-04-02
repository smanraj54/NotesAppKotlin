package com.example.mynotes.persistence.dao

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynotes.datamodels.NoteInfo

@Dao
interface NoteDAO {

    @Query("SELECT * FROM note_table")
    fun getNotes(): LiveData<List<NoteInfo>>

    @Query("SELECT * FROM note_table WHERE id = :noteID LIMIT 1")
    fun getNote(noteID: Int): LiveData<NoteInfo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: NoteInfo)

    @Delete
    suspend fun deleteNote(note: NoteInfo)

    @Update
    suspend fun updateNote(note: NoteInfo)
}