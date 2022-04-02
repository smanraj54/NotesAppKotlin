package com.example.mynotes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.datamodels.NoteInfo

class NotesRecyclerAdapter(private val clickListener: (NoteInfo) -> Unit) : RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder>() {

    private var notes = emptyList<NoteInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.notes_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note, position, clickListener)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private val noteTitle = itemView?.findViewById<TextView>(R.id.noteTitleTextView)
        private val noteBody = itemView?.findViewById<TextView>(R.id.noteBodyTextView)
        private var notePosition = 0;

        fun bind(note: NoteInfo, position: Int, clickListener: (NoteInfo) -> Unit) {
            noteTitle?.text = note.title
            noteBody?.text = note.body
            notePosition = position
            itemView.setOnClickListener { clickListener(note) }
        }
    }

    fun setNotes(notes : List<NoteInfo>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNoteAtPosition(position: Int): NoteInfo {
        return notes[position]
    }

}