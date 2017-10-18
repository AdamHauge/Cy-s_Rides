<html>
    <head>
      <title>Create Offer</title>
    </head>
    <body>
        <?php

        $cost = $_POST["cost"];
        $email = $_POST["email"];
        $destination = $_POST["destination"];
        $latitude = $_POST["latitude"];
        $longitude = $_POST["longitude"];
        $description = $_POST["description"];
        $date = $_POST["date"];

        $host="mysql.cs.iastate.edu";
        $port=3306;
        $socket="";
        $username = 'dbu309sab5';
        $password = 'VRCc@3V2';
        $dbname = 'db309sab5';

        $conn = new mysqli($host, $username, $password, $dbname, $port, $socket) or die('Could not connect to database server'.mysqli_connect_error);

        $sql = "INSERT INTO OFFER_TABLE (COST, EMAIL, DESTINATION, LATITUDE, LONGITUDE, DESCRIPTION, DATE) VALUES (".$cost.",'".$email."','".$destination."','".$latitude"','".$longitude"','".$description."',".$date.");";

        if(mysqli_query($conn,$sql)) {
            echo "Data insertion success...";
        } else {
            echo "Error while insertion..." . $sql;
        }

        mysqli_close($conn);

        ?>
    </body>
</html>