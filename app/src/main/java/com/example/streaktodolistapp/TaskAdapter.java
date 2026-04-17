package com.example.streaktodolistapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {

    public interface TaskCompletionListener {
        void onTaskCompleted(boolean isChecked);
    }

    private Context context;
    private ArrayList<Task> tasks;
    private TaskCompletionListener listener;

    public TaskAdapter(Context context, ArrayList<Task> tasks, TaskCompletionListener listener) {
        this.context = context;
        this.tasks = tasks;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        }

        Task task = tasks.get(position);

        TextView taskText = convertView.findViewById(R.id.taskText);
        CheckBox checkBox = convertView.findViewById(R.id.taskCheck);

        taskText.setText(task.getText());
        checkBox.setChecked(task.isCompleted());

        // ✅ Checkbox update
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            if (listener != null) {
                listener.onTaskCompleted(isChecked);
            }
        });

        // ✅ Edit task (click on text)
        taskText.setOnClickListener(v -> {
            EditText editInput = new EditText(context);
            editInput.setText(task.getText());

            new AlertDialog.Builder(context)
                    .setTitle("Edit Task")
                    .setView(editInput)
                    .setPositiveButton("Save", (dialog, which) -> {
                        String updatedText = editInput.getText().toString().trim();
                        if (!updatedText.isEmpty()) {
                            task.setText(updatedText);
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // ✅ Delete task (long press on item)
        convertView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        tasks.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        });

        return convertView;
    }
}
