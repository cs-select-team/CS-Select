package com.csselect.email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



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

    }

    private static void postMail(Session session, String recipient, String subject, String message) throws MessagingException {
        Message msg = new MimeMessage(session);

        InternetAddress addressRecipient = new InternetAddress(recipient);
        msg.setRecipient(Message.RecipientType.TO, addressRecipient);

        msg.setSubject(subject);
        msg.setContent(message, "text/plain");
        Transport.send(msg);

    }
}
