package com.epam.courses.java.final_project.util;

import com.epam.courses.java.final_project.model.Reservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Properties;

import static com.epam.courses.java.final_project.util.constant.Constant.LOG_ERROR;

/**
 * Class provide functionality for sending emails.
 *
 * @author Kostiantyn Kolchenko
 */
public class MailManager {

    private static final MailManager INSTANCE;
    private static final Logger log = LogManager.getLogger(LOG_ERROR);
    private static Session session;
    private static Properties prop;

    private final String footerEN = "\n\nPlease, do not respond for this email.\n\n" +
            " - Sincerely yours Unnamed Hotel.";
    private final String footerRU = "\n\n\u041F\u043E\u0436\u0430\u043B\u0443\u0439\u0441\u0442\u0430, \u043D\u0435 \u043E\u0442\u0432\u0435\u0447\u0430\u0439\u0442\u0435 \u043D\u0430 \u044D\u0442\u043E\u0442 email.\n\n" +
            " - \u0421 \u0443\u0432\u0430\u0436\u0435\u043D\u0438\u0435\u043C, \u0432\u0430\u0448 Unnamed Hotel.";

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

    public void sendEmailVerification(String to, String name, String surname, String verificationCode, String lang) {
        String url="http://localhost:8080/Home/emailVerify?email=" + to + "&code=" + verificationCode;

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(prop.getProperty("email.address")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Unnamed Hotel email verification");
            if (lang.equals("ru"))
                message.setContent(
                        "\u0423\u0432\u0430\u0436\u0430\u0435\u043C\u044B\u0435 " + surname + " " + name +
                            "<br/>\u0412\u0430\u0448 \u0437\u0430\u043F\u0440\u043E\u0441 \u043D\u0430 \u0441\u043E\u0437\u0434\u0430\u043D\u0438\u0435 \u0430\u043A\u043A\u0430\u0443\u043D\u0442\u0430 - \u0431\u044B\u043B \u043F\u0440\u0438\u043D\u044F\u0442." +
                            "\u041F\u043E\u0436\u0430\u043B\u0443\u0439\u0441\u0442\u0430, \u043F\u0435\u0440\u0435\u0439\u0434\u0438\u0442\u0435 \u043F\u043E \u0441\u0441\u044B\u043B\u043A\u0435 \u043D\u0438\u0436\u0435, \u0447\u0442\u043E \u0431\u044B \u043F\u043E\u0434\u0442\u0432\u0435\u0440\u0434\u0438\u0442\u044C \u0432\u0430\u0448 email:<br/><br/>" +
                            "<a href='"+url+"'>\u043F\u043E\u0434\u0442\u0432\u0435\u0440\u0434\u0438\u0442\u044C</a><br/><br/>" + footerRU, "text/html; charset=utf-8");
            else
                message.setContent(
                        "Dear " + surname + " " + name +
                        "<br/>Your request for registration was accepted." +
                        "Please follow the link below to verify your email address:<br/><br/>" +
                        "<a href='"+url+"'>verify</a><br/><br/>" + footerEN, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("Cant send mail", e);
        }
    }

    public static String paymentMailTemplate(String name, String surname, Date from, Date to, String lang) {
        if (lang.equals("ru"))
            return "\u0423\u0432\u0430\u0436\u0430\u0435\u043C\u044B\u0435 " + surname + " " + name +
                    "\n\u0411\u043B\u0430\u0433\u043E\u0434\u0430\u0440\u0438\u043C \u0432\u0430\u0441 \u0437\u0430 \u043E\u043F\u043B\u0430\u0442\u0443 \u0431\u0440\u043E\u043D\u0438 \u043D\u0430 " +
                    from + " - " + to + "\n" +
                    "\u0412\u0430\u0448\u0430 \u0431\u0440\u043E\u043D\u044C \u0431\u044B\u043B\u0430 \u0443\u0441\u043F\u0435\u0448\u043D\u043E \u0441\u043E\u0437\u0434\u0430\u043D\u0430. \u0412\u044B \u043C\u043E\u0436\u0435\u0442\u0435 \u043D\u0430\u0439\u0442\u0438 \u0438\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u044E \u043E \u0431\u0440\u043E\u043D\u0435 \u043D\u0430 \u0441\u0442\u0440\u0430\u043D\u0438\u0446\u0435 \"\u041C\u043E\u0438 \u0440\u0435\u0437\u0435\u0440\u0432\u0430\u0446\u0438\u0438\"" +
                    getInstance().footerRU;

        return "Dear " + surname + " " + name +
                "\nThank you for payment for your reservation on " +
                from + " - " + to + " dates.\n" +
                "Your reservation was successfully created, you can found it on \"My reservations\" page" +
                getInstance().footerEN;
    }

    public static String requestResponseMailTemplate(String name, String surname, String lang) {
        if (lang.equals("ru"))
            return "\u0423\u0432\u0430\u0436\u0430\u0435\u043C\u044B\u0435 " + surname + " " + name +
                    "\n\u0412\u0430\u0448\u0430 \u0437\u0430\u043F\u0440\u043E\u0441 \u0431\u044B\u043B \u0440\u0430\u0441\u0441\u043C\u043E\u0442\u0440\u0435\u043D \u0438 \u043E\u0434\u043E\u0431\u0440\u0435\u043D \u043D\u0430\u0448\u0438\u043C \u043C\u0435\u043D\u0435\u0434\u0436\u0435\u0440\u043E\u043C. " +
                    "\u0412\u044B \u043C\u043E\u0436\u0435\u0442\u0435 \u043D\u0430\u0439\u0442\u0438 \u0438\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u044E \u043E \u0437\u0430\u043F\u0440\u043E\u0441\u0435 \u043D\u0430 \u0441\u0442\u0440\u0430\u043D\u0438\u0446\u0435 \"\u041C\u043E\u0438 \u0437\u0430\u043F\u0440\u043E\u0441\u044B\".\n" +
                    "\u0415\u0441\u043B\u0438 \u043D\u043E\u043C\u0435\u0440 \u0438 \u0446\u0435\u043D\u0430 \u0443\u0441\u0442\u0440\u0430\u0438\u0432\u0430\u044E\u0442 \u0432\u0430\u0441, \u043F\u043E\u0436\u0430\u043B\u0443\u0439\u0441\u0442\u0430 \u043F\u043E\u0434\u0442\u0432\u0435\u0440\u0434\u0438\u0442\u0435 \u0438 \u043E\u043F\u043B\u0430\u0442\u0438\u0442\u0435 " +
                    "\u0432\u0430\u0448\u0443 \u0431\u0440\u043E\u043D\u044C \u043D\u0435 \u0432 \u0442\u0435\u0447\u0435\u043D\u0438\u0438 2 \u0434\u043D\u0435\u0439." +
                    getInstance().footerRU;
        return "Dear " + surname + " " + name +
                "\nYour request was accepted by our manager. " +
                "Please check \"My requests\" page.\n" +
                "If this room type and price are suitable " +
                "for you, please, accept and pay for it within 2 days." +
                getInstance().footerEN;
    }

    public static String creatingRequestMailTemplate(String name, String surname, String lang) {
        if (lang.equals("ru"))
            return "\u0423\u0432\u0430\u0436\u0430\u0435\u043C\u044B\u0435 " + surname + " " + name +
                    "\n\u0412\u0430\u0448 \u0437\u0430\u043F\u0440\u043E\u0441 \u043D\u0430 \u0440\u0435\u0437\u0435\u0440\u0432\u0430\u0446\u0438\u044E \u0431\u044B\u043B \u043F\u0440\u0438\u043D\u044F\u0442. " +
                    "\u0412\u044B \u043C\u043E\u0436\u0435\u0442\u0435 \u043D\u0430\u0439\u0442\u0438 \u0435\u0433\u043E \u043D\u0430 \u0441\u0442\u0440\u0430\u043D\u0438\u0446\u0435 \"\u041C\u043E\u0438 \u0437\u0430\u043F\u0440\u043E\u0441\u044B\" \u0432\u0430\u0448\u0435\u0433\u043E \u043F\u0440\u043E\u0444\u0438\u043B\u044F.\n" +
                    "\u041D\u0430\u0448 \u043C\u0435\u043D\u0435\u0434\u0436\u0435\u0440 \u0440\u0430\u0441\u0441\u043C\u043E\u0442\u0440\u0438\u0442 \u0435\u0433\u043E \u0438 \u043E\u0442\u0432\u0435\u0442\u0438\u0442 \u0432 \u0431\u043B\u0438\u0437\u0436\u0430\u0439\u0448\u0435\u0435 \u0432\u0440\u0435\u043C\u044F." +
                    getInstance().footerRU;
        return "Dear " + surname + " " + name +
                "\nYour request for reservation was created. " +
                "You can find it on \"My requests\" page.\n" +
                "Our manager will response for your request in nearest time." +
                getInstance().footerEN;
    }
}
