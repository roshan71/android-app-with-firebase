package com.example.abc;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteAndEditCallback extends ItemTouchHelper.SimpleCallback {

    private TodoAdapter mAdapter;
    private Drawable icon;
    private final ColorDrawable background;


    public SwipeToDeleteAndEditCallback(TodoAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        icon = ContextCompat.getDrawable(mAdapter.context, R.drawable.ic_baseline_delete_24);
        background = new ColorDrawable(Color.RED);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            mAdapter.editItem(position);
        } else {
            mAdapter.deleteItem(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 0; // Use some value to avoid rounded corners clipping
        Drawable background;
        int iconId;
        int iconMargin;
        int backgroundColor;

        if (dX < 0) { // Swiping to the left
            iconId = R.drawable.ic_baseline_edit_24; // Set the icon for the left swipe
            iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
            backgroundColor = Color.GREEN; // Set the background color for the left swipe
            background = new ColorDrawable(backgroundColor);
            icon=ContextCompat.getDrawable(mAdapter.context, R.drawable.ic_baseline_edit_24);
            background.setBounds(itemView.getRight() + (int) dX - backgroundCornerOffset, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            icon.setBounds(itemView.getRight() - iconMargin - icon.getIntrinsicWidth(), itemView.getTop() + iconMargin, itemView.getRight() - iconMargin, itemView.getBottom() - iconMargin);
        }
//        if (dX < 0) { // Swiping to the left
//            iconId = R.drawable.ic_baseline_edit_24; // Set the icon for the left swipe
//            iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
//            backgroundColor = Color.GREEN; // Set the background color for the left swipe
//            background = new ColorDrawable(backgroundColor);
//            background.setBounds(itemView.getLeft() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
//            icon.setBounds(itemView.getRight() - iconMargin - icon.getIntrinsicWidth(), itemView.getTop() + iconMargin, itemView.getRight() - iconMargin, itemView.getBottom() - iconMargin);
//        }
        else { // Swiping to the right
            iconId = R.drawable.ic_baseline_delete_24; // Set the icon for the right swipe
            icon=ContextCompat.getDrawable(mAdapter.context, R.drawable.ic_baseline_delete_24);
            iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
            backgroundColor = Color.RED; // Set the background color for the right swipe
            background = new ColorDrawable(backgroundColor);
            background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + (int) dX + backgroundCornerOffset, itemView.getBottom());
            icon.setBounds(itemView.getLeft() + iconMargin, itemView.getTop() + iconMargin, itemView.getLeft() + iconMargin + icon.getIntrinsicWidth(), itemView.getBottom() - iconMargin);
        }

        background.draw(c);
        icon.draw(c);
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    }
}
