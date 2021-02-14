package com.sky.plainnote2.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insertAll(List<NoteEntity> notes);

    @Insert
    void insertAll(NoteEntity... notes);

    @Insert
    void insert(NoteEntity note);

    @Delete
    void delete(NoteEntity note);

    @Query("DELETE FROM tbl_notes")
    void deleteAll();

    @Query("SELECT * FROM tbl_notes ORDER BY name")
    List<NoteEntity> getAll();

    @Query("SELECT * FROM tbl_notes WHERE id=:id")
    NoteEntity findNoteById(int id);

    @Query("SELECT COUNT(*) FROM tbl_notes")
    int getCount();
}