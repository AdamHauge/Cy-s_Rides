<?php

    $host="mysql.cs.iastate.edu";
    $port=3306;
    $socket="";
    $username = 'dbu309sab5';
    $password = 'VRCc@3V2';
    $dbname = 'db309sab5';

    $conn = new mysqli($host, $username, $password, $dbname, $port, $socket) or die('Could not connect to database server'.mysqli_connect_error);

    $post_id = mysqli_real_escape_string($conn, $_POST['id']);

    $sql = "SELECT * FROM GROUP_TABLE WHERE REQUEST_ID='".$post_id."';";
    $result = $conn->query($sql);
    $jsonArr = array();

    if ($result->num_rows > 0) {
    // output data of each row
        while($row = $result->fetch_assoc()) {
            array_push($jsonArr, $row);
        }
    } else {
        echo "0 results";
    }
      
    echo $json = json_encode($jsonArr);

    $conn->close();
?>