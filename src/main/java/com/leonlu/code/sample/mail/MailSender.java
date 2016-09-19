package com.leonlu.code.sample.mail;

import com.sun.mail.smtp.SMTPAddressFailedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * Maven dependencies:
 * 		<dependency>
 *		    <groupId>javax.mail</groupId>
 *		    <artifactId>javax.mail-api</artifactId>
 *		    <version>1.5.6</version>
 *		</dependency>
 *		<dependency>
 *			<groupId>com.sun.mail</groupId>
 *			<artifactId>javax.mail</artifactId>
 *			<version>1.5.6</version>
 *		</dependency>
 *
 */
public class MailSender {
	private String user;
	private String password;
	private Properties props;
	
	public MailSender(Properties props) {
		this.props = props;
		this.user = props.getProperty("mail.auth.user");
		this.password = props.getProperty("mail.auth.password");
	}
	
	public MailSender(String smtpHost, int stmpPort, String user, String password) {
		this.props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", stmpPort);
		props.put("mail.smtp.auth", "true");
		this.user = user;
		this.password = password;
	}

	public boolean send(String to, String subject, String content) {
		boolean bl = true;
		
		try {
			Session mailSession = null;
			mailSession = Session.getInstance(props, new MyAuthenticator(this.user, this.password));
			Message message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress());
			//message.setFrom(new InternetAddress(from)); 可以指定发送者的邮箱信息
			Address[] ttAddress = null;
			List addList = new ArrayList();
			if (to.indexOf(";") > 0) {
				String[] strs = to.split(";");
				for (int i = 0; i < strs.length; i++) {
					Address toAddress = new InternetAddress(strs[i]);
					addList.add(toAddress);
				}
			} else {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(to));
			}
			if ((addList != null) && (addList.size() > 0)) {
				ttAddress = new Address[addList.size()];
				for (int i = 0; i < addList.size(); i++) {
					ttAddress[i] = ((Address) addList.get(i));
				}
				message.addRecipients(Message.RecipientType.TO, ttAddress);
			}

			message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();

			mbp.setContent(content, "text/html;charset=UTF-8");
			mp.addBodyPart(mbp);
			message.setContent(mp);

			message.saveChanges();
			Transport.send(message, message.getAllRecipients());
		} catch (SMTPAddressFailedException es) {
			throw new RuntimeException("邮件地址错误:" + es.getMessage());
		} catch (SendFailedException ef) {
			throw new RuntimeException("邮件发送失败:" + ef.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			bl = false;
		}
		return bl;
	}

	class MyAuthenticator extends Authenticator {
		protected String id = "";
		protected String password = "";

		public MyAuthenticator(String id, String password) {
			this.id = id;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(this.id, this.password);
		}
	}
	
	public static void main(String[] args) {
		//a gmail sample
		Properties prop = new Properties();
		prop.put("mail.auth.user", "GMAIL_ACCOUNT@gmail.com");
		prop.put("mail.auth.password", "GMAIL_PASSWORD");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", 587);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.auth", "true");
		MailSender sender = new MailSender(prop);
		sender.send("RECEIVER_MAIL_ADDR", "A test mail", "the test content");
	
	}
}

