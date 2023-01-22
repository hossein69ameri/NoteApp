package com.example.noteappclean_github.domain.usecase

import com.example.noteappclean_github.domain.repository.MainRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val mainRepository: MainRepository) {

    fun searchNote(search : String) = mainRepository.searchNotes(search)
}