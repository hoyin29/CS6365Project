package team.cs6365.payfive.model;

public class User {
	String name = "";
	String paypalId = "";

	public User(String name, String paypalId) {
		this.name = name;
		this.paypalId = paypalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPaypalId() {
		return paypalId;
	}

	public void setPaypalId(String paypalId) {
		this.paypalId = paypalId;
	}

}
