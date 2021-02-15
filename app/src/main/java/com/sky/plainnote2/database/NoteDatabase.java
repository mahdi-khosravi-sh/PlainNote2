package com.sky.plainnote2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.sky.plainnote2.database.entities.NoteEntity;
import com.sky.plainnote2.database.type_converter.DateConverter;
import com.sky.plainnote2.database.type_converter.PasswordConverter;

@Database(entities = {NoteEntity.class}, version = 1)
@TypeConverters({DateConverter.class, PasswordConverter.class})
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    private static final String DATABASE_NAME = "noteDatabase.db";
    private static final Object LOCK = new Object();

    public static NoteDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context, NoteDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        return instance;
    }

    public abstract NoteDao getNoteDao();
}