

public class OfficialFriend extends Official implements hasBday{
	
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
	
	


