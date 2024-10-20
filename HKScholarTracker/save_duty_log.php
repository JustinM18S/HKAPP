<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: Content-Type");
header("Access-Control-Allow-Methods: POST");

require_once 'connection.php'; // Assuming you have a connection file

$input = json_decode(file_get_contents("php://input"), true);

if (isset($input['task'], $input['date'], $input['start_time'], $input['end_time'])) {
    $task = $input['task'];
    $date = $input['date'];
    $start_time = $input['start_time'];
    $end_time = $input['end_time'];

    $stmt = $conn->prepare("INSERT INTO duty_logs (task, date, start_time, end_time) VALUES (?, ?, ?, ?)");
    $stmt->bind_param("ssss", $task, $date, $start_time, $end_time);

    if ($stmt->execute()) {
        echo json_encode(["status" => true, "message" => "Duty log saved successfully"]);
    } else {
        echo json_encode(["status" => false, "message" => "Failed to save duty log"]);
    }

    $stmt->close();
} else {
    echo json_encode(["status" => false, "message" => "Invalid input data"]);
}

$conn->close();
?>
