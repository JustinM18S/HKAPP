package com.example.hkscholarhub;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Locale;

import java.util.Calendar;
import java.util.Map;

public class FacultyLogHours extends AppCompatActivity {

    private Spinner spinnerDutyTask;
    private EditText editTextDutyDate, editTextDutyStart, editTextDutyEnd;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_faculty_log_hours);

        // Adjust padding for edge-to-edge content
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.FacultyLogHours), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        spinnerDutyTask = findViewById(R.id.spinnerDutyTask);
        editTextDutyDate = findViewById(R.id.editTextDutyDate);
        editTextDutyStart = findViewById(R.id.editTextDutyStart);
        editTextDutyEnd = findViewById(R.id.editTextDutyEnd);
        Button saveButton = findViewById(R.id.saveButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        // Set up the spinner with sample tasks
        String[] tasks = {"Select Task", "Checking of Activity ", "Attendance Monitoring", "Records Score", "Facilitate Classroom", "Announcement"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tasks);
        spinnerDutyTask.setAdapter(adapter);

        // Initialize Calendar for Date and Time Pickers
        calendar = Calendar.getInstance();

        // Date Picker
        editTextDutyDate.setOnClickListener(v -> showDatePicker());

        // Time Picker for Start Time
        editTextDutyStart.setOnClickListener(v -> showTimePicker(editTextDutyStart));

        // Time Picker for End Time
        editTextDutyEnd.setOnClickListener(v -> showTimePicker(editTextDutyEnd));

        // Save Button action
        saveButton.setOnClickListener(v -> {
            String task = spinnerDutyTask.getSelectedItem().toString();
            String date = editTextDutyDate.getText().toString();
            String startTime = editTextDutyStart.getText().toString();
            String endTime = editTextDutyEnd.getText().toString();

            if (validateInputs(task, date, startTime, endTime)) {
                // Save logic (e.g., to database)
                saveDutyToServer(task, date, startTime, endTime);
            } else {
                Toast.makeText(FacultyLogHours.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            }
        });

        // Cancel Button action
        cancelButton.setOnClickListener(v -> {
            // Clear all inputs
            spinnerDutyTask.setSelection(0);
            editTextDutyDate.setText("");
            editTextDutyStart.setText("");
            editTextDutyEnd.setText("");
        });
    }

    // Show DatePickerDialog
    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                FacultyLogHours.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    editTextDutyDate.setText(date);
                },
                year, month, day);
        datePickerDialog.show();
    }

    // Show TimePickerDialog
    private void showTimePicker(EditText editText) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                FacultyLogHours.this,
                (view, selectedHour, selectedMinute) -> {
                    // Use Locale in the String format
                    String time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                    editText.setText(time);
                },
                hour, minute, true);
        timePickerDialog.show();
    }

    // Validate inputs before saving
    private boolean validateInputs(String task, String date, String startTime, String endTime) {
        return !(task.equals("Select Task") || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty());
    }

    // Method to handle back navigation
    public void goBackToFacultyLog(View view) {
        Intent intent = new Intent(FacultyLogHours.this, FacultyLog.class);
        startActivity(intent);
        finish();
    }
    private void saveDutyToServer(String task, String date, String startTime, String endTime) {
        String url = "http://10.0.2.2/HKScholarTracker/save_duty_log.php "; // Replace with your actual PHP URL

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(FacultyLogHours.this, "Duty log saved successfully", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(FacultyLogHours.this, "Failed to save duty log", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("task", task);
                params.put("date", date);
                params.put("start_time", startTime);
                params.put("end_time", endTime);
                return params;
            }
        };

        queue.add(stringRequest);
    }

}
