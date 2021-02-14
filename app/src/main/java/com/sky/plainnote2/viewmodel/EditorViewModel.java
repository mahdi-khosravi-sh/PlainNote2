package com.sky.plainnote2.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.sky.plainnote2.database.NoteEntity;
import com.sky.plainnote2.database.NoteRepository;

import java.text.DateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {
    public MutableLiveData<NoteEntity> mLiveNote = new MutableLiveData<>();
    private final NoteRepository mRepo;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepo = NoteRepository.getInstance(application);
    }

    public void loadData(int id) {
        executor.execute(() -> {
            NoteEntity noteEntity = mRepo.findNoteById(id);
            mLiveNote.postValue(noteEntity);
        });
    }

    public void delete() {
        mRepo.deleteNote(mLiveNote.getValue());
    }

    public boolean saveNote(String name, String text) {
        if (TextUtils.isEmpty(text.trim())) {
            return false;
        }

        NoteEntity mNote = mLiveNote.getValue();
        if (mNote == null) {
            mNote = new NoteEntity();
        }
        mNote.setDateToCurrent(System.currentTimeMillis());
        if (TextUtils.isEmpty(name)) {

            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
            name = "Note - " + dateFormat.format(mNote.getDate());
        }

        mNote.setName(name);
        mNote.setText(text);

        mRepo.saveNote(mNote);
        return true;
    }
}
