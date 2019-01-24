package com.csselect.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Class handling the sending of emails
 */
public final class EmailSender {

    private EmailSender() {
        //No instances of this class should be instantiated
    }

    /**
     * Sends an email with the given heading and message to the recipient
     * @param recipient email address to send the mail to
     * @param header the emails header
     * @param message the emails message
     */
    public static void sendEmail(String recipient, String header, String message) {
        Email email = new SimpleEmail();
        email.setStartTLSRequired(true);
        email.setHostName("mail.gmx.net");
        email.setSslSmtpPort("465");
        email.setAuthenticator(new DefaultAuthenticator("csselect@gmx.de", "PSEWs2018/19"));
        email.setSSLOnConnect(true);
        try {
            email.getMailSession().getProperties().put("mail.smtp.auth", "true");
            email.getMailSession().getProperties().put("mail.smtp.**ssl.enable", "true");
            email.getMailSession().getProperties().put("mail.smtp.**ssl.required", "true");
            email.setFrom("csselect@gmx.de");
            email.setSubject(header);
            email.setMsg(message);
            email.addTo(recipient);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
