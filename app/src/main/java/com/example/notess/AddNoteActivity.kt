package com.example.notess

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class AddNoteActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var etJudul: EditText
    private lateinit var etIsi: EditText
    private lateinit var btnSimpan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        // Inisialisasi database
        db = DatabaseHelper(this)

        // Hubungkan ke view
        etJudul = findViewById(R.id.etJudul)
        etIsi = findViewById(R.id.etIsi)
        btnSimpan = findViewById(R.id.btnSimpan)

        // Event klik tombol simpan
        btnSimpan.setOnClickListener {
            val judul = etJudul.text.toString().trim()
            val isi = etIsi.text.toString().trim()

            if (judul.isEmpty() || isi.isEmpty()) {
                Toast.makeText(this, "Judul dan isi wajib diisi!", Toast.LENGTH_SHORT).show()
            } else {
                val result = db.insertNote(judul, isi)
                if (result) {
                    Toast.makeText(this, "Catatan berhasil disimpan", Toast.LENGTH_SHORT).show()
                    finish() // kembali ke halaman utama
                } else {
                    Toast.makeText(this, "Gagal menyimpan catatan", Toast.LENGTH_SHORT).show()
                }

                val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
                toolbar.setNavigationOnClickListener {
                    onBackPressed() // kembali ke halaman sebelumnya
                }

            }
        }
    }
}