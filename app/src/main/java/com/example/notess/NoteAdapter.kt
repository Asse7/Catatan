package com.example.notess

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    private val context: Context,
    private var notes: MutableList<Note>, // ubah menjadi mutable
    private val db: DatabaseHelper
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cbStatus: CheckBox = view.findViewById(R.id.cbStatus)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvContent: TextView = view.findViewById(R.id.tvContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]

        holder.tvTitle.text = note.title
        holder.tvContent.text = note.content
        holder.cbStatus.isChecked = note.status == 1

        // Update status selesai/belum
        holder.cbStatus.setOnCheckedChangeListener { _, isChecked ->
            db.updateStatus(note.id, if (isChecked) 1 else 0)
        }

        // Klik untuk edit
        holder.itemView.setOnClickListener {
            val intent = Intent(context, EditNoteActivity::class.java)
            intent.putExtra("id", note.id)
            intent.putExtra("title", note.title)
            intent.putExtra("content", note.content)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = notes.size

    // 🔹 Fungsi untuk update data
    fun updateNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }
}
