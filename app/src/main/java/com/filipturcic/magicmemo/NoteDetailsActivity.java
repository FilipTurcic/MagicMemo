package com.filipturcic.magicmemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class NoteDetailsActivity extends AppCompatActivity {

    EditText titleEditText, contentEditText;
    ImageButton saveNoteBtn;
    TextView pageTitleTextView;
    String title, content, docId;
    boolean isEditMode = false;
    TextView deleteNoteTextViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        // Initialize UI elements
        titleEditText = findViewById(R.id.notes_title_text);
        contentEditText = findViewById(R.id.notes_content_text);
        saveNoteBtn = findViewById(R.id.save_note_btn);
        pageTitleTextView = findViewById(R.id.page_title);
        deleteNoteTextViewBtn = findViewById(R.id.delete_note_text_view_btn);

        // Receive data from the previous activity
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        // Check if in edit mode
        if (docId != null && !docId.isEmpty()) {
            isEditMode = true;
        }

        // Set received data to the UI elements
        titleEditText.setText(title);
        contentEditText.setText(content);

        // Set the page title and show delete button if in edit mode
        if (isEditMode) {
            pageTitleTextView.setText("Edit your note");
            deleteNoteTextViewBtn.setVisibility(View.VISIBLE);
        }

        // Set click listeners
        saveNoteBtn.setOnClickListener((v) -> saveNote());
        deleteNoteTextViewBtn.setOnClickListener((v) -> deleteNoteFromFirebase());
    }

    // Method to save a note
    void saveNote() {
        String noteTitle = titleEditText.getText().toString();
        String noteContent = contentEditText.getText().toString();

        // Validate note data
        if (noteTitle.isEmpty()) {
            titleEditText.setError("Title Required");
            return;
        }

        if (noteContent.isEmpty()) {
            contentEditText.setError("Content Required");
            return;
        }

        // Create a Note object and save it to Firebase
        Note note = new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());

        saveNoteToFirebase(note);
    }

    // Method to save a note to Firebase
    void saveNoteToFirebase(Note note) {
        DocumentReference documentReference;

        if (isEditMode) {
            // If in edit mode, update the existing note
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        } else {
            // If not in edit mode, create a new note
            documentReference = Utility.getCollectionReferenceForNotes().document();
        }

        // Set the note data and handle the completion of the task
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Note has been added or updated successfully
                    Utility.showToast(NoteDetailsActivity.this, "Note added successfully");
                    finish(); // Close the activity
                } else {
                    // Failed to add or update the note
                    Utility.showToast(NoteDetailsActivity.this, "Failure adding note");
                }
            }
        });
    }

    // Method to delete a note from Firebase
    void deleteNoteFromFirebase() {
        DocumentReference documentReference;

        documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Note has been deleted successfully
                    Utility.showToast(NoteDetailsActivity.this, "Note deleted successfully");
                    finish(); // Close the activity
                } else {
                    // Failed to delete the note
                    Utility.showToast(NoteDetailsActivity.this, "Failure deleting note");
                }
            }
        });
    }
}
