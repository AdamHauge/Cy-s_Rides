<?php

$driver = $_POST["driver"];


//MAYBE USE IF WE IMPLEMENT MAKING CHATS THAT ARENT RELATED TO TRIPS
// $rider1 = $_POST["rider1"];
// $rider2 = $_POST["rider2"];
// $rider3 = $_POST["rider3"];
// $rider4 = $_POST["rider4"];
// $rider5 = $_POST["rider5"];
// $rider6 = $_POST["rider6"];
// $rider7 = $_POST["rider7"];

$host="mysql.cs.iastate.edu";
$port=3306;
$socket="";
$username = 'dbu309sab5';
$password = 'VRCc@3V2';
$dbname = 'db309sab5';

$con = new mysqli($host, $username, $password, $dbname, $port, $socket) or die('Could not connect to database server'.mysqli_connect_error);

$sql = "INSERT INTO GROUP_TABLE (DRIVER/*, RIDER_1, RIDER_2, RIDER_3, RIDER_4, RIDER_5, RIDER_6, RIDER_7*/) VALUES ('".$driver."');";
  //end of values statement
  //,'".$rider1."','".$rider2."','".$rider3."','".$rider4."','".$rider5."','".$rider6."','".$rider7."'
if(mysqli_query($con,$sql)) {
    echo "Data insertion success...";
} else {
    echo "Error while insertion... ".$sql." ".mysqli_error($con);
}

?>
