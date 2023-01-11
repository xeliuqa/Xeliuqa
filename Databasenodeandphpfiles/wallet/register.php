<?php 
$response = array(); 
include 'db_connect.php'; 
include 'functions.php'; //Get the input request parameters 
$inputJSON = file_get_contents('php://input'); 
$input = json_decode($inputJSON, TRUE); 
//convert JSON into array
 
//Check for Mandatory parameters 
if(isset($input['email']) && isset($input['password']) && isset($input['name']) && isset($input['address']) && isset($input['secret'])){
	$email = $input['email'];
	$password = $input['password'];
	$name = $input['name'];
	$address = $input['address'];
	$key = $input['secret'];
	$addressb = $input['addressb'];
	$keyb = $input['secretb'];
        $word = $input['code'];
	$hashes = md5( rand(0,1000) );
	
	$dt = new DateTime();
        $time = $dt->format('Y-m-d H:i:s');
	//Check if user already exist
	if(!userExists($name)){
 
        $hash = dec_enc(encrypt, $password);
		//Get a unique Salt
		$salt = getSalt();
		
		//Generate a unique password Hash
		$hash = password_hash($password, PASSWORD_BCRYPT);
		
		
		//Query to register new user
		$insertQuery = "INSERT INTO users(email, username, password, salt, eth_address, eth_secret, hash, btc_address, btc_secret, hashes) VALUES (?,?,?,?,?,?,?,?,?,?)";
		if($stmt = $con->prepare($insertQuery)){
			$stmt->bind_param("ssssssssss",$email,$name,$passwordHash,$salt,$address,$key,$hash,$addressb,$keyb,$hashes);
			$stmt->execute();
			$response["status"] = 0;
			$response["message"] = "User created";
			$stmt->close();
			
		//Send Email
			$to = $username;
            $subject = 'XELIUQA WALLET';
            $message = 'Welcome to Xeliuqa Wallet!' . "\r\n" . "\r\n" .
                            '3 Security Tips:' . "\r\n" .
                            '* DO NOT give your password to anyone!' . "\r\n" .
                            '* DO NOT call any phone number for someone claiming to be Xeliuqa Support!' . "\r\n" .
                            '* DO NOT send any money to anyone claiming to be a member of Xeliuqa!' . "\r\n" . "\r\n" .
                            'If this activity is not your own operation, please contact us immediately on info@Xeliuqa.app.' . "\r\n" . "\r\n".
                           'XELIUQA WALLET Team' . "\r\n" . "\r\n" .
                           'Automated message. Please do not reply!' . "\r\n" ."\r\n" .
                           '';
            $headers = 'From: noreply-xeliuqa <info@xeliuqa.app>' . "\r\n" .
                           'Reply-To: info@xeliuqa.app' . "\r\n" .
                           'X-Mailer: PHP/' . phpversion();
            mail($to, $subject, $message, $headers);
		}
	}
	else{
		$response["status"] = 1;
		$response["message"] = "User exists";
	}
}
else{
	$response["status"] = 2;
	$response["message"] = "Missing mandatory parameters";
}
echo json_encode($response);
?>
