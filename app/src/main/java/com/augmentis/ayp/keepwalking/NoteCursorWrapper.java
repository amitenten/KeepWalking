package com.augmentis.ayp.keepwalking;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.augmentis.ayp.keepwalking.NoteDbSchema.NoteTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Amita on 8/1/2016.
 */
public class NoteCursorWrapper extends CursorWrapper {

    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote() {
        String uuidString = getString(getColumnIndex(NoteTable.Cols.UUID));
        String titile = getString(getColumnIndex(NoteTable.Cols.TITLE));
        long date = getLong(getColumnIndex(NoteTable.Cols.DATE));

        Note note = new Note(UUID.fromString(uuidString));
        note.setTitle(titile);
        note.setNoteDate(new Date(date));

        return note;
    }
}
