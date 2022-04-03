package com.example.mynotes.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.adapters.NotesRecyclerAdapter
import com.example.mynotes.datamodels.NoteInfo
import com.example.mynotes.gestures.SwipeNoteCallBack
import com.example.mynotes.viewmodels.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesFragment : Fragment() {

    private val notesViewModel: NotesViewModel by activityViewModels()
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesListRecyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = NotesRecyclerAdapter { note: NoteInfo -> handleNoteClick(note) }
        notesListRecyclerView.adapter = adapter

        notesViewModel.notes.observe(
            viewLifecycleOwner,
            Observer { notes -> notes?.let { adapter.setNotes(it) } })

        view.findViewById<FloatingActionButton>(R.id.floating_action_button).setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_createNoteFragment)
        }

        val swipeNoteCallBack = object : SwipeNoteCallBack(mContext, notesListRecyclerView) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //Changed action from LEFT to RIGHT touch and the background process is edit
                if (direction == ItemTouchHelper.RIGHT) {
                    val note = adapter.getNoteAtPosition(viewHolder.adapterPosition)
                    val action =
                        NotesFragmentDirections.actionNotesFragmentToEditNoteFragment(note.id)
                    findNavController().navigate(action)
                }
                //changed action from right to left touch and the background process is delete
                else if (direction == ItemTouchHelper.LEFT) {
                    val note = adapter.getNoteAtPosition(viewHolder.adapterPosition)
                    notesViewModel.deleteNote(note)
                    val snack = Snackbar.make(
                        view,
                        note.title + " deleted successfully.",
                        Snackbar.LENGTH_LONG
                    )
                    snack.show()
                }
            }
        }

        itemTouchHelper = ItemTouchHelper(swipeNoteCallBack)
        itemTouchHelper.attachToRecyclerView(notesListRecyclerView)
    }

    private fun handleNoteClick(note: NoteInfo) {
        val noteID = note.id
        val action = NotesFragmentDirections.actionNotesFragmentToNoteFragment(noteID)
        findNavController().navigate(action)
    }

}