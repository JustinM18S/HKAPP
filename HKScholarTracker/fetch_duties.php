<?php
require_once 'connection.php'; // Include the database connection file

// Add headers to allow cross-origin requests and to specify JSON output
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

$student_id = isset($_GET['student_id']) ? intval($_GET['student_id']) : null;

if ($student_id) {
    $stmt = $conn->prepare("SELECT id, day, date, time, room, professor FROM duties WHERE student_id = ?");
    $stmt->bind_param("i", $student_id);
    $stmt->execute();
    $result = $stmt->get_result();

    $duties = [];
    while ($row = $result->fetch_assoc()) {
        $duties[] = $row;
    }

    echo json_encode(["status" => true, "duties" => $duties]);

    $stmt->close();
} else {
    echo json_encode(["status" => false, "message" => "Student ID is missing"]);
}

$conn->close();
?>
