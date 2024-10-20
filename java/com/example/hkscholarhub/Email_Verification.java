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

public class Email_Verification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email_verification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.emailverify), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Find the button by its ID
        Button verifyButton = findViewById(R.id.verify_button);

        // Set onClickListener to navigate to another activity when the button is clicked
        verifyButton.setOnClickListener(view -> {
            // Intent to navigate to the target activity (replace TargetActivity.class with the actual class)
            Intent intent = new Intent(Email_Verification.this, CreateNew_Password.class);
            startActivity(intent);
        });
    }
    // Method to handle back navigation
    public void goBackToForgotPassword(View view) {
        Intent intent = new Intent(Email_Verification.this, Forgot_Password.class);
        startActivity(intent);
        finish();
    }
}