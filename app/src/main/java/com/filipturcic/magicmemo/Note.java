package com.filipturcic.magicmemo;

import com.google.firebase.Timestamp;

public class Note {
    // Declare instance variables for the Note class
    String title;         // Stores the title of the note
    String content;       // Stores the content of the note
    Timestamp timestamp;  // Stores the timestamp of when the note was created

    // Constructor for the Note class (default constructor)
    public Note() {
        // This constructor is empty because it's used when creating a new instance of Note with no initial values.
    }

    // Getter method for retrieving the title of the note
    public String getTitle() {
        return title;
    }

    // Setter method for setting the title of the note
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter method for retrieving the content of the note
    public String getContent() {
        return content;
    }

    // Setter method for setting the content of the note
    public void setContent(String content) {
        this.content = content;
    }

    // Getter method for retrieving the timestamp of the note
    public Timestamp getTimestamp() {
        return timestamp;
    }

    // Setter method for setting the timestamp of the note
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
