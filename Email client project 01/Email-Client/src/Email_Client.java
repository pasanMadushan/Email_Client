// 180364R

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import java.io.Serializable;
import java.io.FileWriter;
import java.io.PrintWriter;

//import libraries

public class Email_Client {

      public static void main(String[] args) throws Exception {
    	  //loading the recipients to the program using an arrayList
    	  //
    	  ArrayList<Recipient> ClientList= new ArrayList<Recipient>();
    	  ArrayList<hasBday> bdayList = new ArrayList<hasBday>();
    	  BufferedReader br = null;
    	  
    	  boolean check = new File("clientList.txt").exists();
    	  if(check==true) {
    		  
    		  try {
        		  br = new BufferedReader(new FileReader("clientList.txt"));
        		  String line;
        		  while ((line=br.readLine())!=null) {
        			  
        			  String[] list01 = line.split(":");
                	  String[]list02=list01[1].split(",");
                	 
                	  
                	  if (list01[0].equals("Office_friend")) {
                		  OfficialFriend OF1 = new OfficialFriend(list02[0],list02[1],list02[2],list02[3]);
                		  
                		  bdayList.add(OF1);
                		  ClientList.add(OF1);
                		  
                	  }
                	  else if (list01[0].equals("Official")) {
                		  OfficialRecipient OR1= new OfficialRecipient(list02[0],list02[1],list02[2]);
                		  
                		  ClientList.add(OR1);
                	  }
                	  else if (list01[0].equals("Personal")) {
                		  PersonalRecipient PR1  = new PersonalRecipient(list02[0],list02[1],list02[2],list02[3]);
                		  
                		  bdayList.add(PR1);
                		  ClientList.add(PR1);
        		  }}
        	  }
        	  finally {
        		  br.close();
        	  }
    		 
    	  }
    	  
    	  
    	  
    	  
    	  //sending bday greetings on the correct day
    	  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  //taking the current date
    	   LocalDateTime now = LocalDateTime.now();
    	   String currentDate= dtf.format(now);
    	   
    	  for(hasBday R:bdayList) {
    		  
    		  if(R.getBday().equals(currentDate)) {
    			  Mail mail = null;
    			  
    			  if(R.getClass().toString().equals("class OfficialFriend"))  {
    				  EmailUtility.sendMail( ((OfficialFriend)R).getEmail() ,"Birthday Greeting",R.getGreeting());
    				  mail=new Mail(((OfficialFriend)R).getEmail(),"Birthday Greeting",R.getGreeting(),currentDate);
    			  }
    			  else if(R.getClass().toString().equals("class PersonalRecipient")) {
    				  EmailUtility.sendMail( ((PersonalRecipient)R).getEmail() ,"Birthday Greeting",R.getGreeting());
    				  mail=new Mail(((PersonalRecipient)R).getEmail(),"Birthday Greeting",R.getGreeting(),currentDate);
    			  }
    			  
    			  //Serialization part
    			  
    			  ArrayList<Mail>mails2 = EmailUtility.readFile();
         	   		mails2.add(mail);
         	   		
         	   		EmailUtility.writeToFile(mails2);
    			  
    		  }
    	  }
    	  
    	  
			
            Scanner scanner = new Scanner(System.in);
            boolean flag=true;
            
            while(flag) {
            
            
            
            System.out.println("\nEnter option type: \n"
                  + "1 - Adding a new recipient\n"
                  + "2 - Sending an email\n"
                  + "3 - Printing out all the recipients who have birthdays\n"
                  + "4 - Printing out details of all the emails sent\n"
                  + "5 - Printing out the number of recipient objects in the application\n"
                  +"6 - quit");
  

            String option = scanner.nextLine();
            
      
            
          
            switch(option){
                  case "1":
                	  Scanner scan = new Scanner(System.in);
                	  System.out.println("Enter recipient details as\n  Official: <name>,<email>,<designation>  or \n"
                	  		+ "  Office_friend: <name>,<email>,<designation>,<birthday>  or\n"
                	  		+ "  Personal: <name>,<nickname>,<email>,<birthday>"
                	  		);
                      
                	  String details = scan.nextLine();
                      // Use a single input to get all the details of a recipient
                	  
                	  String[] list1 = details.split(":");
                	 
                	  String[]list2=list1[1].split(",");
                	 
                	  if (list1[0].equals("Official")) {
                		  OfficialRecipient OR= new OfficialRecipient(list2[0],list2[1],list2[2]);
                		  OR.writeFile(OR.writingString);
                		  ClientList.add(OR);
                		  
                	  }
                	  else if (list1[0].equals("Office_friend")) {
                		  OfficialFriend OF = new OfficialFriend(list2[0],list2[1],list2[2],list2[3]);
                		  OF.writeFile(OF.writingString);
                		  bdayList.add(OF);
                		  ClientList.add(OF);
                		
                	  }
                	  else if (list1[0].equals("Personal")) {
                		  PersonalRecipient PR  = new PersonalRecipient(list2[0],list2[1],list2[2],list2[3]);
                		  PR.writeFile(PR.writingString);
                		  bdayList.add(PR);
                		  ClientList.add(PR);
                	  }
                      break;
                      
                      
                  case "2":
                	  Scanner scan1= new Scanner(System.in);
                	  System.out.println("Enter recipients mail address: ");
                	  String mailadress = scan1.nextLine();
                	  System.out.println("Enter the subject: ");
                	  String mailsub = scan1.nextLine();
                	  System.out.println("Enter the content: ");
                	  String mailcontent = scan1.nextLine();
                	  EmailUtility.sendMail(mailadress, mailsub, mailcontent);
                	  
                	  	DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");  //taking the current date
               	   		LocalDateTime now1 = LocalDateTime.now();
               	   		String currentDate1= dtf1.format(now1);
               	   		
               	   		// Serialization part
               	   		ArrayList<Mail>mails = EmailUtility.readFile();
               	   		
               	   		Mail newmail = new Mail(mailadress,mailsub,mailcontent,currentDate1);
               	   		mails.add(newmail);
               	   		
               	   		EmailUtility.writeToFile(mails);
               	   		
                	  
                	  System.out.println("Mail sent successfully");
                	  
                      // input format - email, subject, content
                      // code to send an email
                      break;
                      
                  case "3":
                	  
                	  Scanner scan2 = new Scanner(System.in);
                	  System.out.println("Enter the date to get the recipients whose bday lies on today: ");
                	  String queryDate= scan2.nextLine();
                      // input format - yyyy/MM/dd (ex: 2018/09/17)
                	  for(hasBday B:bdayList) {
                		  if(B.getBday().equals(queryDate)) {
                			  System.out.println(((Recipient)B).getName());
                		  }
                	  }
                      // code to print recipients who have birthdays on the given date
                      break;
                  case "4":
                	  Scanner scan4 = new Scanner(System.in);
                	  System.out.println("Enter the date to get the emails on that day: ");
                	  String qdate = scan4.nextLine();
                	  for(Mail a:EmailUtility.readFile()) {
                		  if (a.getDate().equals(qdate)) {
                			  System.out.println(a.toString());
                		  }
                	  }
                	  
                	  
                      // input format - yyyy/MM/dd (ex: 2018/09/17)
                      // code to print the details of all the emails sent on the input date
                      break;
                  case "5":
                	  
                	  System.out.println(ClientList.size());
                      // code to print the number of recipient objects in the application
                      break;
                      
                  case "6":
                	  flag=false;
                	  break;
            }
            
           
            }
           
            

            // start email client
            // code to create objects for each recipient in clientList.txt
            // use necessary variables, methods and classes

        }
      
}

