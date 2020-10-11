public class PersonalRecipient extends Recipient implements hasBday{
	
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
