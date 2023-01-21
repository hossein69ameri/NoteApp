package com.example.noteappclean_github.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteappclean_github.data.db.NoteDao
import com.example.noteappclean_github.domain.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}