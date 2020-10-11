
public abstract class Official extends Recipient{

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
