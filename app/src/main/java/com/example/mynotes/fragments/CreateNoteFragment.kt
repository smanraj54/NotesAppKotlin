package com.example.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mynotes.R
import com.example.mynotes.datamodels.NoteInfo
import com.example.mynotes.viewmodels.NotesViewModel
import kotlinx.android.synthetic.main.fragment_create_note.*
import org.w3c.dom.Text

class CreateNoteFragment : Fragment() {

    private val notesViewModel : NotesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolBar = view.findViewById<Toolbar>(R.id.topAppBarCreateNoteFragment)

        toolBar.setNavigationOnClickListener {
            saveNote(noteTitleInputField, noteBodyInputField)
            findNavController().navigate(R.id.action_createNoteFragment_to_notesFragment)
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                saveNote(noteTitleInputField, noteBodyInputField)
                findNavController().navigate(R.id.action_createNoteFragment_to_notesFragment)
            }
        })
    }

    private fun saveNote(noteTitleInputField : TextView, noteBodyInputField : TextView) {
        val noteTitle : String = noteTitleInputField.text.toString()
        val noteBody : String = noteBodyInputField.text.toString()

        if (noteTitle.isNotEmpty() && noteBody.isNotEmpty()) {
            val note = NoteInfo(title = noteTitle, body = noteBody)
            notesViewModel.insertNote(note)
        } else {
            return
        }
    }

}