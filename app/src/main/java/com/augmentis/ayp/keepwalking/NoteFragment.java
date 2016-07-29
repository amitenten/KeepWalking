package com.augmentis.ayp.keepwalking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Amita on 7/27/2016.
 */
public class NoteFragment extends Fragment {

    private static final String NOTE_ID = "NoteFragment.NOTE_ID";

    private Note note;
    private EditText editText;
    private int position;
    private Button saveButton;
    private String tempString;

    public NoteFragment() {
    }

    public static NoteFragment newInstance(UUID noteId) {
        Bundle args = new Bundle();
        args.putSerializable(NOTE_ID, noteId);

        NoteFragment noteFragment = new NoteFragment();
        noteFragment.setArguments(args);
        return noteFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID noteId = (UUID) getArguments().getSerializable(NOTE_ID);
        note = CreateNote.getInstance(getActivity()).getNoteById(noteId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_note, container, false);

        editText = (EditText) v.findViewById(R.id.note_title);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tempString = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        saveButton = (Button) v.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                note.setTitle(tempString);
            }
        });


        return v;
    }

    private String getFormattedDate(Date date) {
        return new SimpleDateFormat("dd MMMM yyyy").format(date);
    }
}
