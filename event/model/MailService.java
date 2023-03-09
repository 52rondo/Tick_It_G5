package tw.com.tibame.event.model;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

import javax.crypto.SecretKey;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailService {

    private final static String HOST = "smtp.gmail.com";
    private final static String AUTH = "true";
    private final static String PORT = "587";
    private final static String STARTTLE_ENABLE = "true";
    private final static String SENDER = "YFa025NHO5mG+v+nMadTJS4SpbFIkVb2OgK8JotlCn0=";
    private final static String PASSWORD = "volJ89/PI8EUN/Y9PG4ufL0E7S2myxs8GR6c6bzsgPM=";

    public void sendMail(String recipients, String mailSubject, String mailBody ) {
        
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", STARTTLE_ENABLE);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        try {
            
            SecretKey key = EncryptUtils.generateKey("tickit");
            String deSENDER = EncryptUtils.decrypt(SENDER, key);
            String dep6d = EncryptUtils.decrypt(PASSWORD, key);
            
            
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(deSENDER, dep6d);
                }
            });
            
            Message message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(deSENDER));
            
            message.setHeader("Content-Type", "text/html; charset=UTF-8");
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            message.setSubject(MimeUtility.encodeText(mailSubject, StandardCharsets.UTF_8.toString(), "B"));
            
            MimeBodyPart messageBody = new MimeBodyPart();
            messageBody.setContent(mailBody, "text/html; charset=UTF-8");
            
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBody);
            
            /*
            File file = new File("picture/20211214151834.jpg");
            MimeBodyPart messageImgBody = new MimeBodyPart();
            DataSource fds = new FileDataSource(file);
            messageImgBody.setDataHandler(new DataHandler(fds));
            messageImgBody.setHeader("Content-ID", "<image>");
            messageImgBody.setFileName(file.getName());
            multipart.addBodyPart(messageImgBody);
            */
            
            message.setContent(multipart);
            
            //Transport transport = mailSession.getTransport("smtp");
            
            Transport.send(message);
            /*
            try {
                transport.connect();
                transport.sendMessage(message, message.getAllRecipients());
            } finally {
                transport.close();
            }
            */
            
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
