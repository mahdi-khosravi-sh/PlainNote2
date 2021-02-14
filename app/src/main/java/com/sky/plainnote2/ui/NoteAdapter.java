package com.sky.plainnote2.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sky.plainnote2.Act_Editor;
import com.sky.plainnote2.R;
import com.sky.plainnote2.database.NoteEntity;
import com.sky.plainnote2.utility.Constants;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private final List<NoteEntity> mNoteEntities;
    private final Context mContext;

    public NoteAdapter(List<NoteEntity> mNoteEntities, Context mContext) {
        this.mNoteEntities = mNoteEntities;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteEntity noteEntity = mNoteEntities.get(position);
        holder.tvName.setText(noteEntity.getName());

        holder.fab.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, Act_Editor.class);
            intent.putExtra(Constants.KEY_NOTE_NAME, noteEntity.getName());
            intent.putExtra(Constants.KEY_NOTE_TEXT, noteEntity.getText());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mNoteEntities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        FloatingActionButton fab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            fab = itemView.findViewById(R.id.fab);
        }
    }
}
