package com.example.hkscholarhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FacultyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile);

        // Handling window insets to adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.FacultyProfile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);  // No bottom padding
            return insets;
        });
    }
    // Method to handle back navigation
    public void goBackToFacultyHome(View view) {
        Intent intent = new Intent(FacultyProfile.this, FacultyHomePage.class);
        startActivity(intent);
        finish();
    }

    public void exitApp(View view) {
        // This will close all the activities and exit the app
        finishAffinity();
    }
}