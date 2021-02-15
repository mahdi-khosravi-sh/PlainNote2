package com.sky.plainnote2.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.sky.plainnote2.database.entities.NoteEntity;
import com.sky.plainnote2.utility.SampleData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {
    private static NoteRepository instance;
    private final NoteDatabase mDb;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    public LiveData<List<NoteEntity>> mLiveNotes;

    public NoteRepository(Context context) {
        mDb = NoteDatabase.getInstance(context);
        mLiveNotes = getAll();
    }

    public static NoteRepository getInstance(Context context) {
        if (instance == null) {
            instance = new NoteRepository(context);
        }
        return instance;
    }

    public LiveData<List<NoteEntity>> getAll() {
        return mDb.getNoteDao().getAll();
    }

    public void addSampleData() {
        executor.execute(() -> mDb.getNoteDao().insertAll(SampleData.getNotes()));
    }

    public void deleteAll() {
        executor.execute(() -> mDb.getNoteDao().deleteAll());
    }

    public NoteEntity findNoteById(int id) {
        return mDb.getNoteDao().findNoteById(id);
    }

    public void deleteNote(NoteEntity note) {
        executor.execute(() -> mDb.getNoteDao().delete(note));
    }

    public void saveNote(NoteEntity noteEntity) {
        executor.execute(() -> mDb.getNoteDao().insert(noteEntity));
    }
}
