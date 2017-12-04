<?php

$host="mysql.cs.iastate.edu";
$port=3306;
$socket="";
$username = 'dbu309sab5';
$password = 'VRCc@3V2';
$dbname = 'db309sab5';

$con = new mysqli($host, $username, $password, $dbname, $port, $socket) or die('Could not connect to database server'.mysqli_connect_error);

$sql = "SELECT gt.*,
        ot.COST, ot.OFFER_EMAIL, ot.DESTINATION, ot.START, ot.DESCRIPTION, ot.DATE,
        rt.NUM_BAGS, rt.REQUEST_EMAIL, rt.DESTINATION, rt.START, rt.DESCRIPTION, rt.DATE
        FROM GROUP_TABLE gt
        LEFT JOIN OFFER_TABLE ot
          ON ot.GROUP_ID = gt.ID
        LEFT JOIN REQUEST_TABLE rt
          ON rt.GROUP_ID = rt.ID;";
$arr = array();
$result = $con->query($sql);

if($result->num_rows > 0){
  while($row = $result->fetch_assoc()){
      array_push($arr, $row);
  }
}else{
  echo "0 results";
  return;
}

echo json_encode($arr);


 ?>
