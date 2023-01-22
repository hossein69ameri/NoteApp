package com.example.noteappclean_github.presentation.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.noteappclean_github.R
import com.example.noteappclean_github.databinding.FragmentNoteBinding
import com.example.noteappclean_github.domain.entity.NoteEntity
import com.example.noteappclean_github.util.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment() {
        private lateinit var binding: FragmentNoteBinding
    @Inject
    lateinit var entity: NoteEntity

    //Other
    private val viewModel: NoteViewModel by viewModels()
    private var category = ""
    private var priority = ""
    private var noteId = 0
    private var type = ""
    private var isEdit = false
    private val categoriesList: MutableList<String> = mutableListOf()
    private val prioriesList: MutableList<String> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Bundle
        noteId = arguments?.getInt(BUNDLE_ID) ?: 0
        //Type
        if (noteId > 0) {
            type = EDIT
            isEdit = true
        } else {
            isEdit = false
            type = NEW
        }
        //InitViews
        binding.apply {
            //Close
            closeImg.setOnClickListener { dismiss() }
            //Spinner Category
            viewModel.loadCategoriesData()
            viewModel.categoriesList.observe(viewLifecycleOwner) {
                categoriesList.addAll(it)
                categoriesSpinner.setupListWithAdapter(it) { itItem ->
                    category = itItem
                }
            }
            //Spinner priority
            viewModel.loadPrioritiesData()
            viewModel.prioritiesList.observe(viewLifecycleOwner) {
                prioriesList.addAll(it)
                prioritySpinner.setupListWithAdapter(it) { itItem -> priority = itItem }
            }
            if (type == EDIT) {
                viewModel.getDetail(noteId)
                lifecycleScope.launchWhenCreated {
                    viewModel.detailNote.collectLatest {
                        if (it != null) {
                            it.data?.let { itData ->
                                titleEdt.setText(itData.title)
                                descEdt.setText(itData.desc)
                                categoriesSpinner.setSelection(categoriesList.getIndexFromList(itData.category))
                                prioritySpinner.setSelection(prioriesList.getIndexFromList(itData.priority))
                            }
                        }
                    }
                }
            }
            //Click
            saveNote.setOnClickListener {
                val title = titleEdt.text.toString()
                val desc = descEdt.text.toString()
                entity.id = noteId
                entity.title = title
                entity.desc = desc
                entity.category = category
                entity.priority = priority

                if (title.isNotEmpty() && desc.isNotEmpty()) {
                    viewModel.saveOrEditNote(isEdit, entity)
                }

                dismiss()
            }
        }
    }
}