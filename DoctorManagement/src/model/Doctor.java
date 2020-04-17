package model;

import java.sql.*;

public class Doctor {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertItem(String doctorName, String address, String phoneNum, String email, String gender, String age, String status, String specialization, String hospitalWork, String details, String username, String password)  
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
	   String query = " insert into doctor(`doctorID`,`doctorName`,`address`,`phoneNum`,`email`,`gender`,`age`,`status`,`specialization`,`hospitalWork`,`details`,`username`,`password`)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
	 
	   PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	   // binding values   
	   preparedStmt.setInt(1, 0);    
	   preparedStmt.setString(2, doctorName);    
	   preparedStmt.setString(3, address);    
	   preparedStmt.setString(4, phoneNum);
	   preparedStmt.setString(5, email);
	   preparedStmt.setString(6, gender);
	   preparedStmt.setString(7, age);
	   preparedStmt.setString(8, status);
	   preparedStmt.setString(9, specialization);
	   preparedStmt.setString(10, hospitalWork);
	   preparedStmt.setString(11, details);
	   preparedStmt.setString(12, username);
	   preparedStmt.setString(13, password);
	   
	// execute the statement    
	   preparedStmt.execute();    
	   con.close(); 
	   
	   output = "Inserted successfully";   
	   }   catch (Exception e)  
	  	
	  {   
		   output = "Error while inserting the item.";    
		   System.err.println(e.getMessage());  
		   
	  	} 
	 
	  return output;  
	  
	}

	public String readDoctors() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Doc Name</th><th>Address</th><th>Phone</th><th>E-mail</th><th>Gender</th>"
					+ "<th>Age</th><th>Status</th><th>Specialization</th><th>Hospital</th><th>Details</th><th>User Name</th><th>Password</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from doctor";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String doctorID = Integer.toString(rs.getInt("doctorID"));
				String doctorName = rs.getString("doctorName");
				String address = rs.getString("address");
				String phoneNum = rs.getString("phoneNum");
				String email = rs.getString("email");
				String gender = rs.getString("gender");
				String age = Integer.toString(rs.getInt("age"));
				String status = rs.getString("status");
				String specialization = rs.getString("specialization");
				String hospitalWork = rs.getString("hospitalWork");
				String details = rs.getString("details");
				String username = rs.getString("username");
				String password = rs.getString("password");

				// Add into the html table
				// output += "<tr><td>" + doctorID + "</td>";
				output += "<tr><td>" + doctorName + "</td>";
				output += "<td>" + address + "</td>";
				output += "<td>" + phoneNum + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + gender + "</td>";
				output += "<td>" + age + "</td>";
				output += "<td>" + status + "</td>";
				output += "<td>" + specialization + "</td>";
				output += "<td>" + hospitalWork + "</td>";
				output += "<td>" + details + "</td>";
				output += "<td>" + username + "</td>";
				output += "<td>" + password + "</td>";
				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"register.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
						+ "<input name=\"doctorID\" type=\"hidden\" value=\"" + doctorID + "\">" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the doctors.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateItem(String doctorID, String doctorName, String address, String phoneNum, String email,
			String gender, String age, String status, String specialization, String hospitalWork, String details,
			String username, String password) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE doctor SET doctorName=?,address=?,phoneNum=?,email=?,gender=?,age=?,status=?,specialization=?,hospitalWork=?,details=?,username=?,password=? WHERE doctorID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, doctorName);
			preparedStmt.setString(2, address);
			preparedStmt.setString(3, phoneNum);
			preparedStmt.setString(4, email);
			preparedStmt.setString(5, gender);
			preparedStmt.setString(6, age);
			preparedStmt.setString(7, status);
			preparedStmt.setString(8, specialization);
			preparedStmt.setString(9, hospitalWork);
			preparedStmt.setString(10, details);
			preparedStmt.setString(11, username);
			preparedStmt.setString(12, password);
			preparedStmt.setInt(13, Integer.parseInt(doctorID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the doctor.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteItem(String doctorID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from doctor where doctorID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(doctorID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the doctors.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
