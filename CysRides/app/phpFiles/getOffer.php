<?php


  $cost = $_GET["cost"];
  $email = $_GET["email"];
  $destination = $_GET["destination"];
  $description = $_GET["description"];
  $date =$_GET["date"];


  $host="mysql.cs.iastate.edu";
  $port=3306;
  $socket="";
  $username = 'dbu309sab5';
  $password = 'VRCc@3V2';
  $dbname = 'db309sab5';

  $conn = new mysqli($host, $username, $password, $dbname, $port, $socket) or die('Could not connect to database server'.mysqli_connect_error);

  $sql = 'SELECT '

    echo "YAY";
    mysqli_close($conn);
?>
