<?php

$user = "dbu309sab5";
$password = "VRCc@3V2";
$host = "mysql.cs.iastate.edu";
$db_name = "db309sab5";

$con = myspli_connect($host,$user,$password$db_name);

$sql = "sqlstatement";

if(mysqli_query($con,$sql)) {
    echo "Data insertion success...";
} else {
    echo "Error while insertion...";
}

?>