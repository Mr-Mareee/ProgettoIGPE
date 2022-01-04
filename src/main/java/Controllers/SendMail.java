package Controllers;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class SendMail {

    public static boolean Send_mail(String mail,String Nome,String Cognome,String User) {

        final String username = "mpj.project.igpe@gmail.com";
        final String password = "Mpj_2021";
        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mpj.project.igpe@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(mail)
            );
            message.setSubject("Benvenuto in Mpj: "+User);
            message.setContent("<!DOCTYPE html>"
            					+ "<html>"
            						+ "<head><meta charset='utf-8'</head>"
            						+ "<body>"
            							+ "<h1>Gentile "+Nome+" "+Cognome+", benvenuto in Mpj!</h1>"
            						+ "</body"
            						+ "</html>",
                    			"text/html");
            Transport.send(message);
            return true;
            
        } catch (MessagingException e) {
            return false;
        }
    }
    public static boolean Send_CodiceDistruzione(String mail,String Codice,String User) {
        final String username = "mpj.project.igpe@gmail.com";
        final String password = "Mpj_2021";
        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mpj.project.igpe@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(mail)
            );
            message.setSubject("Questo è il tuo codice di Cancellazione");
            message.setContent("<!DOCTYPE html>"
            					+ "<html>"
            						+ "<head><meta charset='utf-8'</head>"
            						+ "<body>"
            							+ "<h1>Gentile "+User+",il suo codice di cancellazione è: "+Codice+".</h1>"
            						+ "</body"
            						+ "</html>",
                    			"text/html");
            Transport.send(message);
            return true;
            
        } catch (MessagingException e) {
            return false;
        }
    }
    public static boolean Send_MessaggioGenerico(String mail,String Oggetto,String Testo) {
        final String username = "mpj.project.igpe@gmail.com";
        final String password = "Mpj_2021";
        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mpj.project.igpe@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(mail)
            );
            message.setSubject(Oggetto);
            message.setContent("<!DOCTYPE html>"
            					+ "<html>"
            						+ "<head><meta charset='utf-8'</head>"
            						+ "<body>"
            							+ "<h1>"+Testo+".</h1>"
            						+ "</body"
            						+ "</html>",
                    			"text/html");
            Transport.send(message);
            return true;
            
        } catch (MessagingException e) {
            return false;
        }
    }
}

