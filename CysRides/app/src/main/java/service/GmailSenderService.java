package service;

public interface GmailSenderService {

    void sendEmail(String to, String from, String subject, String body);

}
