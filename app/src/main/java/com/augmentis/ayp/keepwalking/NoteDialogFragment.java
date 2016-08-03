package com.augmentis.ayp.keepwalking;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DialogTitle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Amita on 7/29/2016.
 */
public class NoteDialogFragment extends DialogFragment {
    private Note note;

    protected static final String NOTE_ID = "noteActivity.noteID";
    protected static final String EXTRA_TITLE = "extra_title";

    public static NoteDialogFragment newInstance(UUID noteId) {
        Bundle args = new Bundle();
        args.putSerializable(NOTE_ID, noteId);

        NoteDialogFragment noteDF = new NoteDialogFragment();
        noteDF.setArguments(args);
        return noteDF;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_note_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        builder.setTitle(R.string.app_name);
        final EditText editTitle = (EditText) v.findViewById(R.id.note_dialog_title);

        builder.setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Note note = new Note();
                note.setTitle(editTitle.getText().toString());
                CreateNote.getInstance(getActivity()).addNote(note);

                sendResult(Activity.RESULT_OK, note.getId());
            }
        });
        return builder.create();
    }
    private void sendResult(int resultCode, UUID id){
        if (getTargetFragment()== null){
            return;
        }else {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TITLE, id);

            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }
}
