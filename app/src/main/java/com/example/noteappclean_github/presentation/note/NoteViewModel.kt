package com.example.noteappclean_github.presentation.note


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappclean_github.domain.entity.NoteEntity
import com.example.noteappclean_github.domain.usecase.DetailUseCase
import com.example.noteappclean_github.domain.usecase.SaveUseCase
import com.example.noteappclean_github.domain.usecase.UpdateUseCase
import com.example.noteappclean_github.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val saveUseCase: SaveUseCase,
    private val updateUseCase: UpdateUseCase,
    private val detailUseCase: DetailUseCase
) : ViewModel() {
    //Spinners
    val categoriesList = MutableLiveData<MutableList<String>>()
    val prioritiesList = MutableLiveData<MutableList<String>>()
    val detailNote = MutableLiveData<DataStatus<NoteEntity>>()

    fun loadCategoriesData() = viewModelScope.launch(Dispatchers.IO) {
        val data = mutableListOf(WORK, EDUCATION, HOME, HEALTH)
        categoriesList.postValue(data)
    }

    fun loadPrioritiesData() = viewModelScope.launch(Dispatchers.IO) {
        val data = mutableListOf(HIGH, NORMAL, LOW)
        prioritiesList.postValue(data)
    }

    fun saveOrEditNote(isEdit : Boolean ,entity: NoteEntity) = viewModelScope.launch {
        if (isEdit){
            updateUseCase.updateNote(entity)
        }else{
            saveUseCase.saveNote(entity)
        }
    }

    fun getDetail(id : Int) = viewModelScope.launch {
        detailUseCase.detailNote(id).collect{
            detailNote.postValue(DataStatus.success(it,false))
        }
    }

}