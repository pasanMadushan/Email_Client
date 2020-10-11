import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.Scanner;

// your index number

//import libraries

public class Email_Client {

      public static void main(String[] args) throws Exception {
    	  //loading the recipients to the program using an arrayList
    	  //
    	  ArrayList<Recipient> ClientList= new ArrayList<Recipient>();
    	  ArrayList<hasBday> bdayList = new ArrayList<hasBday>();
    	  BufferedReader br = null;
    	  
    	  try {
    		  br = new BufferedReader(new FileReader("C:\\Users\\DELL\\eclipse-workspace\\EmailClient\\src\\clientList.txt"));
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
    	  }finally {
    		  br.close();
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
    			  
    			  ArrayList<Mail>mails2 = EmailUtility.readFile();
         	   		mails2.add(mail);
         	   		
         	   		EmailUtility.writeToFile(mails2);
    			  
    		  }
    	  }
    	  
    	  
    	  
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter option type: \n"
                  + "1 - Adding a new recipient\n"
                  + "2 - Sending an email\n"
                  + "3 - Printing out all the recipients who have birthdays\n"
                  + "4 - Printing out details of all the emails sent\n"
                  + "5 - Printing out the number of recipient objects in the application");

            int option = scanner.nextInt();
           
            switch(option){
                  case 1:
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
                      
                      
                  case 2:
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
               	   		
               	   		
               	   		ArrayList<Mail>mails = EmailUtility.readFile();
               	   		
               	   		Mail newmail = new Mail(mailadress,mailsub,mailcontent,currentDate1);
               	   		mails.add(newmail);
               	   		
               	   		EmailUtility.writeToFile(mails);
               	   		
                	  
                	  System.out.println("Mail sent successfully");
                	  
                      // input format - email, subject, content
                      // code to send an email
                      break;
                      
                  case 3:
                	  
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
                  case 4:
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
                  case 5:
                	  
                	  System.out.println(ClientList.size());
                      // code to print the number of recipient objects in the application
                      break;

            }
            
            
            

            // start email client
            // code to create objects for each recipient in clientList.txt
            // use necessary variables, methods and classes

        }
      
}

// create more classes needed for the implementation (remove the  public access modifier from classes when you submit your code)