package com.sky.plainnote2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sky.plainnote2.database.NoteEntity;
import com.sky.plainnote2.ui.ListOperation;
import com.sky.plainnote2.ui.NoteAdapter;
import com.sky.plainnote2.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListOperation {
    private final List<NoteEntity> mNotes = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        initRecyclerView();

        initViewModel();
    }

    private void findViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        tvEmptyList = findViewById(R.id.tvEmptyList);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Act_Editor.class));
        });
    }


    private static final int ACTION_INSERT = 0;
    private static final int ACTION_ADD_SAMPLE = 1;
    private static final int ACTION_CHANGE = 2;
    private static final int ACTION_DELETE = 3;
    private static final int ACTION_DELETE_ALL = 4;
    private int action = -1;

    private TextView tvEmptyList;

    private void initViewModel() {
        final Observer<List<NoteEntity>> observer = noteEntities -> {
            final int size = mNotes.size();
            mNotes.clear();
            mNotes.addAll(noteEntities);
            if (adapter == null) {
                adapter = new NoteAdapter(mNotes, MainActivity.this);
                recyclerView.setAdapter(adapter);
            } else {
                switch (action) {
                    case ACTION_DELETE_ALL: {
                        adapter.notifyItemRangeRemoved(0, size);
                        break;
                    }
                    case ACTION_DELETE: {
                        adapter.notifyItemRemoved(listItemPosition);
                        break;
                    }
                    case ACTION_ADD_SAMPLE: {
                        final int ADAPTER_ITEM_COUNT = adapter.getItemCount();
                        adapter.notifyItemRangeInserted(ADAPTER_ITEM_COUNT, 3);
                        break;
                    }
                    case ACTION_INSERT: {
                        adapter.notifyItemInserted(mNotes.size() - 1);
                        break;
                    }
                    case -1: {
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
                action = -1;

            }
            syncEmptyViewVisibility();
        };

        mViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(MainViewModel.class);

        mViewModel.mLiveNotes.observe(this, observer);
    }

    private void syncEmptyViewVisibility() {
        if (mNotes.size() == 0) {
            tvEmptyList.animate().alpha(1F);
            recyclerView.animate().alpha(0F);
        } else {
            tvEmptyList.animate().alpha(0F);
            recyclerView.animate().alpha(1F);
        }
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setDrawable(new ColorDrawable(Color.parseColor("#EDEDED")));

        recyclerView.addItemDecoration(decoration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.action_add_sample) {
            addSampleData();
            return true;
        } else if (id == R.id.action_delete_all) {
            deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAll() {
        action = ACTION_DELETE_ALL;
        mViewModel.deleteAll();
    }

    private void addSampleData() {
        action = ACTION_ADD_SAMPLE;
        mViewModel.addSampleData();
    }

    private int listItemPosition;

    @Override
    public void deleteItem(int position) {
        action = ACTION_DELETE;
        this.listItemPosition = position;
        mViewModel.deleteNote(mNotes.get(position));
    }
}
