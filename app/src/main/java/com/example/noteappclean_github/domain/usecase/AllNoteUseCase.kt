package com.example.noteappclean_github.domain.usecase

import com.example.noteappclean_github.domain.repository.MainRepository
import javax.inject.Inject

class AllNoteUseCase @Inject constructor(private val mainRepository: MainRepository) {

    fun getAllNote() = mainRepository.getAllNote()
}