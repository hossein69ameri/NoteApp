package com.example.noteappclean_github.presentation.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteappclean_github.R
import com.example.noteappclean_github.databinding.FragmentHomeBinding
import com.example.noteappclean_github.domain.entity.NoteEntity
import com.example.noteappclean_github.presentation.note.NoteFragment
import com.example.noteappclean_github.util.BUNDLE_ID
import com.example.noteappclean_github.util.DELETE
import com.example.noteappclean_github.util.EDIT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
 private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var notesAdapter: NoteAdapter

    @Inject
    lateinit var noteEntity: NoteEntity

    //Other
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.notesToolbar)
        //InitViews
        binding.apply {
            //Note fragment
            addNoteBtn.setOnClickListener {
                NoteFragment().show(parentFragmentManager, NoteFragment().tag)
            }
            //Get data
            viewModel.getAll()
            lifecycleScope.launchWhenCreated {
                viewModel.getAllNotes.collectLatest {
                    if (it != null) {
                        showEmpty(it.isEmpty)
                    }
                    if (it != null) {
                        it.data?.let { itData ->
                            notesAdapter.setData(itData)
                        }
                    }
                    noteList.apply {
                        layoutManager =
                            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        adapter = notesAdapter
                    }
                }
            }
        }

        //Clicks
        notesAdapter.setOnItemClickListener { entity, type ->
            when (type) {
                EDIT -> {
                    val noteFragment = NoteFragment()
                    val bundle = Bundle()
                    bundle.putInt(BUNDLE_ID, entity.id)
                    noteFragment.arguments = bundle
                    noteFragment.show(parentFragmentManager, NoteFragment().tag)
                }
                DELETE -> {
                    noteEntity.id = entity.id
                    noteEntity.title = entity.title
                    noteEntity.desc = entity.desc
                    noteEntity.category = entity.category
                    noteEntity.priority = entity.priority
                    viewModel.deleteNote(noteEntity)
                }
            }
        }



    }

    private fun showEmpty(isShown: Boolean) {
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


    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
        val search = menu.findItem(R.id.actionSearch)
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchNote(newText)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}