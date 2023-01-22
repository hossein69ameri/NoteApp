package com.example.noteappclean_github.domain.usecase

import com.example.noteappclean_github.domain.entity.NoteEntity
import com.example.noteappclean_github.domain.repository.MainRepository
import javax.inject.Inject

class DeleteUseCase @Inject constructor(private val mainRepository: MainRepository) {

    suspend fun deleteNote(noteEntity: NoteEntity) = mainRepository.deleteNote(noteEntity)
}