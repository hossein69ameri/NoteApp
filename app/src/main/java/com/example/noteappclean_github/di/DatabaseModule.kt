package com.example.noteappclean_github.di


import android.content.Context
import androidx.room.Room
import com.example.noteappclean_github.data.db.NoteDatabase
import com.example.noteappclean_github.data.repository.MainRepositoryImpl
import com.example.noteappclean_github.data.repository.NoteRepositoryImpl
import com.example.noteappclean_github.domain.entity.NoteEntity
import com.example.noteappclean_github.domain.repository.MainRepository
import com.example.noteappclean_github.domain.repository.NoteRepository
import com.example.noteappclean_github.domain.usecase.*
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


    @Provides
    @Singleton
    fun provideMainRepository(noteDataBase: NoteDatabase):MainRepository{
        return MainRepositoryImpl(noteDataBase.noteDao())
    }

    @Provides
    @Singleton
    fun provideGetAllUseCase(mainRepository: MainRepository): AllNoteUseCase{
        return AllNoteUseCase(mainRepository)
    }

    @Provides
    @Singleton
    fun provideSearchUseCase(mainRepository: MainRepository): SearchUseCase{
        return SearchUseCase(mainRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteUseCase(mainRepository: MainRepository): DeleteUseCase{
        return DeleteUseCase(mainRepository)
    }
}