<html>
    <head>
      <title>Create Offer</title>
    </head>
    <body>
        <?php

        $netID = $_POST["net-ID"];
        $password = $_POST["password"];
        $confirmationCode = $_POST["confirmation code"];
        $firstName = $_POST["first name"];
        $lastName = $_POST["last name"];
        $venmo = $_POST["venmo name"];
        $profileDescription = $_POST["profile description"];
        $userType = $_POST["user type"];
        $userRating = $_POST["user rating"];

        $host="mysql.cs.iastate.edu";
        $port=3306;
        $socket="";
        $username = 'dbu309sab5';
        $password = 'VRCc@3V2';
        $dbname = 'db309sab5';

        $conn = new mysqli($host, $username, $password, $dbname, $port, $socket) or die('Could not connect to database server'.mysqli_connect_error);

        $sql = "INSERT INTO USER_TABLE (NETID, PASSWORD, CONFIRMATION_CODE, FIRST_NAME, LAST_NAME, VENMO, PROFILE_DESCRIPTION, USER_TYPE, USER_RATING)
                VALUES('".$netID."','".$password."',".$confirmationCode.",'".$firstName."','".$lastName."','".$venmo."','".$profileDescription."',
                '".$userType."',".$userRating.");";

        if(mysqli_query($conn,$sql)) {
            echo "Data insertion success...";
        } else {
            echo "Error while insertion..." . $cost . $email. $destination. $description . $date;
        }

        mysqli_close($conn);

        ?>
    </body>
</html>