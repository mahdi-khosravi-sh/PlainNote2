package com.sky.plainnote2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sky.plainnote2.database.NoteEntity;
import com.sky.plainnote2.ui.NoteAdapter;
import com.sky.plainnote2.utility.SampleData;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final List<NoteEntity> mNoteEntities = SampleData.getNotes();
    private RecyclerView recyclerView;
    private NoteAdapter adapter;

    /* commented from github */
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        initRecyclerView();
    }

    private void findViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setDrawable(new ColorDrawable(Color.parseColor("#EDEDED")));

        recyclerView.addItemDecoration(decoration);

        adapter = new NoteAdapter(mNoteEntities, this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Act_Editor.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
