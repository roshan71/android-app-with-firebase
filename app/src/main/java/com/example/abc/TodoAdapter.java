package com.example.abc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {

    private List<HashMap<String, String>> todoList;
    private OnTodoClickListener listener;

    public TodoAdapter(List<HashMap<String, String>> todoList, OnTodoClickListener listener) {
        this.todoList = todoList;
        this.listener = listener;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        holder.bindData(todoList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public interface OnTodoClickListener {
        void onDeleteClick(HashMap<String, String> todoMap);
        void onEditClick(HashMap<String, String> todoMap);
    }
}
