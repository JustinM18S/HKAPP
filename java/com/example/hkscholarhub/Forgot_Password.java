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

public class Forgot_Password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forgotpassword), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Find the button by its ID
        Button sendEmailButton = findViewById(R.id.send_email_button);

        // Set onClickListener to navigate to another activity when the button is clicked
        sendEmailButton.setOnClickListener(view -> {
            Intent intent = new Intent(Forgot_Password.this, Email_Verification.class);
            startActivity(intent);
        });
    }
    // Method to handle back navigation
    public void goBackToSignInPage(View view) {
        Intent intent = new Intent(Forgot_Password.this, SignIn.class);
        startActivity(intent);
        finish();
    }
}