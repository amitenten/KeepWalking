package com.augmentis.ayp.keepwalking;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Amita on 7/27/2016.
 */
public class CreateNote {
    List<Note> noteList;

    private static CreateNote instance;

    public static CreateNote getInstance(Context context) {
        if(instance == null){
            instance = new CreateNote();
        }
        return instance;
    }

    public CreateNote(){
        if(noteList == null) {
            noteList = new ArrayList<>();
        }
    }

    public void addNote(String title){
        Note newNote = new Note();
        newNote.setTitle(title);
        noteList.add(newNote);
    }

    public Note getNoteById(UUID uuid) {
        for (Note note : noteList){
            if (note.getId().equals(uuid)){
                return note;
            }
        }
        return null;//new Note();
    }

    public List<Note> getNote(){
        return this.noteList;
    }
    public static void main(String[] args){
        CreateNote createNote = CreateNote.getInstance(null);
        List<Note> crimeList = createNote.getNote();
        int size = crimeList.size();
        for (int i=0; i<size; i++){
            System.out.println(i);
        }
    }
}
