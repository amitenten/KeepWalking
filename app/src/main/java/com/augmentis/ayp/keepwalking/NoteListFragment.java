package com.augmentis.ayp.keepwalking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

/**
 * Created by Amita on 7/27/2016.
 */
public class NoteListFragment extends Fragment{

    private static final int REQUEST_UPDATED_NOTE = 137;

    private RecyclerView _noteRecyclerView;
    private NoteAdapter _adapter;
    protected static final String TAG = "NOTE_LIST";
    private int notePos;
    private Button addButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note_list, container, false);

        _noteRecyclerView = (RecyclerView) v.findViewById(R.id.note_recycler_view);
        _noteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addButton = (Button) v.findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NoteActivity.newIntent(getActivity(), null);
                startActivity(intent);
            }
        });

        updateUI();

        return v;
    }

    /**
     * Update UI
     */


    public void updateUI() {
        CreateNote createNote = CreateNote.getInstance(getActivity());
        List<Note> note = createNote.getNote();

        if (_adapter == null) {
            _adapter = new NoteAdapter(note);
            _noteRecyclerView.setAdapter(_adapter);
        } else {
            _adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_UPDATED_NOTE) {
            if (resultCode == Activity.RESULT_OK) {
                notePos = (int) data.getExtras().get("position");
            }
        }
    }

    private class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView _titleTextView;
        public TextView _dateTextView;

        Note _note;
        int _position;

        public NoteHolder(View itemView) {
            super(itemView);

            _titleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_note_title_text_view);
            _dateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_note_date_text_view);

            itemView.setOnClickListener(this);
        }

        public void bind(Note note, int position) {

            _note = note;
            _position = position;
            _titleTextView.setText(_note.getTitle());
            _dateTextView.setText(_note.getDate().toString());
        }


        @Override
        public void onClick(View v) {
            Intent intent = NoteActivity.newIntent(getActivity(), _note.getId());
            startActivityForResult(intent, REQUEST_UPDATED_NOTE);
        }
    }

    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {
        protected List<Note> _note;
        private int ViewCreatingCount;


        public NoteAdapter(List<Note> note) {
            this._note = note;
        }


        @Override
        public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewCreatingCount++;

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.fragment_item_note_list, parent, false);

            return new NoteHolder(v);
        }

        @Override
        public void onBindViewHolder(NoteHolder holder, int position) {

            Note note = _note.get(position);
            holder.bind(note, position);
        }

        @Override
        public int getItemCount() {
            return _note.size();
        }
    }

}
