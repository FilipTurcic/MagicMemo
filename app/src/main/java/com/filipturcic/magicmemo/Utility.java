package com.filipturcic.magicmemo;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class Utility {

    // Function to show a short Toast message to the user
    static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // Function to get a reference to the collection of notes in Firestore
    static CollectionReference getCollectionReferenceForNotes() {
        // Get the currently authenticated user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // Return a reference to the "my_notes" collection for the current user
        return FirebaseFirestore.getInstance().collection("notes")
                .document(currentUser.getUid()).collection("my_notes");
    }

    // Function to convert a Firestore Timestamp to a formatted string
    static String timestampToString(Timestamp timestamp) {
        // Format the timestamp to display as "MM/dd/yyyy"
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
    }
}
