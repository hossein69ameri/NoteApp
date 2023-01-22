package com.example.noteappclean_github.domain.repository

import com.example.noteappclean_github.domain.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun saveNote(noteEntity: NoteEntity)
    suspend fun updateNote(noteEntity: NoteEntity)
     fun detailNote(id : Int) : Flow<NoteEntity>

}