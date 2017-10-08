<html>
<head>
  <title>php server</title>
</head>

<body>
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

  $conn = new mysqli($host, $username, $password, $dbname, $port, $socket) or die('Could not connect to database server'.mysqli_connect_error);

    echo "createUser";
    mysqli_close($conn);
  ?>
</body>

</html>
