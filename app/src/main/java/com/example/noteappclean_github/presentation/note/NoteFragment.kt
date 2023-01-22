package com.example.noteappclean_github.presentation.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.noteappclean_github.R
import com.example.noteappclean_github.databinding.FragmentNoteBinding
import com.example.noteappclean_github.domain.entity.NoteEntity
import com.example.noteappclean_github.util.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment() {
        private lateinit var binding: FragmentNoteBinding
    private val viewModel: NoteViewModel by viewModels()
    private var categoryList: MutableList<String> = mutableListOf()
    private var priorityList: MutableList<String> = mutableListOf()

    @Inject
    lateinit var entity: NoteEntity

    private var category = ""
    private var priority = ""
    private var noteID = 0
    private var type = ""
    private var isEdit = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //bundle
        noteID = arguments?.getInt(BUNDLE_ID) ?: 0
        //type
        if (noteID > 0) {
            type = EDIT
            isEdit = true
        } else {
            type = NEW
            isEdit = false
        }
        binding.apply {
            //Close
            closeImg.setOnClickListener { dismiss() }
            //Spinner Category
            viewModel.loadCategoriesData()
            viewModel.categoriesList.observe(viewLifecycleOwner) {
                categoryList.addAll(it)
                categoriesSpinner.setupListWithAdapter(it) { itItem ->
                    category = itItem
                }
            }
            //Spinner priority
            viewModel.loadPrioritiesData()
            viewModel.prioritiesList.observe(viewLifecycleOwner) {
                priorityList.addAll(it)
                prioritySpinner.setupListWithAdapter(it) { itItem -> priority = itItem }
            }

            //get detail
            if (type == EDIT) {
                viewModel.getDetail(noteID)
                viewModel.detailNote.observe(viewLifecycleOwner) { itdata ->
                    itdata.data?.let { note ->
                        titleEdt.setText(note.title)
                        descEdt.setText(note.desc)
                        categoriesSpinner.setSelection(categoryList.getIndexItem(note.category))
                        prioritySpinner.setSelection(priorityList.getIndexItem(note.priority))
                    }
                }
            }

            // click on save
            saveNote.setOnClickListener {
                val title = titleEdt.text.toString()
                val desc = descEdt.text.toString()
                entity.id = noteID
                entity.title = title
                entity.desc = desc
                entity.category = category
                entity.priority = priority
                //call save method
                if (title.isNotEmpty() && desc.isNotEmpty()) {
                    viewModel.saveOrEditNote(isEdit, entity)
                }
                // exit
                dismiss()
            }
        }
    }

}