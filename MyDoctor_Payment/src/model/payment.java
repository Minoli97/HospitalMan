package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class payment {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/payment", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPayment(String Card_holder, String Card_number, String CVV, String Date, String Total_amount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into pay_form(pay_id,Card_holder,Card_number,CVV,Date,Total_amount)" + " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, Card_holder);
			preparedStmt.setInt(3, Integer.parseInt(Card_number));
			preparedStmt.setInt(4, Integer.parseInt(CVV));
			preparedStmt.setString(5, Date);
			preparedStmt.setString(6, Total_amount);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";

		} catch (Exception e) {
			output = "Error while inserting the payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPayment() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>pay_id</th><th>Card_holder</th><th>Card_number</th><th>CVV</th><th>Date</th><th>Total_amount</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from pay_form";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String pay_id = Integer.toString(rs.getInt("pay_id"));
				String Card_holder = rs.getString("Card_holder");
				String Card_number = rs.getString("Card_number");
				String CVV = rs.getString("CVV");
				String Date = rs.getString("Date");
				String Total_amount = Double.toString(rs.getDouble("Total_amount"));
				// Add into the html table
				output += "<tr><td>" + pay_id + "</td>";
				output += "<td>" + Card_holder + "</td>";
				output += "<td>" + Card_number + "</td>";
				output += "<td>" + CVV + "</td>";
				output += "<td>" + Date + "</td>";
				output += "<td>" + Total_amount + "</td>";
				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"items.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
						+ "<input name=\"itemID\" type=\"hidden\" value=\"" + pay_id + "\">" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePayment(String pay_id, String Card_holder, String Card_number, String CVV, String Date, String Total_amount) {
		String output = "";
		try {
			Connection con = connect();
			
			if (con == null) {
				return "Error while connecting to the database for updating the payment.";
			}
			
			// create a prepared statement
			String query = "UPDATE pay_form SET Card_holder=? ,Card_number=? ,CVV=? ,Date=?, Total_amount=? WHERE pay_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			
			preparedStmt.setString(1, Card_holder);
			preparedStmt.setInt(2, Integer.parseInt(Card_number));
			preparedStmt.setInt(3, Integer.parseInt(CVV));
			preparedStmt.setString(4, Date);
			preparedStmt.setDouble(5, Double.parseDouble(Total_amount));
			preparedStmt.setInt(6, Integer.parseInt(pay_id));
			
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Payments Updated successfully";
			
		} 
		catch (Exception e) {
			output = "Error while updating the payments.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePayment(String pay_id) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from pay_form where pay_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(pay_id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Payment Deleted successfully";
		} 
		catch (Exception e) {
			output = "Error while deleting the payment.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
	
	
	
	public String insertLogin(String Username, String Password) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into log_in(login_id,Username,Password)" + " values (?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, Username);
			preparedStmt.setString(3, Password);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Logged in successfully";

		} catch (Exception e) {
			output = "Error while inserting the login details.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String readLogin() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>login_id</th><th>Username</th><th>Password</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from log_in";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String login_id = Integer.toString(rs.getInt("login_id"));
				String Username = rs.getString("Username");
				String Password = rs.getString("Password");
			
				// Add into the html table
				output += "<tr><td>" + login_id + "</td>";
				output += "<td>" + Username + "</td>";
				output += "<td>" + Password + "</td>";
				
				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"items.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
						+ "<input name=\"itemID\" type=\"hidden\" value=\"" + login_id + "\">" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the login details.";
			System.err.println(e.getMessage());
		}
		return output;
	}


}
