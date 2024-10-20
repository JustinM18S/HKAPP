<?php
// Enable error reporting for debugging
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// Set content type to application/json
header('Content-Type: application/json');

require_once 'connection.php'; // Your database connection file

$response = array(); // Initialize an array for JSON response

// Check if email and password are provided
if (isset($_POST['email']) && isset($_POST['password'])) {
    $email = $_POST['email'];
    $password = $_POST['password'];

    // Sanitize inputs to prevent SQL Injection
    $email = mysqli_real_escape_string($conn, $email);

    // Prepare statement to check the database for the email and role
    $stmt = $conn->prepare("SELECT password, role FROM tableuser WHERE email = ?");
    if ($stmt) {
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // Fetch the hashed password and role from the database
            $stmt->bind_result($hashedPassword, $role);
            $stmt->fetch();

            // Verify the password
            if (password_verify($password, $hashedPassword)) {
                // Login successful, return the role
                $response['status'] = true;
                $response['message'] = "Login successful.";
                $response['role'] = $role; // Include the role in the response
            } else {
                // Incorrect password
                $response['status'] = false;
                $response['message'] = "Invalid password.";
            }
        } else {
            // Email not found
            $response['status'] = false;
            $response['message'] = "Email not found.";
        }

        $stmt->close();
    } else {
        // Log an error if statement preparation fails
        $response['status'] = false;
        $response['message'] = "Database error.";
    }

    $conn->close();
} else {
    // Email or password missing
    $response['status'] = false;
    $response['message'] = "Missing email or password.";
}

// Make sure nothing else is sent before the JSON response
echo json_encode($response);
?>
