package com.example.noteappclean_github.domain.repository

import com.example.noteappclean_github.domain.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getAllNote() : Flow<MutableList<NoteEntity>>
    fun searchNotes(search: String) : Flow<MutableList<NoteEntity>>
    suspend fun deleteNote(entity: NoteEntity)

}