package com.example.noteappclean_github.domain.usecase

import com.example.noteappclean_github.domain.entity.NoteEntity
import com.example.noteappclean_github.domain.repository.NoteRepository
import javax.inject.Inject

class SaveUseCase @Inject constructor(private val noteRepository: NoteRepository) {

    suspend fun saveNote(noteEntity: NoteEntity) = noteRepository.saveNote(noteEntity)
}