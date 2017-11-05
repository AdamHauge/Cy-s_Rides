package service;

public interface GmailSenderService {

    public void sendData(String name, String to, String from, String subject, String message);

}
