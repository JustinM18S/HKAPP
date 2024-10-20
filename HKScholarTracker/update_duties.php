<?php
require_once 'connection.php'; // Include the database connection file

// Add headers to allow cross-origin requests and to specify JSON output
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

$input = json_decode(file_get_contents("php://input"), true);
file_put_contents('php://stderr', print_r($input, true));

if (isset($input['duty_id'], $input['date'], $input['time'], $input['room'], $input['professor'])) {
    $duty_id = intval($input['duty_id']);
    $date = $input['date'];
    $time = $input['time']; // Expected format: "7:30 AM - 4:30 PM"
    $room = $input['room'];
    $professor = $input['professor'];

    // Prepare and bind parameters for the SQL update statement
    $stmt = $conn->prepare("UPDATE duties SET date = ?, time = ?, room = ?, professor = ? WHERE duty_id = ?");
    // Change bind_param to "ssssi" since time is VARCHAR
    $stmt->bind_param("ssssi", $date, $time, $room, $professor, $duty_id);

    // Execute the statement and return JSON response
    if ($stmt->execute()) {
        echo json_encode(["status" => true, "message" => "Duty updated successfully"]);
    } else {
        echo json_encode(["status" => false, "message" => "Failed to update duty"]);
    }

    $stmt->close();
} else {
    echo json_encode(["status" => false, "message" => "Invalid input data"]);
}

$conn->close();
?>
