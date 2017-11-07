<?php

    $to = $_POST['to'];
    $from = $_POST['from'];
    $subject = $_POST['subject'];
    $message .= $_POST['message'];
    $headers = "From:" . $from;

    //sends an email to the specified "to", with the corresponding "from," "subject," and "message" fields.
    mail($to,$subject,$message,$headers);

?>