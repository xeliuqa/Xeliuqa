<?php $response = array(); include 'db_connect.php'; include 'functions.php';
 
//Get the input request parameters
 $inputJSON = file_get_contents('php://input'); 
$input = json_decode($inputJSON, TRUE); //convert JSON into array
 
//Check for Mandatory parameters 
if(isset($input['email'])){
	$mail = $input['email'];
	$query = "SELECT username, hash FROM member WHERE username = ?";
 
	if($stmt = $con->prepare($query)){
		$stmt->bind_param("s",$mail);
		$stmt->execute();
		$stmt->bind_result($email,$hash);
		if($stmt->fetch()){
			//Validate the password
			if($mail == $email){
				$response["status"] = 0;
				$response["message"] = "Email valid";
				//Send Email password
				$passkey = dec_enc(decrypt, $hash);
				
				$to = $email;
                $subject = 'XELIUQA WALLET';
                $message = 'Hello' . "\r\n" . "\r\n" . 'Your mobile wallet password is ' . $passkey .
                           ', If this activity is not your own operation, please contact us immediately on info@xeliuqa.app.' . "\r\n" . "\r\n".
                           'Regards' . "\r\n" .
                           'Xeliuqa Team' . "\r\n" ."\r\n" .
                            'Automated message. Please do not reply!' . "\r\n" ."\r\n" .
                           'To unsubscribe, visit:';
                $headers = 'From: noreply-xeliuqa <info@xeliuqa.app>' . "\r\n" .
                           'Reply-To: info@xeliuqa.app' . "\r\n" .
                           'X-Mailer: PHP/' . phpversion();
                mail($to, $subject, $message, $headers);
			}
			else{
				$response["status"] = 1;
				$response["message"] = "Email is incorrect";
			}
		}
		else{
			$response["status"] = 1;
			$response["message"] = "Email is incorrect";
		}
		
		$stmt->close();
	}
}
else{
	$response["status"] = 2;
	$response["message"] = "Missing mandatory parameters";
}
//Display the JSON response echo json_encode($response);
?>
