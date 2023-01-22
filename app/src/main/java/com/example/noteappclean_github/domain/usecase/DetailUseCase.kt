package com.example.noteappclean_github.domain.usecase

import com.example.noteappclean_github.domain.repository.NoteRepository
import javax.inject.Inject

class DetailUseCase @Inject constructor(private val noteRepository: NoteRepository) {

    fun detailNote(id:Int) = noteRepository.detailNote(id)
}