package service;

import conf.Mailer;

public class EmailSendingService {

    public void sendEmail(String emailSubject, String emailContent, String emailReceiver) {
        String senderEmail = System.getenv("JSP_MAIL_SENDER");
        String senderPassword = System.getenv("JSP_MAIL_PWD");
        Mailer.send(senderEmail, senderPassword, emailReceiver, emailSubject, emailContent);
    }
}
