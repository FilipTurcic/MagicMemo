package com.filipturcic.magicmemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> {
    Context context;

    // Constructor for the NoteAdapter class
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
    }

    // Method to bind data to each item in the RecyclerView
    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.titleTextview.setText(note.title);
        holder.contentTextView.setText(note.content);
        holder.timestampTextView.setText(Utility.timestampToString(note.timestamp));

        // Set a click listener to open the NoteDetailsActivity when an item is clicked
        holder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, NoteDetailsActivity.class);
            intent.putExtra("title", note.title);
            intent.putExtra("content", note.content);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });
    }

    // Method to create new ViewHolder instances
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_note_item, parent, false);
        return new NoteViewHolder(view);
    }

    // ViewHolder class to hold and manage the views for each item in the RecyclerView
    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextview, contentTextView, timestampTextView;

        // Constructor for the NoteViewHolder class
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views from the layout
            titleTextview = itemView.findViewById(R.id.notes_title_text_view);
            contentTextView = itemView.findViewById(R.id.notes_content_text_view);
            timestampTextView = itemView.findViewById(R.id.notes_timestamp_text_view);
        }
    }
}
