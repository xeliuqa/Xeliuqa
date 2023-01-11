<?php 
define('DB_SERVER', "localhost");
define('DB_USER', "root");
define('DB_PASSWORD', "mZtAYlxJyS53M1i3");
define('DB_DATABASE', "wallet");
 
$con = mysqli_connect(DB_SERVER,DB_USER,DB_PASSWORD,DB_DATABASE);
 
// Check connection if(mysqli_connect_errno()) {
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

