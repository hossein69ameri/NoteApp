package com.example.noteappclean_github.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noteappclean_github.R
import com.example.noteappclean_github.databinding.ActivityMainBinding
import com.example.noteappclean_github.presentation.note.NoteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
        }
    }
}