package com.augmentis.ayp.keepwalking;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Amita on 7/18/2016.
 */
public class Note {
    private UUID id;
    private Date noteDate;
    private String title;

    public Date getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(Date noteDate) {
        this.noteDate = noteDate;
    }

    public Note() {
        this(UUID.randomUUID());
    }

    public Note(UUID uuid){
        this.id = uuid;
        noteDate = new Date();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UUID= ").append(id);
        builder.append(",Title= ").append(title);
        builder.append(",Note Date= ").append(noteDate);
        return builder.toString();
    }
}
