package tw.com.tibame.main;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
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
	//SENDER 是送到誰的EMAIL
	private final static String SENDER = "52rondo@gmail.com";
	//從google拿來的FOR應用程式密碼
	private final static String PASSWORD = "jmnxvkgycalsyuzd";

//  設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
	public void sendMail(String recipients, String mailSubject, String mailBody) {
//		String recipientCcs = "副本mail";
		Properties props = new Properties();
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.auth", AUTH);
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.starttls.enable", STARTTLE_ENABLE);
		props.put("mail.smtp.ssl.trust", HOST);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

//      設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER, PASSWORD);
			}
		};

		Session session = Session.getInstance(props, authenticator);
		Message message = new MimeMessage(session);

		try {
//			設定Email Message start

//			設定寄件人、收件人、副本、主旨
			message.setSentDate(new Date());
			message.setHeader("Content-Type", "text/html; charset=UTF-8");
			message.setFrom(new InternetAddress(SENDER));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
//			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(recipientCcs));
//          https://javaee.github.io/javamail/docs/api/javax/mail/internet/MimeUtility.html#encodeText-java.lang.String-java.lang.String-java.lang.String- (第三個參數參考API文件)
			message.setSubject(MimeUtility.encodeText(mailSubject, StandardCharsets.UTF_8.toString(), "B"));

//			first part (text)
			MimeBodyPart messageBody = new MimeBodyPart();
			messageBody.setContent(mailBody, "text/html; charset=UTF-8");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBody);

////          second part (the image) 可根據自己需要決定是否要加這段 (不需要就註解至85行)
//			File file = new File("picture/20211214151834.jpg");
//			MimeBodyPart messageImgBody = new MimeBodyPart();
//			DataSource fds = new FileDataSource(file);
//
//			messageImgBody.setDataHandler(new DataHandler(fds));
//			messageImgBody.setHeader("Content-ID", "<image>");
//			messageImgBody.setFileName(file.getName());
//
////          add image to the multipart
//			multipart.addBodyPart(messageImgBody);
//
			message.setContent(multipart);

//   		寄出email
			Transport transport = session.getTransport("smtp");
			try {
				transport.connect();
				transport.sendMessage(message, message.getAllRecipients());
			} finally {
				transport.close();
			}

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

//	public static void main(String args[]) {
//
//		String to = "52rondo@gmail.com";
//
//		String subject = " TICK IT 帳號啟用";
//
//		String ch_name = "親愛的使用者 ";
//		String passRandom = genAuthCode();
//		String messageText = "您好 " + ch_name + "\n\n\n\n\n" +
//							" 您的帳號啟用碼為: " + passRandom + "\n" ;
//
//		MailService mailService = new MailService();
//		mailService.sendMail(to, subject, messageText);
//	}
	
	public String genAuthCode() {
		int[] code = new int[5];
		String codeS = "";
		for (int i = 0; i < 5; i++) {
			int rand = 0;
			do {
				rand = (int) Math.round(Math.random() * 92);
				if (rand == 0) {
				} else {
					code[i] = rand;
				}
			//includes only numbers & capitalized a-z, excluding all others
			} while (rand < 48 || (57 < rand) && (rand < 65) ||  (rand > 90));
//			System.out.print((char) (code[i]));
		//put the chars into a string 
			codeS += (char) (code[i]);
		}
		System.out.println("Generated Verification Code: " + codeS);
		return codeS;
	}
	
	

}
