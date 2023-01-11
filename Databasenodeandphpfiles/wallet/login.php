<?php 
$response = array(); 
include 'db_connect.php'; 
include 'functions.php';
 
//Get the input request parameters 
$inputJSON = file_get_contents('php://input'); 
$input = json_decode($inputJSON, TRUE); //convert JSON into array
 
//Check for Mandatory parameters 
if(isset($input['username']) && isset($input['password'])){
	$username = $input['username'];
	$password = $input['password'];
	$query = "SELECT full_name,password_hash, salt, api_key, api_secret, api_keyb, api_secretb, active FROM member WHERE username = ?";
 
	if($stmt = $con->prepare($query)){
		$stmt->bind_param("s",$username);
		$stmt->execute();
		$stmt->bind_result($fullName,$passwordHashDB,$salt,$key,$secret,$keyb,$secretb,$isValid);
		if($stmt->fetch()){
			//Validate the password
			if(password_verify(concatPasswordWithSalt($password,$salt),$passwordHashDB)){
			    if ($isValid==1){
				$response["status"] = 0;
				$response["message"] = "Login successful";
				$response["full_name"] = $fullName;
				$response["api_key"] = $key;
				$response["api_secret"] = $secret;
				$response["api_keyb"] = $keyb;
				$response["api_secretb"] = $secretb;
				
				$rndno=rand(100000, 999999);
				$response["code"] = $rndno;
				//Insert new verification code
				//$rndno=rand(100000, 999999);//OTP generate
				//$query_u = "UPDATE member SET otpcode='".$rndno."' WHERE username='".$username."'";
				//$stmts = $con->prepare($query_u);
				//$stmts->execute();
				//Email the user
				$to = $username;
                $subject = 'XELIUQA WALLET';
                $message = 'Hello '. $full_name . "\r\n" . "\r\n" .
                            'This email contains your 2 Factor Authentication Code to complete your login at Xeliuqa Wallet.' . "\r\n" . "\r\n" .
                            'Email: ' . $username ."\r\n" .
                            'OTP Code: '. $rndno ."\r\n" . "\r\n" .
                            'This code is case sensitive!' . "\r\n" . "\r\n" .
                            'If you did not make this login attempt you should change your password immediately since whoever made this request has your correct password. For support please contact 
us on info@xeliuqa.app.' . "\r\n" . "\r\n".
                            'Thank you!' . "\r\n" ."\r\n" .
                           'Xeliuqa Wallet Team' . "\r\n" . "\r\n" .
                           '';
                $headers = 'From: noreply-xeliuqa <info@xeliuqa.app>' . "\r\n" .
                           'Reply-To: info@xeliuqa.app' . "\r\n" .
                           'X-Mailer: PHP/' . phpversion();
                mail($to, $subject, $message, $headers);
			    }
				else{
				$response["status"] = 3;
				$response["message"] = "Account not yet verified";
				}
			}
			else{
				$response["status"] = 1;
				$response["message"] = "Invalid email and password combination";
			}
		}
		else{
			$response["status"] = 1;
			$response["message"] = "Invalid email and password combination";
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
