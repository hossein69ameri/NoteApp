package com.example.noteappclean_github.di


import android.content.Context
import androidx.room.Room
import com.example.noteappclean_github.data.db.NoteDatabase
import com.example.noteappclean_github.data.repository.NoteRepositoryImpl
import com.example.noteappclean_github.domain.entity.NoteEntity
import com.example.noteappclean_github.domain.repository.NoteRepository
import com.example.noteappclean_github.domain.usecase.DetailUseCase
import com.example.noteappclean_github.domain.usecase.SaveUseCase
import com.example.noteappclean_github.domain.usecase.UpdateUseCase
import com.example.noteappclean_github.util.NOTE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, NoteDatabase::class.java, NOTE_DATABASE
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: NoteDatabase) = db.noteDao()

    @Provides
    @Singleton
    fun provideEntity() = NoteEntity()

    @Provides
    @Singleton
    fun provideNoteRepository(noteDataBase: NoteDatabase):NoteRepository{
        return NoteRepositoryImpl(noteDataBase.noteDao())
    }

    @Provides
    @Singleton
    fun provideAddUseCase(noteRepository: NoteRepository): SaveUseCase{
        return SaveUseCase(noteRepository)
    }

    @Provides
    @Singleton
    fun provideDetailUseCase(noteRepository: NoteRepository): DetailUseCase{
        return DetailUseCase(noteRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateUseCase(noteRepository: NoteRepository): UpdateUseCase{
        return UpdateUseCase(noteRepository)
    }
}