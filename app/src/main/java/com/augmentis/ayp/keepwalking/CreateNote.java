package com.augmentis.ayp.keepwalking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.augmentis.ayp.keepwalking.NoteDbSchema.NoteTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Amita on 7/18/2016.
 */
public class CreateNote {
    private Context context;
    private SQLiteDatabase database;

    private static CreateNote instance;
    private static final String TAG = "NoteLab";

    public static CreateNote getInstance(Context context) {
        if (instance == null) {
            instance = new CreateNote(context);
        }
        return instance;
    }

    private static ContentValues getContentValues(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteDbSchema.NoteTable.Cols.UUID, note.getId().toString());
        contentValues.put(NoteDbSchema.NoteTable.Cols.TITLE, note.getTitle());
        contentValues.put(NoteDbSchema.NoteTable.Cols.DATE, note.getNoteDate().getTime());
        return contentValues;
    }

    private CreateNote(Context context) {
        this.context = context.getApplicationContext();

        NoteBaseHelper noteBaseHelper = new NoteBaseHelper(context);
        database = noteBaseHelper.getWritableDatabase();

    }

    public Note getNotesById(UUID uuid) {
        NoteCursorWrapper cursor = queryNotes(NoteTable.Cols.UUID + " = ? ",
                new String[]{uuid.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getNote();
        } finally {
            cursor.close();
        }
    }

/*    //set position
    public int getNotesPositionById(UUID uuid) {
        return -1;
    }*/

    public NoteCursorWrapper queryNotes(String whereCause, String[] whereArgs) {
        Cursor cursor = database.query(NoteTable.NAME,
                null,
                whereCause,
                whereArgs,
                null,
                null,
                null,
                null);
        return new NoteCursorWrapper(cursor);
    }

    public List<Note> getNote() {
        List<Note> notes = new ArrayList<>();

        NoteCursorWrapper cursor = queryNotes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                notes.add(cursor.getNote());

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return notes;
    }

    public static void main(String[] args) {
        CreateNote createNote = CreateNote.getInstance(null);
        List<Note> noteList = createNote.getNote();
        int size = noteList.size();
        for (int i = 0; i < size; i++) {
            System.out.println(i);

//            System.out.println(createNote.toString());
//            System.out.println(CreateNote.getInstance(null));
        }
    }

    public void addNote(Note note) {
        Log.d(TAG, "Add Note" + note.toString());

        ContentValues contentValues = getContentValues(note);
        database.insert(NoteTable.NAME, null, contentValues);
    }

    public void deleteNote(UUID uuid) {
        database.delete(NoteTable.NAME,
                NoteTable.Cols.UUID + " = ? ",
                new String[] {uuid.toString()});
    }

    public void updateNote(Note note) {
        Log.d(TAG, "Update Note" + note.toString());

        String uuidStr = note.getId().toString();
        ContentValues contentValues = getContentValues(note);

        database.update(NoteTable.NAME, contentValues,
                NoteTable.Cols.UUID + " = ?", new String[]{uuidStr});
    }
}
