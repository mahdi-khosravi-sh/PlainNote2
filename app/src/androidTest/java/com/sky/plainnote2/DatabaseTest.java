package com.sky.plainnote2;


import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.sky.plainnote2.database.NoteDatabase;
import com.sky.plainnote2.database.NoteEntity;
import com.sky.plainnote2.utility.SampleData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private static final String TAG = "DatabaseTest";
    private NoteDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        mDb = Room.inMemoryDatabaseBuilder(context, NoteDatabase.class)
                .allowMainThreadQueries()
                .build();
        Log.i(TAG, "createDb");
    }

    @Test
    public void insertSampleData() {
        mDb.getNoteDao().insertAll(SampleData.getNotes());

        Log.i(TAG, "insertSampleData");
        Log.i(TAG, "note count : " + mDb.getNoteDao().getCount());

        List<NoteEntity> notes = mDb.getNoteDao().getAll();
        for (NoteEntity note : notes) {
            Log.i(TAG, note.toString());
        }
    }

    @Test
    public void compareNames() {
        mDb.getNoteDao().insertAll(SampleData.getNotes());
        Log.i(TAG, "compareNames");

        NoteEntity original = SampleData.getNotes().get(0);
        NoteEntity fromDb = mDb.getNoteDao().findNoteById(1);

        assertEquals(original.getName(), fromDb.getName());
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }
}