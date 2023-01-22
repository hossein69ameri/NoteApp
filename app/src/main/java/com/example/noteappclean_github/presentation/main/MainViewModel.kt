package com.example.noteappclean_github.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappclean_github.domain.entity.NoteEntity
import com.example.noteappclean_github.domain.usecase.AllNoteUseCase
import com.example.noteappclean_github.domain.usecase.DeleteUseCase
import com.example.noteappclean_github.domain.usecase.PriorityUseCase
import com.example.noteappclean_github.domain.usecase.SearchUseCase
import com.example.noteappclean_github.util.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val allNoteUseCase: AllNoteUseCase,
    private val searchUseCase: SearchUseCase,
    private val priorityUseCase: PriorityUseCase,
    private val deleteUseCase: DeleteUseCase
) : ViewModel() {

    private val _getAllNotes : MutableStateFlow<DataStatus<List<NoteEntity>>?> = MutableStateFlow(null)
    val getAllNotes = _getAllNotes.asStateFlow()

    fun getAll() = viewModelScope.launch {
        allNoteUseCase.getAllNote().collect{
            _getAllNotes.value = DataStatus.success(it,it.isEmpty())
        }
    }

    fun getPriority(priority : String) = viewModelScope.launch {
        priorityUseCase.priorityNote(priority).collect{
            _getAllNotes.value = DataStatus.success(it,it.isEmpty())
        }
    }

    fun searchNote(search : String) = viewModelScope.launch {
        searchUseCase.searchNote(search).collect{
            _getAllNotes.value = DataStatus.success(it,it.isEmpty())
        }
    }

    fun deleteNote(entity: NoteEntity) = viewModelScope.launch {
        deleteUseCase.deleteNote(entity)
    }

}