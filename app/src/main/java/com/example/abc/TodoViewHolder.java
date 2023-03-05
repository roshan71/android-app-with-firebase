package com.example.abc;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public class TodoViewHolder extends RecyclerView.ViewHolder {

    TextView titleTextView;
    TextView descriptionTextView;


    public TodoViewHolder(View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.tv_task_title);
        descriptionTextView = itemView.findViewById(R.id.tv_task_desc);

    }

    public void bindData(final HashMap<String, String> todoMap) {
        titleTextView.setText(todoMap.get("title"));
        descriptionTextView.setText(todoMap.get("description"));

    }
}
