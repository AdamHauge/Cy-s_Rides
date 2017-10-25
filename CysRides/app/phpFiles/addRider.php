<?php


$group_id = $_POST["id"];
$rider = $_POST["rider"];
$rider_num = $_POST["rider_num"];
//$offer_id = $_POST["offer_id"];


$host="mysql.cs.iastate.edu";
$port=3306;
$socket="";
$username = 'dbu309sab5';
$password = 'VRCc@3V2';
$dbname = 'db309sab5';
$sel_column = 'RIDER_' .$rider_num;

$con = new mysqli($host, $username, $password, $dbname, $port, $socket) or die('Could not connect to database server'.mysqli_connect_error);

$sql =  "UPDATE GROUP_TABLE SET " .$sel_column. " = '" .$rider. "' WHERE ID = " .$group_id. ";";
//"INSERT INTO GROUP_TABLE (DRIVER/*, RIDER_1, RIDER_2, RIDER_3, RIDER_4, RIDER_5, RIDER_6, RIDER_7*/) VALUES ('".$driver."');";
  //end of values statement
  //,'".$rider1."','".$rider2."','".$rider3."','".$rider4."','".$rider5."','".$rider6."','".$rider7."'
if(mysqli_query($con,$sql)) {
    echo "Data insertion success...";
} else {
    echo "Error while insertion... ".$sql." ".mysqli_error($con);
}

?>
