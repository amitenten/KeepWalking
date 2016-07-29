package com.augmentis.ayp.keepwalking;

import java.util.Date;
import java.util.UUID;

public class Note {
    private UUID id;
    private  String title;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Note() {
        id = UUID.randomUUID();
        date = new Date();
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
}
