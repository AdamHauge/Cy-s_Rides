<html>
    <head>
      <title>get Offers</title>
    </head>

    <body>

      <?php

      $host="mysql.cs.iastate.edu";
      $port=3306;
      $socket="";
      $username = 'dbu309sab5';
      $password = 'VRCc@3V2';
      $dbname = 'db309sab5';

      $conn = new mysqli($host, $username, $password, $dbname, $port, $socket) or die('Could not connect to database server'.mysqli_connect_error);

      $sql = 'SELECT * FROM OFFER_TABLE;';
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

      // array_push($result, array(
      //   "cost" => $rawResult['COST'],
      //   "email" => $rawResult['EMAIL'],
      //   "destination" => $rawResult['DESTINATION'],
      //   "description" => $rawResult['DESCRIPTION'],
      //   "date" => $rawResult['DATE']
      // ));






      // $query = mysqli_query($conn, $sql);
      // if(!$query){
      //   die('dead' . mysql_error);
      // }
      // $rawResult = mysql_fetch_array($query);
    //  echo json_encode($rawResult);




      // $result = array();
      // //COST, EMAIL, DESTINATION, DESCRIPTION, DATE
      // array_push($result, array(
      //   "cost" => $rawResult['COST'],
      //   "email" => $rawResult['EMAIL'],
      //   "destination" => $rawResult['DESTINATION'],
      //   "description" => $rawResult['DESCRIPTION'],
      //   "date" => $rawResult['DATE']
      // ));
      //
      // echo json_encode(array("result" =>$result));
//       mysqli_close($conn);
       $conn->close();
      ?>
    </body>
</html>
