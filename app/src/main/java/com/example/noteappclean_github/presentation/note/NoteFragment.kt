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
import com.example.noteappclean_github.util.setupListWithAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment() {
        private lateinit var binding: FragmentNoteBinding
    private val viewModel: NoteViewModel by viewModels()

    @Inject
    lateinit var entity: NoteEntity

    private var category = ""
    private var priority = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //Close
            closeImg.setOnClickListener { dismiss() }
            //Spinner Category
            viewModel.loadCategoriesData()
            viewModel.categoriesList.observe(viewLifecycleOwner) {
                categoriesSpinner.setupListWithAdapter(it) { itItem ->
                    category = itItem
                }
            }
            //Spinner priority
            viewModel.loadPrioritiesData()
            viewModel.prioritiesList.observe(viewLifecycleOwner) {
                prioritySpinner.setupListWithAdapter(it) { itItem -> priority = itItem }
            }

            // click on save
            saveNote.setOnClickListener {
                val title = titleEdt.text.toString()
                val desc = descEdt.text.toString()
                entity.id = 0
                entity.title = title
                entity.desc = desc
                entity.category = category
                entity.priority = priority
                //call save method
                if (title.isNotEmpty() && desc.isNotEmpty()) {
                    viewModel.saveOrEditNote(false, entity)
                }
                // exit
                dismiss()
            }
        }
    }

}