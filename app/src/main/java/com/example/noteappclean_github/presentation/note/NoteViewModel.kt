package com.example.noteappclean_github.presentation.note


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappclean_github.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor() : ViewModel() {
    //Spinners
    val categoriesList = MutableLiveData<MutableList<String>>()
    val prioritiesList = MutableLiveData<MutableList<String>>()

    fun loadCategoriesData() = viewModelScope.launch(Dispatchers.IO) {
        val data = mutableListOf(WORK, EDUCATION, HOME, HEALTH)
        categoriesList.postValue(data)
    }

    fun loadPrioritiesData() = viewModelScope.launch(Dispatchers.IO) {
        val data = mutableListOf(HIGH, NORMAL, LOW)
        prioritiesList.postValue(data)
    }
}