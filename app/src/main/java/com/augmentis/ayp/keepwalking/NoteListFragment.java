package com.augmentis.ayp.keepwalking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by Amita on 7/18/2016.
 */
public class NoteListFragment extends Fragment {

    private static final int REQUEST_UPDATED_NOTE = 137;
    private static final String DIALOG_TITLE = "NoteListFragment.DIALOG_TITLE";
    private static final int REQUET_TITLE = 2005;
    protected static final String TAG = "CRIME_LIST";

    private RecyclerView _noteRecyclerView;
    private NoteAdapter _adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note_list, container, false);

        _noteRecyclerView = (RecyclerView) v.findViewById(R.id.note_recycler_view);
        _noteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.note_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_note:

                FragmentManager fm = getFragmentManager();

                NoteDialogFragment noteDialogFragment = new NoteDialogFragment();
                noteDialogFragment.setTargetFragment(NoteListFragment.this, REQUET_TITLE);
                noteDialogFragment.show(fm, DIALOG_TITLE);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * Update UI
     */
    public void updateUI() {
        CreateNote crateNote = CreateNote.getInstance(getActivity());
        List<Note> notes = crateNote.getNote();

        if (_adapter == null) {
            _adapter = new NoteAdapter(notes);
            _noteRecyclerView.setAdapter(_adapter);
        } else {
            _adapter.setNote(crateNote.getNote());
            _adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUET_TITLE) {
            updateUI();
        }
    }

    private class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView _titleTextView;
        public TextView _date;

        Note _note;
        int _position;

        public NoteHolder(View itemView) {
            super(itemView);

            _titleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_note_title_text_view);
            _date = (TextView)
                    itemView.findViewById(R.id.list_item_note_date_text_view);

            itemView.setOnClickListener(this);
        }

        public void bind(Note note, int position) {

            _note = note;
            _position = position;
            _titleTextView.setText(_note.getTitle());
            _date.setText(_note.getNoteDate().toString());

        }

        @Override
        public void onClick(View v) {
            Intent intent = NoteActivity.newIntent(getActivity(), _note.getId());
            startActivityForResult(intent, REQUEST_UPDATED_NOTE);
        }
    }

    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {
        protected List<Note> _notes;

        public NoteAdapter(List<Note> notes) {
            this._notes = notes;
        }

        protected void setNote(List<Note> notes){
            _notes = notes;
        }

        @Override
        public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.fragment_item_note_list, parent, false);

            return new NoteHolder(v);
        }

        @Override
        public void onBindViewHolder(NoteHolder holder, int position) {

            Note notes = _notes.get(position);
            holder.bind(notes, position);
        }

        @Override
        public int getItemCount() {
            return _notes.size();
        }


    }
}
