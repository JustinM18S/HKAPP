<?php

$host = 'localhost';  
$dbname = 'scholartrackerdb'; 
$db_email = 'root'; 
$password = ''; 

// Create a MySQLi connection
$conn = new mysqli($host, $db_email, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

//echo "Connected successfully";

?>