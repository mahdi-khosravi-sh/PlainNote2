package com.sky.plainnote2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sky.plainnote2.database.entities.NoteEntity;
import com.sky.plainnote2.database.entities.Password;
import com.sky.plainnote2.utility.Constants;
import com.sky.plainnote2.viewmodel.EditorViewModel;

public class Act_Editor extends AppCompatActivity {
    private boolean isEditing;
    private EditText etNoteName;
    private EditText etNoteText;
    private EditorViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_editor);

        findViews();

        initViewModel();
    }

    private void initViewModel() {
        final Observer<NoteEntity> observer = noteEntity -> {
            etNoteName.setText(noteEntity.getName());
            etNoteText.setText(noteEntity.getText());
        };

        mViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(EditorViewModel.class);

        mViewModel.mLiveNote.observe(this, observer);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int id = bundle.getInt(Constants.KEY_NOTE_ID);
            mViewModel.loadData(id);
            isEditing = true;
            setTitle("Editing note");
        } else {
            setTitle("New note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isEditing) {
            getMenuInflater().inflate(R.menu.menu_editor, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            deleteAndReturn();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
        super.onBackPressed();
    }

    private void saveAndReturn() {
        if (mViewModel.saveNote(
                etNoteName.getText().toString(),
                etNoteText.getText().toString())) {
            finish();
        }
    }

    private void deleteAndReturn() {
        mViewModel.delete();
        finish();
    }

    private void findViews() {
        etNoteName = findViewById(R.id.etNoteName);
        etNoteText = findViewById(R.id.etNoteText);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_check);
        }
    }
}