package com.sky.plainnote2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sky.plainnote2.database.NoteEntity;
import com.sky.plainnote2.database.NoteRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    public LiveData<List<NoteEntity>> mLiveNotes;
    private final NoteRepository mRepo;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepo = NoteRepository.getInstance(application);
        mLiveNotes = mRepo.mLiveNotes;
    }

    public void addSampleData() {
        mRepo.addSampleData();
    }

    public void deleteAll() {
        mRepo.deleteAll();
    }

    public void deleteNote(NoteEntity noteEntity) {
        mRepo.deleteNote(noteEntity);
    }
}