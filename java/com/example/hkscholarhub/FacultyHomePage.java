package com.example.hkscholarhub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class FacultyHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home_page);

        // Handling window insets to adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.FacultyHomePage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);  // No bottom padding
            return insets;
        });

        // Set up BottomNavigationView to navigate between activities
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Navigating to HomeActivity
                Intent homeIntent = new Intent(FacultyHomePage.this, FacultyHomePage.class);
                startActivity(homeIntent);
                return true;
            } else if (itemId == R.id.nav_dtr) {
                // Navigating to DTRActivity
                Intent dtrIntent = new Intent(FacultyHomePage.this, FacultyLogStudent.class);
                startActivity(dtrIntent);
                return true;
            } else if (itemId == R.id.nav_profile) {
                // Navigating to ProfileActivity
                Intent profileIntent = new Intent(FacultyHomePage.this, FacultyProfile.class);
                startActivity(profileIntent);
                return true;
            }
            return false;
        });

        // Monday Card
        ImageView editDutyIconMonday = findViewById(R.id.editDutyIconMonday);
        editDutyIconMonday.setOnClickListener(view -> showEditDutyDialog(
                findViewById(R.id.dutyDateMonday),
                findViewById(R.id.dutyTimeMonday),
                findViewById(R.id.assignedRoomMonday),
                findViewById(R.id.assignedProfessorMonday)
        ));

        // Tuesday Card
        ImageView editDutyIconTuesday = findViewById(R.id.editDutyIconTuesday);
        editDutyIconTuesday.setOnClickListener(view -> showEditDutyDialog(
                findViewById(R.id.dutyDateTuesday),
                findViewById(R.id.dutyTimeTuesday),
                findViewById(R.id.assignedRoomTuesday),
                findViewById(R.id.assignedProfessorTuesday)
        ));

        // Wednesday Card
        ImageView editDutyIconWednesday = findViewById(R.id.editDutyIconWednesday);
        editDutyIconWednesday.setOnClickListener(view -> showEditDutyDialog(
                findViewById(R.id.dutyDateWednesday),
                findViewById(R.id.dutyTimeWednesday),
                findViewById(R.id.assignedRoomWednesday),
                findViewById(R.id.assignedProfessorWednesday)
        ));

        // Thursday Card
        ImageView editDutyIconThursday = findViewById(R.id.editDutyIconThursday);
        editDutyIconThursday.setOnClickListener(view -> showEditDutyDialog(
                findViewById(R.id.dutyDateThursday),
                findViewById(R.id.dutyTimeThursday),
                findViewById(R.id.assignedRoomThursday),
                findViewById(R.id.assignedProfessorThursday)
        ));

        // Friday Card
        ImageView editDutyIconFriday = findViewById(R.id.editDutyIconFriday);
        editDutyIconFriday.setOnClickListener(view -> showEditDutyDialog(
                findViewById(R.id.dutyDateFriday),
                findViewById(R.id.dutyTimeFriday),
                findViewById(R.id.assignedRoomFriday),
                findViewById(R.id.assignedProfessorFriday)
        ));

        // Enable immersive mode
        enableImmersiveMode();
    }

    // Method to show the edit duty dialog for a specific day
    private void showEditDutyDialog(TextView dutyDate, TextView dutyTime, TextView dutyRoom, TextView dutyProfessor) {
        // Create an AlertDialog for editing
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_duty, null);
        builder.setView(dialogView);

        // Find EditTexts in dialog layout
        EditText editDate = dialogView.findViewById(R.id.editDate);
        EditText editTime = dialogView.findViewById(R.id.editTime);
        EditText editRoom = dialogView.findViewById(R.id.editRoom);
        EditText editProfessor = dialogView.findViewById(R.id.editProfessor);

        // Pre-fill EditTexts with current duty details
        editDate.setText(dutyDate.getText());
        editTime.setText(dutyTime.getText());
        editRoom.setText(dutyRoom.getText());
        editProfessor.setText(dutyProfessor.getText());

        // Set Positive button for saving edits
        builder.setPositiveButton("Save", (dialog, which) -> {
            // Update the TextViews with new values after saving
            dutyDate.setText(editDate.getText().toString());
            dutyTime.setText(editTime.getText().toString());
            dutyRoom.setText(editRoom.getText().toString());
            dutyProfessor.setText(editProfessor.getText().toString());

            // Optional: Call method to send updated data to server here
            sendDutyUpdateToServer(editDate.getText().toString(),
                    editTime.getText().toString(),
                    editRoom.getText().toString(),
                    editProfessor.getText().toString());
        });

        // Set Negative button for canceling the dialog
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Method to send the updated duty info to the server (optional)
    private void sendDutyUpdateToServer(String date, String time, String room, String professor) {
        // Replace with your server URL
        String baseUrl = "http://10.0.2.2/HKScholarTracker/";
        String url = baseUrl + "update_duties.php"; // Replace with your PHP URL

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Create a new StringRequest for POST method
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Response handling
                    Toast.makeText(FacultyHomePage.this, "Duty updated successfully.", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Error handling
                    Toast.makeText(FacultyHomePage.this, "Error updating duty: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Pass parameters to the request (e.g., duty details)
                Map<String, String> params = new HashMap<>();
                params.put("duty_id", "dutyId"); // Replace with actual duty ID if needed
                params.put("date", date);
                params.put("time", time);
                params.put("room", room);
                params.put("professor", professor);
                return params;
            }
        };

        // Add the request to the RequestQueue
        requestQueue.add(stringRequest);
    }

    // Method to enable immersive mode without hiding the navigation bar
    private void enableImmersiveMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Window window = getWindow();
            WindowInsetsController controller = window.getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars());
                controller.setSystemBarsBehavior(
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                );
            }
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
    }

}
