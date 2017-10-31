<?php

$cost = $_POST["cost"];
$email = $_POST["email"];
$destination = $_POST["destination"];
$description = $_POST["description"];
$date =$_POST["date"];

$host="mysql.cs.iastate.edu";
$port=3306;
$socket="";
$username = 'dbu309sab5';
$password = 'VRCc@3V2';
$dbname = 'db309sab5';

$con = new mysqli($host, $username, $password, $dbname, $port, $socket) or die('Could not connect to database server'.mysqli_connect_error);

$sql = "insert into OFFER_TABLE (COST, EMAIL, DESTINATION, DESCRIPTION, DATE) values('".$cost."','".$email."','".$destination."','".$description."',".$date.");";

if(mysqli_query($con,$sql)) {
    echo "Data insertion success...";
} else {
    echo "Error while insertion... ".$sql." ".mysqli_error($con);
}

?>
