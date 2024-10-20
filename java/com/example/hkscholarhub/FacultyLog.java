package com.example.hkscholarhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FacultyLog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_faculty_log);

        // Adjust insets for the layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Facultylog), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the button by its ID
        Button addEntryButton = findViewById(R.id.add_entry_btn);

        // Set onClickListener to navigate to another activity when the button is clicked
        addEntryButton.setOnClickListener(view -> {
            // Intent to navigate to the target activity (replace TargetActivity.class with the actual class)
            Intent intent = new Intent(FacultyLog.this, FacultyLogHours.class);
            startActivity(intent);
        });
    }

    // Method to handle back navigation
    public void goBackToFacultyLogStudent(View view) {
        Intent intent = new Intent(FacultyLog.this, FacultyLogStudent.class);
        startActivity(intent);
        finish();
    }
}
