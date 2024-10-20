<?php
require_once 'connection.php';
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");

$faculty_id = isset($_POST['faculty_id']) ? intval($_POST['faculty_id']) : null;
$student_id = isset($_POST['student_id']) ? intval($_POST['student_id']) : null;
$task_date = isset($_POST['task_date']) ? $_POST['task_date'] : null;
$start_time = isset($_POST['start_time']) ? $_POST['start_time'] : null;
$end_time = isset($_POST['end_time']) ? $_POST['end_time'] : null;
$assigned_room = isset($_POST['assigned_room']) ? $_POST['assigned_room'] : null;
$assigned_professor = isset($_POST['assigned_professor']) ? $_POST['assigned_professor'] : null;

if ($faculty_id && $student_id && $task_date && $start_time && $end_time) {
    $stmt = $conn->prepare("INSERT INTO tasks (task_date, start_time, end_time, assigned_room, assigned_professor, student_id) 
                            VALUES (?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("sssssi", $task_date, $start_time, $end_time, $assigned_room, $assigned_professor, $student_id);
    
    if ($stmt->execute()) {
        echo json_encode(["status" => true, "message" => "Task assigned successfully"]);
    } else {
        echo json_encode(["status" => false, "message" => "Failed to assign task"]);
    }

    $stmt->close();
} else {
    echo json_encode(["status" => false, "message" => "Missing required fields"]);
}

$conn->close();
?>