// create more classes needed for the implementation (remove the  public access modifier from classes when you submit your code)







class EmailUtility {

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
		boolean check1 = new File("Mail.bin").exists();
		if(check1==true) {
		ObjectInputStream OIS = new ObjectInputStream(new FileInputStream("Mail.bin"));	
		ArrayList<Mail> mailist=new ArrayList<>();
		mailist=(ArrayList<Mail>)OIS.readObject();
		OIS.close();
		return mailist;
		
		}
		
		else {
			File file = new File("Main.bin");
			ArrayList<Mail> mailist=new ArrayList<>();
			return mailist;
		}
	}
		
}


class Mail implements Serializable{
	
	private String subject;
	private String message;
	private String recipient;
	private String date;
	
	
	public Mail(String recipient,String subject,String message,String date) {
		this.message=message;
		this.recipient=recipient;
		this.subject=subject;
		this.date=date;
		
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getDate() {
		return date;
	}

	@Override
	public String toString() {
		return recipient+"-"+subject+"-"+message;
	}
		
}



interface hasBday {
	
	public String getBday();
	
	public String getGreeting();
	  
}




abstract class Recipient {
	private String name;
	private String email;
	protected String writingString;
	
	public  Recipient(String name,String email) {
		this.email=email;
		this.name=name;
	}
	
	public void writeFile(String string) {
		FileWriter fw;
		try {
			fw = new FileWriter("clientList.txt",true);
			PrintWriter pw  = new PrintWriter(fw);
			pw.printf(string+"\n");
			pw.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
}



abstract class Official extends Recipient{

	private String designation;
	public Official(String name, String email,String designation) {
		super(name, email);
		this.designation=designation;
		
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
}



class OfficialFriend extends Official implements hasBday{
	
	private String bday;
	private static String greeting = "Wish you a Happy Birthday. Madhshan R.P.P.V";
	
	public OfficialFriend(String name, String email, String designation,String bday) {
		super(name, email, designation);
		this.bday=bday;
		this.writingString="Office_friend: "+name+","+email+","+designation+","+bday;
		
	}

	public String getBday() {
		return bday;
	}

	public void setBday(String bday) {
		this.bday = bday;
	}
	
	public String getGreeting() {
		return greeting;
	}
	
}	
	
	

class PersonalRecipient extends Recipient implements hasBday{
	
	private String nickName;
	private String bday;
	private static String greeting = "Hugs and love on your birthday. Madhushan R.P.P.V";

	public PersonalRecipient(String name ,String nickname,String email,String bday) {
		super(name, email);
		this.nickName=nickname;
		this.bday=bday;
		this.writingString="Personal: "+name+","+nickname+","+email+","+bday;
		
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getBday() {
		return bday;
	}

	public void setBday(String bday) {
		this.bday = bday;
	}
	
	public String getGreeting() {
		return greeting;
	}	
}



class OfficialRecipient extends Official{
	public OfficialRecipient(String name, String email, String designation) {
		super(name, email, designation);
		this.writingString="Official: "+name+","+email+","+designation;
		
	}
}



