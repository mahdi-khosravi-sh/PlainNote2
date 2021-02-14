package com.sky.plainnote2.ui;

import com.sky.plainnote2.database.NoteEntity;

public interface ListOperation {
    void deleteItem(NoteEntity noteEntity, int position);
}
