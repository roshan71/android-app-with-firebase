package com.example.abc;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public class TodoViewHolder extends RecyclerView.ViewHolder {

    TextView titleTextView;
    TextView descriptionTextView;
    Button deleteButton;
    Button editButton;

    public TodoViewHolder(View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.tv_task_title);
        descriptionTextView = itemView.findViewById(R.id.tv_task_desc);
        deleteButton = itemView.findViewById(R.id.task_delete_button);
        editButton = itemView.findViewById(R.id.task_edit_button);
    }

    public void bindData(final HashMap<String, String> todoMap, final TodoAdapter.OnTodoClickListener listener) {
        titleTextView.setText(todoMap.get("title"));
        descriptionTextView.setText(todoMap.get("description"));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClick(todoMap);
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditClick(todoMap);
            }
        });
    }
}
