package com.example.hkscholarhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FacultyLogStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_log_student);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.FacultyLogStudent), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Handle student list item clicks
        LinearLayout student1 = findViewById(R.id.studentLayout1);  // First student
        student1.setOnClickListener(v -> {
            // Navigate to a new page for Student 1
            Intent intent = new Intent(FacultyLogStudent.this, FacultyLog.class);
            intent.putExtra("studentName", "Angela De Vera"); // Pass data if needed
            startActivity(intent);
        });

        // Repeat for other students
        LinearLayout student2 = findViewById(R.id.studentLayout2);
        student2.setOnClickListener(v -> {
            Intent intent = new Intent(FacultyLogStudent.this, FacultyLog.class);
            intent.putExtra("studentName", "Justin Kurt Munar");
            startActivity(intent);
        });

        LinearLayout student3 = findViewById(R.id.studentLayout3);
        student3.setOnClickListener(v -> {
            Intent intent = new Intent(FacultyLogStudent.this, FacultyLog.class);
            intent.putExtra("studentName", "Krysta Orallo");
            startActivity(intent);
        });
    }

    // Method to handle back navigation
    public void goBackToHomePage(View view) {
        Intent intent = new Intent(FacultyLogStudent.this, FacultyHomePage.class);
        startActivity(intent);
        finish();
    }
}
