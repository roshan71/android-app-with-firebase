package com.example.abc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    private DBManager dbHelper;
    private List<HashMap<String, String>> todoList;

    private MainActivity2 homePage;
    public Context context;
    public TodoAdapter(Context context,List<HashMap<String, String>> todoList,MainActivity2 homePage) {
        this.todoList = todoList;

        this.context=context;
        this.homePage=homePage;
        dbHelper=new DBManager(homePage.getApplicationContext());
    }



    public void deleteItem(int position) {

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage("Are you Sure to delete? ")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // get the id of the data at the position
                        String id = todoList.get(position).get("id");

                        // delete the data from the database
                        dbHelper.deleteTaskwithID(id);
                        homePage.reload();
                        // remove the data from the list
                        todoList.remove(position);
                        notifyItemRemoved(position);

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notifyDataSetChanged();

                    }
                });
        AlertDialog alert =builder.create();
        alert.show();



    }
    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view, parent, false);
        return new TodoViewHolder(view);
    }


    private void openEditDialog(final HashMap<String, String> data) {
        // create the dialog
        final Dialog dialog = new Dialog(homePage);
        dialog.setContentView(R.layout.dialog_edit);

        // get the EditText views from the dialog
        final EditText amountEditText = dialog.findViewById(R.id.edit_amount);
        final EditText descriptionEditText = dialog.findViewById(R.id.edit_description);

        // set the default values for amount and description
        amountEditText.setText(data.get("title"));
        descriptionEditText.setText(data.get("description"));

        // set the click listener for the "Save" button
        Button saveButton = dialog.findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the new values for amount and description
                String title1 = amountEditText.getText().toString();
                String newDescription = descriptionEditText.getText().toString();

                dbHelper.editTask(data.get("id"),title1,newDescription);
                // update the item in the database
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("amount", newAmount);
//                values.put("purpose", newDescription);
//                db.update("billbook", values, "_id = ?", new String[] { money.get("id") });
//                db.close();

                // update the data in the adapter and notify the RecyclerView
                data.put("amount", title1);
                data.put("purpose", newDescription);
                notifyDataSetChanged();
                homePage.reload();
                dialog.dismiss();
            }
        });

        // set the click listener for the "Cancel" button
        Button cancelButton = dialog.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();

                dialog.dismiss();
            }
        });
        notifyDataSetChanged();
        // show the dialog
        dialog.show();
    }


        @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
            holder.bindData(todoList.get(position));
            final HashMap<String, String> data = todoList.get(position);

            // bind the data to the ViewHolder
            holder.bindData(data);

            // set the swipe listener for the item
            holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                private float startX;
                private boolean isSwiping;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startX = event.getX();
                            isSwiping = false;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (event.getX() < startX - 50) {
                                // swiped left
                                isSwiping = true;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if (isSwiping) {
                                // swiped left, open edit dialog
                                openEditDialog(data);
                                isSwiping = false;
                            }
                            break;
                    }
                    return false;
                }

            });
    }

    public void editItem(int position) {
        // get the data from the position
        HashMap<String, String> data = todoList.get(position);
        System.out.println(data);
        String id = data.get("id");
        String amount = data.get("amount");
        String description = data.get("purpose");
        openEditDialog(data);



    }
    @Override
    public int getItemCount() {
        return todoList.size();
    }




}
