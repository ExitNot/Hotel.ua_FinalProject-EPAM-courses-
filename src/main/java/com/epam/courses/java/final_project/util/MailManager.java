package com.epam.courses.java.final_project.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.epam.courses.java.final_project.util.constant.Constant.LOG_ERROR;

public class MailManager {

    private static final MailManager INSTANCE;
    private static final Logger log = LogManager.getLogger(LOG_ERROR);
    private static Session session;
    private static Properties prop;

    private final String footer = "\nPlease, do not respond for this email.\n\n" +
            " - Sincerely yours Unnamed Hotel.";

    static {
        INSTANCE = new MailManager();
    }

    private MailManager() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        prop = new Properties();
        try(InputStream resourceStream = loader.getResourceAsStream("app.properties")) {
            prop.load(resourceStream);
        } catch (IOException e) {
            log.error(e);
        }

        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");

        session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(prop.getProperty("email.address"), prop.getProperty("email.password"));
            }
        });
    }

    public static MailManager getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        MailManager.getInstance().sendEmail("kostyakolchenko@outlook.com", "hello");
    }

    public void sendEmail(String to, String mailText) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(prop.getProperty("email.address")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Unnamed Hotel info mail");
            message.setText(mailText);
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("Cant send mail", e);
        }
    }

    public void sendEmailVerification(String to, String name, String surname, String verificationCode) {
        String url="http://localhost:8080/Home/emailVerify?email=" + to + "&code=" + verificationCode;

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(prop.getProperty("email.address")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Unnamed Hotel email verification");
            message.setContent(
                    "Dear " + surname + " " + name +
                    "<br/>Your request for registration was accepted." +
                    "Please follow the link below to verify your email address:<br/><br/>" +
                    "<a href='"+url+"'>verify</a><br/><br/>" + footer, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("Cant send mail", e);
        }
    }

    public static String requestResponseMailTemplate(String name, String surname) {
        return "Dear " + surname + " " + name +
                "\nYour request was accepted by our manager. " +
                "Please check \"My requests\" page.\n" +
                "If this room type and price are suitable " +
                "for you, please, accept and pay for it within 2 days." + getInstance().footer;
    }

    public static String creatingRequestMailTemplate(String name, String surname) {
        return "Dear " + surname + " " + name +
                "\nYour request for reservation was created. " +
                "You can find it on \"My requests\" page.\n" +
                "Our manager will response for your request in nearest time." + getInstance().footer;
    }
}
