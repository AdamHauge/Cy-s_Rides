package service;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cysrides.cysrides.LoginActivity;

public class GmailSenderServiceImpl implements GmailSenderService {

    public void sendData(String name, String to, String from, String subject, String message)
    {

    }

}