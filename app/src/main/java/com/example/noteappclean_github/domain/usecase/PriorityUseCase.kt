package com.example.noteappclean_github.domain.usecase

import com.example.noteappclean_github.domain.repository.MainRepository
import javax.inject.Inject

class PriorityUseCase @Inject constructor(private val mainRepository: MainRepository) {

    fun priorityNote(priority : String) = mainRepository.priorityNotes(priority)
}