package com.example.streaktodolistapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskAdapter.TaskCompletionListener {

    private ArrayList<Task> tasks;
    private TaskAdapter adapter;
    private int streakCount = 0;
    private TextView streakCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        streakCounter = findViewById(R.id.streakCounter);
        ListView listView = findViewById(R.id.taskList);
        EditText inputTask = findViewById(R.id.inputTask);
        Button addButton = findViewById(R.id.addButton);

        tasks = new ArrayList<>();
        adapter = new TaskAdapter(this, tasks, this);
        listView.setAdapter(adapter);

        // ✅ Add Task
        addButton.setOnClickListener(v -> {
            String taskText = inputTask.getText().toString().trim();
            if (!taskText.isEmpty()) {
                tasks.add(new Task(taskText, false));
                adapter.notifyDataSetChanged();
                inputTask.setText("");
            }
        });
    }

    // ✅ Update streak when task completed
    @Override
    public void onTaskCompleted(boolean isChecked) {
        if (isChecked) {
            streakCount++;
        } else {
            streakCount = Math.max(0, streakCount - 1); // avoid negative streak
        }
        streakCounter.setText("🔥 Streak: " + streakCount);
    }
}
