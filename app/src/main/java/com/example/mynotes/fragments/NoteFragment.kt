package com.example.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mynotes.R
import com.example.mynotes.datamodels.NoteInfo
import com.example.mynotes.viewmodels.NotesViewModel
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.android.synthetic.main.notes_list_item.*

class NoteFragment : Fragment() {

    private val notesViewModel: NotesViewModel by activityViewModels()
    private val noteIDArgument: EditNoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolBar = view.findViewById<Toolbar>(R.id.topAppBarNoteFragment)

        toolBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_notesFragment)
        }

        toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit_note_icon -> {
                    val action =
                        NoteFragmentDirections.actionNoteFragmentToEditNoteFragment(noteIDArgument.noteID)
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_noteFragment_to_notesFragment)
                }
            })

        notesViewModel.getNote(noteIDArgument.noteID)
            .observe(viewLifecycleOwner, Observer { note -> note?.let { setNoteFieldValues(it) } })
    }

    private fun setNoteFieldValues(note: NoteInfo) {
        noteFragmentTitleTextView.text = note.title
        noteFragmentBodyTextView.text = note.body
    }

}