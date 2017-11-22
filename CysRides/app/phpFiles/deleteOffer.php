<?php

    $id = $_POST["id"];

    $host="mysql.cs.iastate.edu";
    $port=3306;
    $socket="";
    $username = 'dbu309sab5';
    $password = 'VRCc@3V2';
    $dbname = 'db309sab5';

    $conn = new mysqli($host, $username, $password, $dbname, $port, $socket) or die('Could not connect to database server'.mysqli_connect_error);

    $sql = "DELETE FROM OFFER_TABLE WHERE ID=".$id.";";
    if(mysqli_query($con,$sql)) {
        echo "Data insertion success...";
    } else {
        echo "Error while insertion... ".$sql." ".mysqli_error($con);
    }

    $conn->close();
?>