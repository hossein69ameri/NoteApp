package com.example.noteappclean_github.data.repository

import com.example.noteappclean_github.data.db.NoteDao
import com.example.noteappclean_github.domain.entity.NoteEntity
import com.example.noteappclean_github.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : MainRepository {
    override fun getAllNote(): Flow<MutableList<NoteEntity>> {
        return noteDao.getAllNotes()
    }

    override fun searchNotes(search: String): Flow<MutableList<NoteEntity>> {
        return noteDao.searchNote(search)
    }

    override suspend fun deleteNote(entity: NoteEntity) {
        noteDao.deleteNote(entity)
    }
}