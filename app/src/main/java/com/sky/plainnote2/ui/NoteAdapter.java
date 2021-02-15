package com.sky.plainnote2.ui;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sky.plainnote2.Act_Editor;
import com.sky.plainnote2.R;
import com.sky.plainnote2.database.entities.NoteEntity;
import com.sky.plainnote2.utility.Constants;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private final List<NoteEntity> mNoteEntities;
    private final Context mContext;
    private final ListOperation listOperation;

    public NoteAdapter(List<NoteEntity> mNoteEntities, Context mContext) {
        this.mNoteEntities = mNoteEntities;
        this.mContext = mContext;
        this.listOperation = (ListOperation) mContext;
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
        holder.tvText.setText(noteEntity.getText());

        if (noteEntity.getPassword() != null) {
            holder.imgLock.setVisibility(View.VISIBLE);
        } else {
            holder.imgLock.setVisibility(View.INVISIBLE);
        }

        holder.fab.setOnClickListener(v -> {
            NoteEntity note = mNoteEntities.get(holder.getAdapterPosition());
            if (note.getPassword() != null) {
                showPasswordDialog(note);
                return;
            }
            navigateToNoteEdit(note.getId());
        });

        holder.itemView.setOnLongClickListener(v -> {
            showPopupMenu(holder.getAdapterPosition(), v);
            return true;
        });
    }

    private void showPopupMenu(int position, View v) {
        PopupMenu popupMenu = new PopupMenu(mContext, v);
        popupMenu.inflate(R.menu.list_option_menu);

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_delete) {
                listOperation.deleteItem(position);
            } else if (item.getItemId() == R.id.action_set_password) {
                listOperation.setPassword(position);
            }
            return false;
        });

        popupMenu.show();
    }

    private void navigateToNoteEdit(int id) {
        Intent intent = new Intent(mContext, Act_Editor.class);

        intent.putExtra(Constants.KEY_NOTE_ID, id);

        mContext.startActivity(intent);
    }


    private void showPasswordDialog(NoteEntity noteEntity) {
        EditText editText = new EditText(mContext);
        editText.setHint("Enter password");

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle("Encrypted note")
                .setCancelable(false)
                .setView(editText)
                .setPositiveButton("OK", (dialog, which) -> {
                    String strPassword = editText.getText().toString().trim();

                    if (TextUtils.isEmpty(strPassword)) {
                        Toast.makeText(mContext, "Enter password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (noteEntity.getPassword().equals(strPassword)) {
                        navigateToNoteEdit(noteEntity.getId());
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public int getItemCount() {
        return mNoteEntities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvText;
        FloatingActionButton fab;
        View imgLock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvText = itemView.findViewById(R.id.tvText);
            fab = itemView.findViewById(R.id.fab);
            imgLock = itemView.findViewById(R.id.imgLock);
        }
    }
}
