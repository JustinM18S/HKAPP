<?php
require_once 'connection.php'; // Include the database connection file

// Add headers to allow cross-origin requests and to specify JSON output
header("Access-Control-Allow-Origin: *"); // Allow requests from any origin
header("Content-Type: application/json; charset=UTF-8");

$method = $_SERVER['REQUEST_METHOD'];

switch ($method) {
    case 'GET':
        handleGetRequest();
        break;
    
    case 'POST':
        handlePostRequest();
        break;

    default:
        echo json_encode(["status" => false, "message" => "Invalid request method"]);
        break;
}

// GET: Retrieve User Data
function handleGetRequest() {
    global $conn;

    if (isset($_GET['student_id'])) {
        $id = intval($_GET['student_id']);
        $stmt = $conn->prepare("SELECT id, email FROM tableuser WHERE id = ?");
        $stmt->bind_param("i", $id);
        $stmt->execute();
        $stmt->bind_result($userId, $email);
        if ($stmt->fetch()) {
            echo json_encode(["status" => true, "user" => ['id' => $userId, 'email' => $email]]);
        } else {
            echo json_encode(["status" => false, "message" => "User not found."]);
        }
        $stmt->close();
    } else {
        $result = $conn->query("SELECT id, email FROM tableuser");
        $users = [];
        while ($row = $result->fetch_assoc()) {
            $users[] = $row;
        }
        echo json_encode(["status" => true, "users" => $users]);
    }

    $conn->close();
}

// POST: Create a new user (Register)
function handlePostRequest() {
    global $conn;

    $email = isset($_POST['email']) ? filter_var(trim($_POST['email']), FILTER_SANITIZE_EMAIL) : null;
    $password = isset($_POST['password']) ? trim($_POST['password']) : null;
    $role = isset($_POST['role']) ? trim($_POST['role']) : 'student'; // Default to student role

    if (!$email || !$password) {
        echo json_encode(["status" => false, "message" => "Email, password, and role are required."]);
        exit;
    }

    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        echo json_encode(["status" => false, "message" => "Invalid email format."]);
        exit;
    }

    // Check if email already exists in the database
    $stmt = $conn->prepare("SELECT id FROM tableuser WHERE email = ?");
    $stmt->bind_param("s", $email);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {
        // Email is already registered
        echo json_encode(["status" => false, "message" => "Email is already registered."]);
        $stmt->close();
        $conn->close();
        exit;
    }

    $stmt->close();

    // Hash the password and insert into database
    $hashed_password = password_hash($password, PASSWORD_DEFAULT);
    $stmt = $conn->prepare("INSERT INTO tableuser (email, password, role) VALUES (?, ?, ?)");
    $stmt->bind_param("sss", $email, $hashed_password, $role);

    if ($stmt->execute()) {
        echo json_encode(["status" => true, "message" => "User registered successfully."]);
    } else {
        echo json_encode(["status" => false, "message" => "Error registering user."]);
    }

    $stmt->close();
    $conn->close();
}

?>
