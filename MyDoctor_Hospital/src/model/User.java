package model;


public class User {
	private String id;
	private String name;
	private String userName;
	private String userType;
	
	
	public User() {
		
	}
	
	public User(String id, String name, String userName, String userType) {
		super();
		this.id = id;
		this.name = name;
		this.userName = userName;
		this.userType = userType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
	
}