<?php

  $user = $_POST["user"];
  $id = $_POST["id"];
  $type = $_POST["type"];



  $host="mysql.cs.iastate.edu";
  $port=3306;
  $socket="";
  $username = 'dbu309sab5';
  $password = 'VRCc@3V2';
  $dbname = 'db309sab5';

  $con = new mysqli($host, $username, $password, $dbname, $port, $socket) or die('Could not connect to database server'.mysqli_connect_error);
  if($type === "OFFER"){
    $sql = "INSERT INTO GROUP_TABLE (DRIVER, OFFER_ID) VALUES ('".$user."',".$id.");";
  }else {
    $sql = "INSERT INTO GROUP_TABLE (RIDER_1, REQUEST_ID) VALUES ('".$user."', ".$id.");";
  }

  if(mysqli_query($con,$sql)) {
    $sql = "SELECT ID from GROUP_TABLE ORDER BY ID DESC LIMIT 1;";

    $result = mysqli_query($con,$sql);

    $row = mysqli_fetch_row($result);
    echo $row[0];
    echo " ";
    echo $id;
    } else {
      echo "Error while insertion... ".$sql." ".mysqli_error($con);
  }

?>
