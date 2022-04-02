package com.example.mynotes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotes.datamodels.NoteInfo
import com.example.mynotes.persistence.database.MyNotesDatabase
import com.example.mynotes.persistence.repositories.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository : NoteRepository
    val notes : LiveData<List<NoteInfo>>

    // Initializes notes by fetching them from the NoteRepository through the NoteDAO
    init {
        val notesDAO = MyNotesDatabase.getDatabase(application).noteDAO()
        noteRepository = NoteRepository(notesDAO)
        notes = noteRepository.notes
    }

    fun getNote(noteID: Int): LiveData<NoteInfo> {
        return noteRepository.getNote(noteID)
    }

    // Adds a new note to the data source, i.e. the database, by calling the NoteRepository's insert method.
    // This method in turn calls the NoteDAO's insert method.
    fun insertNote(note : NoteInfo) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.insert(note)
    }

    fun deleteNote(note: NoteInfo) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteNote(note)
    }

    fun updateNote(note: NoteInfo) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.updateNote(note)
    }

}