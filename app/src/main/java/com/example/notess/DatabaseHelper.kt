package com.example.notess

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "todo.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "notes"
        private const val COL_ID = "id"
        private const val COL_TITLE = "title"
        private const val COL_CONTENT = "content"
        private const val COL_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_TITLE TEXT, " +
                "$COL_CONTENT TEXT, " +
                "$COL_STATUS INTEGER)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insert
    fun insertNote(title: String, content: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_TITLE, title)
        values.put(COL_CONTENT, content)
        values.put(COL_STATUS, 0) // default belum selesai
        val result = db.insert(TABLE_NAME, null, values)
        return result != -1L
    }

    // Update
    fun updateNote(id: Int, title: String, content: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_TITLE, title)
        values.put(COL_CONTENT, content)
        val result = db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(id.toString()))
        return result > 0
    }

    // Update status (selesai/belum)
    fun updateStatus(id: Int, status: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_STATUS, status)
        val result = db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(id.toString()))
        return result > 0
    }

    // Delete
    fun deleteNote(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(id.toString()))
        return result > 0
    }

    // Get semua catatan
    fun getAllNotes(): ArrayList<Note> {
        val list = ArrayList<Note>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val note = Note(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTENT)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_STATUS))
                )
                list.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}