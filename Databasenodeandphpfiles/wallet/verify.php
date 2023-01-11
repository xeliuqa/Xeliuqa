<?php mysql_connect("localhost", "root", "Iagdtbr9917@1") or die(mysql_error());
 mysql_select_db("wallet") or die(mysql_error());
             
if(isset($_GET['hash']) && !empty($_GET['hash'])){
    // Verify data
    $hash = mysql_escape_string($_GET['hash']); // Set hash variable
                 
    $search = mysql_query("SELECT hashes, active FROM member WHERE hashes='".$hash."' AND active='0'") or die(mysql_error());
    $match = mysql_num_rows($search);
                 
    if($match > 0){
        // We have a match, activate the account
        mysql_query("UPDATE member SET active='1' WHERE hashes='".$hash."' AND active='0'") or die(mysql_error());
        echo '<center><div class="example">Your account has been activated, you can now login</div></center>';
    }else{
        // No match -> invalid url or account has already been activated.
        echo '<center><div class="example">The url is either invalid or you already have activated your account.</div></center>';
    }
                 
}else{
    // Invalid approach
    echo '<center><div class="example">Invalid approach, please use the link that has been send to your email.</div></center>';
}
?> <!DOCTYPE html> <html> <head> <meta name="viewport" content="width=device-width, initial-scale=1"> <style> div.example {
  background-color: #3399ff;
  color: white;
  padding: 20px;
}
@media screen and (min-width: 601px) {
  div.example {
    font-size: 15px;
  }
}
@media screen and (max-width: 600px) {
  div.example {
    font-size: 30px;
  }
}
</style> </head> <body> </body>
</html>
