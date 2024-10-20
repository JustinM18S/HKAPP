package com.example.hkscholarhub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {

    private EditText email, password;
    private final String URL = "http://10.0.2.2/HKScholarTracker/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void Signin(View view) {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        Log.d("SignIn", "Email: " + userEmail);
        Log.d("SignIn", "Password: " + userPassword);

        if (!userEmail.isEmpty() && !userPassword.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    response -> {
                        Log.d("ServerResponse", "Raw Response: " + response);

                        if (response != null && !response.isEmpty()) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean status = jsonResponse.getBoolean("status");
                                String message = jsonResponse.getString("message");

                                if (status) {
                                    // Fetch role from the response (e.g., "student" or "faculty")
                                    String role = jsonResponse.getString("role");

                                    // Redirect to different pages based on role
                                    if ("student".equals(role)) {
                                        Intent intent = new Intent(SignIn.this, HomeActivity.class);
                                        startActivity(intent);
                                    } else if ("faculty".equals(role)) {
                                        Intent intent = new Intent(SignIn.this, FacultyHomePage.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SignIn.this, "Unknown role: " + role, Toast.LENGTH_SHORT).show();
                                    }

                                    finish();
                                } else {
                                    Toast.makeText(SignIn.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Log.e("SignInError", "JSON parsing error: " + response, e);
                                Toast.makeText(SignIn.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("SignInError", "Empty or null response from server");
                            Toast.makeText(SignIn.this, "No response from server", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Log.e("SignInError", "Server error: " + error.getMessage(), error);
                        Toast.makeText(SignIn.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", userEmail);
                    params.put("password", userPassword);
                    return params;
                }
            };

            int socketTimeout = 30000;
            RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(retryPolicy);

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
    // Method to handle navigation
    public void goToForgotPassword(View view) {
        Intent intent = new Intent(SignIn.this, Forgot_Password.class);
        startActivity(intent);
        finish();
    }
}

