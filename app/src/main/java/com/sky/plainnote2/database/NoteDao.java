package com.sky.plainnote2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sky.plainnote2.database.entities.NoteEntity;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NoteEntity> notes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(NoteEntity... notes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteEntity note);

    @Delete
    void delete(NoteEntity note);

    @Query("DELETE FROM tbl_notes")
    void deleteAll();

    @Query("SELECT * FROM tbl_notes ")
    LiveData<List<NoteEntity>> getAll();

    @Query("SELECT * FROM tbl_notes WHERE id=:id")
    NoteEntity findNoteById(int id);

    @Query("SELECT COUNT(*) FROM tbl_notes")
    int getCount();
}