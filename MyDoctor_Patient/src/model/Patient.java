package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Patient  {

	public Connection connect()
	 {
		Connection con = null;
	 try
	 {
		 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/patient", "root", "");
	 }
	 catch (Exception e)
	 {
		 e.printStackTrace();}
	 	return con;
	 }
	

	
	public String insertDetails(String name, String gender,String phone,String NIC,String email,String username,String password)
	 {
		String output = "";
	 try
	 {
		 Connection con = connect();
	 if (con == null)
	 {
		 return "Error while connecting to the database for inserting.";
	 }
	 // create a prepared statement
	 
	 String query = "insert into patientdetails(patientId,name,gender,phone,NIC,email,username,password)" + " values (?, ?, ?,?,?,?,?,?)";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	 // binding values
	 preparedStmt.setInt(1, 0);
	 preparedStmt.setString(2, name);
	 preparedStmt.setString(3, gender);
	 preparedStmt.setString(4, phone);
	 preparedStmt.setString(5, NIC);
	 preparedStmt.setString(6, email);
	 preparedStmt.setString(7, username);
	 preparedStmt.setString(8, password);
	 
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Inserted successfully";
	 
	 }
	 catch (Exception e)
	 {
		 output = "Error while inserting the item.";
		 System.err.println(e.getMessage());
	 }
	 	return output;
	 } 

public String readDetails()
	 {
		String output = "";
	 try
	 {
		 Connection con = connect();
	 if (con == null)
	 {
		 return "Error while connecting to the database for reading."; 
	 }
	 // Prepare the html table to be displayed
	 output = "<table border=\"1\"><tr><th>name</th><th>gender</th><th>phone</th><th>NIC</th><th>email</th><th>username</th><th>email</th><th>password</th><th>Remove</th></tr>";
	 String query = "select * from patientdetails";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
		 String patientId = Integer.toString(rs.getInt("patientId"));
		 String name = rs.getString("name");
		 String gender = rs.getString("gender");
		 String phone =  rs.getString("phone");
		 String NIC = rs.getString("NIC");
		 String email = rs.getString("email");
		 String username = rs.getString("username");
		 String password = rs.getString("password");
	 
		 // Add into the html table
		 output += "<tr><td>" + name + "</td>";
		 output += "<td>" + gender + "</td>";
		 output += "<td>" + phone + "</td>";
		 output += "<td>" + NIC + "</td>";
		 output += "<td>" + email + "</td>";
		 output += "<td>" + username + "</td>";
		 output += "<td>" + password + "</td>";
	 
	 // buttons
	 output += "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"+ "<td><form method=\"post\" action=\"items.jsp\">" + "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">" + "<input name=\"userId\" type=\"hidden\" value=\"" + email + "\">" + "</form></td></tr>";
	 }
	 con.close();
	 
	 // Complete the html table
	 output += "</table>";
	 }
	 
	 catch (Exception e)
	 {
	 		output = "Error while reading the items.";
	 		System.err.println(e.getMessage());
	 }
	 return output;
	 } 


	
public String updateDetails(String patientId, String name, String gender,String phone,String NIC,String email,String username,String password)
	 {
	 	String output = "";
	 try
	 {
		 Connection con = connect();
	 if (con == null)
	 {
		 return "Error while connecting to the database for updating."; 
	 }
	 // create a prepared statement
	 String query = "UPDATE patientdetails SET name=?,gender=?,phone=?,NIC=? ,email=?,username=?,password=? WHERE patientId=? ";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	 // binding values
	 preparedStmt.setString(1, name);
	 preparedStmt.setString(2, gender);
	 preparedStmt.setString(3,phone); 
	 preparedStmt.setString(4, NIC);
	 preparedStmt.setString(5, email);
	 preparedStmt.setString(6, username);
	 preparedStmt.setString(7, password);
	 preparedStmt.setInt(8, Integer.parseInt(patientId)); 

	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Updated successfully";
	 }
	 	catch (Exception e)
	 {
		 output = "Error while updating the item.";
		 System.err.println(e.getMessage());
	 }
	 	return output;
	 }


//delete user details	
public String deleteDetails(String patientId)
	 {
		String output = "";
	 try
	 {
		 Connection con = connect();
	 if (con == null)
	 {
		 return "Error while connecting to the database for deleting."; 
	 }
	 // create a prepared statement
	 String query = "delete from patientdetails where patientId=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(patientId));
	 
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Deleted successfully";
	 }
	 catch (Exception e)
	 {
		 output = "Error while deleting the item.";
		 System.err.println(e.getMessage());
		
	 }
	 return output;
	 }



public User readLogin(String username, String password) {
	try{
		Connection con = connect();
		
		if (con == null){
		
			System.out.println("execured up before");
			return null;
		}
	
	//		System.out.println("execured up after");
	// create a prepared statement
		String query = "select * from patientdetails where username = '"+username+"'";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);

	

		while (rs.next()){

			System.out.println(rs.getString("patientId"));
			
			User u = new User(rs.getString("patientId"), rs.getString("name"), rs.getString("username"), "patient");
			
			String pass = rs.getString("password");

			if(pass.equals(password)){
				System.out.println("true from db");
				return u;
			}
			else
			{
				System.out.println("false from db");
			}
						}
		
		con.close();

	}catch (Exception e){
			System.err.println(e.getMessage());
	}
	
	return null;
		
}



//public boolean adminLogin(String Admin_username, String Admin_password) {
//	try{
//		Connection con = connect();
//		
//		if (con == null){
//		
//			System.out.println("execured up before");
//			return false;
//		}
//	
//	//		System.out.println("execured up after");
//
//	// create a prepared statement
//		String query = "select * from admin_details where Admin_username = '"+Admin_username+"'";
//		PreparedStatement preparedStmt = con.prepareStatement(query);
//		Statement stmt = con.createStatement();
//		ResultSet rs = stmt.executeQuery(query);
//
//
//		while (rs.next()){
//			System.out.println(rs.getString("Admin_id"));
//			
//			User u = new User(rs.getString("Admin_id"), rs.getString("Admin_name"), rs.getString("Admin_username"), "Admin");
//
//			String pass = rs.getString("Admin_password");
//
//			if(pass.equals(Admin_password)){
//				System.out.println("true from db");
//				return true;
//			}else{
//				System.out.println("false from db");
//			}
//		}
//		
//		con.close();
//
//	}catch (Exception e){
//			System.err.println(e.getMessage());
//	}
//	
//	return false;
//		
//}

public User adminLogin(String Admin_username, String Admin_password) {
	try{
		Connection con = connect();
		
		if (con == null){
			System.out.println("Executed before Comparision");
			return null;
		}
	


	// create a prepared statement
		String query = "select * from admin_details where Admin_username = '"+Admin_username+"'";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);


		while (rs.next()){
			
			System.out.println(rs.getString("Admin_id"));
			
			User u = new User(rs.getString("Admin_id"), rs.getString("Admin_name"), rs.getString("Admin_username"), "Admin");
			
			String password = rs.getString("Admin_password");

			if(password.equals(Admin_password)){
				System.out.println("true from db");
				return u;
			}
			else
			{
				System.out.println("false from db");
			}
		}
		
		con.close();

	}catch (Exception e){
			System.err.println(e.getMessage());
	}
	
	return null;
		
}

}
	

