package com.example.notess

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBack = findViewById(R.id.btnBack)
        recyclerView = findViewById(R.id.recyclerViewNotes)
        fabAdd = findViewById(R.id.fabAdd)
        db = DatabaseHelper(this)

        // 🔹 setup RecyclerView & Adapter sekali saja
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter(this, mutableListOf(), db)
        recyclerView.adapter = adapter

        // 🔹 tombol tambah note
        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

        // 🔹 tombol kembali
        btnBack.setOnClickListener {
            finish()
        }

        // 🔹 load data awal
        loadNotes()
    }

    override fun onResume() {
        super.onResume()
        loadNotes() // refresh data saat kembali ke activity
    }

    private fun loadNotes() {
        val notes = db.getAllNotes()
        adapter.updateNotes(notes) // panggil fungsi update di adapter
    }
}
