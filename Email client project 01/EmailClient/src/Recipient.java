import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class Recipient {
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
			fw = new FileWriter("C:\\Users\\DELL\\eclipse-workspace\\EmailClient\\src\\clientList.txt",true);
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
