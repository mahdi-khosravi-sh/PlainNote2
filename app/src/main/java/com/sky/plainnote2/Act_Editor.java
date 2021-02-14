package com.sky.plainnote2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sky.plainnote2.utility.Constants;

public class Act_Editor extends AppCompatActivity {
    private boolean isEditing;
    private EditText etNoteName;
    private EditText etNoteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_editor);

        findViews();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String text = bundle.getString(Constants.KEY_NOTE_TEXT);
            String name = bundle.getString(Constants.KEY_NOTE_NAME);

            etNoteName.setText(name);
            etNoteText.setText(text);

            isEditing = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEditing) {
            getMenuInflater().inflate(R.menu.menu_editor, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews() {
        etNoteName = findViewById(R.id.etNoteName);
        etNoteText = findViewById(R.id.etNoteText);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_check);
        }
    }
}