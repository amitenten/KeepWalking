package com.augmentis.ayp.keepwalking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.UUID;

/**
 * Created by Amita on 7/27/2016.
 */
public class NoteActivity extends ManuScriptFragmentActivity {

    protected static final String NOTE_ID = "noteActivity.noteID";
    private static final String NOTE_POSITION = "noteActivity.notePos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

    }

    public static Intent newIntent(Context context, UUID id) {
        Intent intent = new Intent(context, NoteActivity.class);
        if(id == null){
            String title = "";
            CreateNote createNote = CreateNote.getInstance(null);
            createNote.addNote(title);
            id = createNote.getNote().get(createNote.getNote().size()-1).getId();
            intent.putExtra(NOTE_ID, id);
        }
        else{
            intent.putExtra(NOTE_ID, id);
        }
        return intent;
    }

    protected Fragment onCreateFragment() {
        UUID noteId = (UUID) getIntent().getSerializableExtra(NOTE_ID);
        Fragment fragment = NoteFragment.newInstance(noteId);
        return fragment;
    }
}