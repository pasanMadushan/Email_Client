import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtility {

	public static ArrayList<Mail> mails = new ArrayList<Mail>();
	
	
	public static void sendMail(String recipient,String subject,String strmessage) throws Exception {
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port","587");
		
		String myAccount = "practicleCSE@gmail.com";
		String password = "CSE@2018";
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccount,password);
			}
		});
		
		Message message = prepareMessage(session,myAccount,recipient,subject,strmessage);
		Transport.send(message);	
	}

	private static Message prepareMessage(Session session, String myAccount, String recipient,String subject,String strmessage) {
		
		try {
			Message message = new MimeMessage(session);	
			message.setFrom(new InternetAddress(myAccount));
			message.setRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
			message.setSubject(subject);
			message.setText(strmessage);
			return message;
			
			
		}catch(Exception e) {
			Logger.getLogger(EmailUtility.class.getName()).log(Level.SEVERE,null,e);
		}
		
		return null;
	}
	
	
	
	
	public static void writeToFile(ArrayList<Mail> list1) throws IOException{
		
		ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream("Mail.bin"));
		OOS.writeObject(list1);
	}
	
	public static ArrayList<Mail> readFile() throws IOException,ClassNotFoundException {
		ObjectInputStream OIS = new ObjectInputStream(new FileInputStream("Mail.bin"));
		
		ArrayList<Mail> mailist=new ArrayList<>();
		mailist=(ArrayList<Mail>)OIS.readObject();
		OIS.close();
		return mailist;
		
			
	}
		
}
