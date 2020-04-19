package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Hospital {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hospital_test_db", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertHospital( String hospital_Name,String hospital_Address, String hospital_ContactNo, String hospital_Email, String hospital_Details, String hospital_Charge, String hospital_Username, String hospital_Password)
	{
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into sign_up(hospital_Id,hospital_Name,hospital_Address,hospital_ContactNo,hospital_Email,hospital_Details,hospital_Charge,hospital_Username,hospital_Password)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, hospital_Name);
			preparedStmt.setString(3, hospital_Address);
			preparedStmt.setString(4, hospital_ContactNo);
			preparedStmt.setString(5, hospital_Email);
			preparedStmt.setString(6, hospital_Details);
			preparedStmt.setDouble(7, Double.parseDouble(hospital_Charge)); 
			preparedStmt.setString(8, hospital_Username);
			preparedStmt.setString(9, hospital_Password);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the hospital.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readHospitals() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Hospital Id</th><th>Hospital Name</th><th>Hospital Address</th><th>Hospital ContactNo</th><th>Hospital Email</th><th>Hospital Details</th><th>Hospital Charge</th><th>Hospital Username</th><th>Hospital Password</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from sign_up";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String hospital_Id = Integer.toString(rs.getInt("hospital_Id"));
				String hospital_Name = rs.getString("hospital_Name");
				String hospital_Address = rs.getString("hospital_Address");
				String hospital_ContactNo = rs.getString("hospital_ContactNo");
				String hospital_Email = rs.getString("hospital_Email");
				String hospital_Details = rs.getString("hospital_Details");				
				String hospital_Charge = Double.toString(rs.getDouble("hospital_Charge"));
				String hospital_Username = rs.getString("hospital_Username");
				String hospital_Password = rs.getString("hospital_Password");

				// Add into the html table
				output += "<tr><td>" + hospital_Id + "</td>";
				output += "<td>" + hospital_Name + "</td>";
				output += "<td>" + hospital_Address + "</td>";
				output += "<td>" + hospital_ContactNo + "</td>";
				output += "<td>" + hospital_Email + "</td>";
				output += "<td>" + hospital_Details + "</td>";
				output += "<td>" + hospital_Charge + "</td>";
				output += "<td>" + hospital_Username + "</td>";
				output += "<td>" + hospital_Password + "</td>";

				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"items.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
						+ "<input name=\"hospital_Id\" type=\"hidden\" value=\"" + hospital_Id + "\">"
						+ "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Hospital.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateHospital(String hospital_Id, String hospital_Name, String hospital_Address, String hospital_ContactNo, String hospital_Email, String hospital_Details, String hospital_Charge, String hospital_Username, String hospital_Password)
	{
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement

			String query = "UPDATE sign_up SET hospital_Name=? ,hospital_Address=? ,hospital_ContactNo=? ,hospital_Email=? ,hospital_Details=? ,hospital_Charge=? ,hospital_Username=? ,hospital_Password=? WHERE hospital_Id=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, hospital_Name);
			preparedStmt.setString(2, hospital_Address);
			preparedStmt.setString(3, hospital_ContactNo);
			preparedStmt.setString(4, hospital_Email);
			preparedStmt.setString(5, hospital_Details);
			preparedStmt.setDouble(6, Double.parseDouble(hospital_Charge));
			preparedStmt.setString(7, hospital_Username);
			preparedStmt.setString(8, hospital_Password);
			preparedStmt.setInt(9, Integer.parseInt(hospital_Id));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		}

		catch (Exception e) {
			output = "Error while updating the hospital.";
			System.err.println(e.getMessage( ));
		}

		return output;
	}

	public String deleteHospital(String hospital_Id) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from sign_up where hospital_Id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(hospital_Id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the hospital.";
			System.err.println(e.getMessage());
		}
		return output;

	}

	
	
	
	
	
	
	
	public boolean readLogin(String hospital_Username, String hospital_Password) {
		try{
			Connection con = connect();
			
			if (con == null){
				System.out.println("bl");
				System.out.println("execured up before");
				return false;
			}
		
//			System.out.println("execured up after");

		// create a prepared statement
			String query = "select hospital_Password from sign_up where hospital_Username = '"+hospital_Username+"'";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);


			while (rs.next()){
	
				String password = rs.getString("hospital_Password");
	
				if(password.equals(hospital_Password)){
					System.out.println("true from db");
					return true;
				}else{
					System.out.println("false from db");
				}
			}
			
			con.close();

		}catch (Exception e){
				System.err.println(e.getMessage());
		}
		
		return false;
			
	}
	
	////////Admin login chck
	public boolean readAdminLogin(String Admin_username, String Admin_password) {
		try{
			Connection con = connect();
			
			if (con == null){
				System.out.println("bl");
				System.out.println("execured up before");
				return false;
			}
		
//			System.out.println("execured up after");

		// create a prepared statement
			String query = "select Admin_password from admin_details where Admin_username = '"+Admin_username+"'";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);


			while (rs.next()){
	
				String password = rs.getString("Admin_password");
	
				if(password.equals(Admin_password)){
					System.out.println("true from db");
					return true;
				}else{
					System.out.println("false from db");
				}
			}
			
			con.close();

		}catch (Exception e){
				System.err.println(e.getMessage());
		}
		
		return false;
			
	}
}


