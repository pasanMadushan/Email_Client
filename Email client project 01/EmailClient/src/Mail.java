import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mail implements Serializable{
	
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
