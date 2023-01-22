package com.example.noteappclean_github.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteappclean_github.R
import com.example.noteappclean_github.databinding.ActivityMainBinding
import com.example.noteappclean_github.domain.entity.NoteEntity
import com.example.noteappclean_github.presentation.note.NoteFragment
import com.example.noteappclean_github.util.BUNDLE_ID
import com.example.noteappclean_github.util.DELETE
import com.example.noteappclean_github.util.EDIT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var noteAdapter: NoteAdapter

    @Inject
    lateinit var entity: NoteEntity



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            //Support toolbar
            setSupportActionBar(notesToolbar)
            //Note fragment
            addNoteBtn.setOnClickListener {
                NoteFragment().show(supportFragmentManager, NoteFragment().tag)
            }
            //get all note
            viewModel.getAll()
            viewModel.getAllNotes.observe(this@MainActivity) {
                //show empty
                showEmpty(it.isEmpty)
                //show in recycler
                noteAdapter.setData(it.data!!)
                noteList.apply {
                    layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    adapter = noteAdapter
                }
            }

            noteAdapter.setOnItemClickListener { noteEntity, state ->
                when(state){
                    EDIT -> {
                        val noteFragment = NoteFragment()
                        val bundle = Bundle()
                        bundle.putInt(BUNDLE_ID,noteEntity.id)
                        noteFragment.arguments = bundle
                        noteFragment.show(supportFragmentManager,NoteFragment().tag)
                    }
                    DELETE -> {
                        entity.id = noteEntity.id
                        entity.desc = noteEntity.desc
                        entity.title = noteEntity.title
                        entity.priority = noteEntity.priority
                        entity.category = noteEntity.category
                        viewModel.deleteNote(entity)
                    }
                }
            }
        }
    }

    private fun showEmpty(isShown : Boolean){
        binding.apply {
            if (isShown) {
                emptyLay.visibility = View.VISIBLE
                noteList.visibility = View.GONE
            } else {
                emptyLay.visibility = View.GONE
                noteList.visibility = View.VISIBLE
            }
        }
    }
}