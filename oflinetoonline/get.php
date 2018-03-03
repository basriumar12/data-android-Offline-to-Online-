<?php

/*
* Database Constants
* Make sure you are putting the values according to your database here 
*/
define('DB_HOST','localhost');
define('DB_USERNAME','root');
define('DB_PASSWORD','');
define('DB_NAME', 'android');

//Connecting to the database
$conn = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);

//checking the successful connection
if($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$response=array();

if($_SERVER['REQUEST_METHOD']=='GET'){

	$stmt = $conn->query("SELECT * FROM names");

	$cek=mysqli_num_rows($stmt);
	if($cek >0){
		$response["data"]=array();

		while ($row=mysqli_fetch_array($stmt)){

			$data=array();
			$data["id"]=$row["id"];
			$data["name"]=$row["name"];
			
			$response['error'] = false; 
			$response['message'] = ' Name found.'; 
			array_push($response["data"],$data);
		}

	}else{
		$response['error'] = true; 
		$response['message'] = ' Name not found.';
	} 
}else{
	$response['error'] = true; 
	$response['message'] = "Invalid request"; 
}

echo json_encode($response);