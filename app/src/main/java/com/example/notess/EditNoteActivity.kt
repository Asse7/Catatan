package com.example.notess

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class EditNoteActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private var noteId: Int = 0
    private lateinit var etJudul: EditText
    private lateinit var etIsi: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        db = DatabaseHelper(this)

        etJudul = findViewById(R.id.etEditJudul)
        etIsi = findViewById(R.id.etEditIsi)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

        // Ambil data dari intent
        noteId = intent.getIntExtra("id", 0)
        etJudul.setText(intent.getStringExtra("title"))
        etIsi.setText(intent.getStringExtra("content"))

        btnUpdate.setOnClickListener {
            val judul = etJudul.text.toString()
            val isi = etIsi.text.toString()
            if (db.updateNote(noteId, judul, isi)) {
                Toast.makeText(this, "Catatan diperbarui", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnDelete.setOnClickListener {
            if (db.deleteNote(noteId)) {
                Toast.makeText(this, "Catatan dihapus", Toast.LENGTH_SHORT).show()
                finish()
            }
            val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                onBackPressed() // kembali ke halaman sebelumnya
            }

        }
    }
}