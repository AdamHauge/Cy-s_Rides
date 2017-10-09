<html>
    <head>
      <title>Create User</title>
    </head>
    <body>
        <?php

        $netID = $_POST["netID"];
        $userPassword = $_POST["userPassword"];
        $confirmationCode = $_POST["confirmationCode"];
        $firstName = $_POST["firstName"];
        $lastName = $_POST["lastName"];
        $venmo = $_POST["venmo"];
        $profileDescription = $_POST["profileDescription"];
        $userType = $_POST["userType"];
        $userRating = $_POST["userRating"];

        $host="mysql.cs.iastate.edu";
        $port=3306;
        $socket="";
        $username = 'dbu309sab5';
        $password = 'VRCc@3V2';
        $dbname = 'db309sab5';

        $conn = new mysqli($host, $username, $password, $dbname, $port, $socket) or die('Could not connect to database server'.mysqli_connect_error);

        $sql = "INSERT INTO USER_TABLE (NETID, PASSWORD, CONFIRMATION_CODE, FIRST_NAME, LAST_NAME, VENMO, PROFILE_DESCRIPTION, USER_TYPE, USER_RATING) VALUES ('".$netID."','".$userPassword."','".$confirmationCode."','".$firstName."','".$lastName."','".$venmo."','".$profileDescription."','".$userType."',".$userRating.");";

        if(mysqli_query($conn,$sql)) {
            echo "Data insertion success...";
        } else {
            echo "Error while insertion..." . $sql;
        }

        mysqli_close($conn);

        ?>
    </body>
</html>