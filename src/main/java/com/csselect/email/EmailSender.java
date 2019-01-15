package com.csselect.email;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


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
    public static void sendEmail(String sender, String recipient, String header, String message) {
        Properties p = new Properties();
        Session s = javax.mail.Session.getInstance(p);
        MimeMessage mime = new javax.mail.internet.MimeMessage(s);
        try {
            javax.mail.internet.InternetAddress from = new InternetAddress(sender, sender);
            mime.setFrom(from);
            mime.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            mime.addRecipient(Message.RecipientType.CC, new InternetAddress(sender));
            mime.setSubject(header);
            mime.setText(message);
            Transport.send(mime);
        } catch (Exception e) {
            System.out.println("Email failure: " + e.toString());
        }
    }
}
