package model;

import  java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Appoinment { 




// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
	
	try{
		Class.forName("com.mysql.jdbc.Driver"); // Provide the correct details: DBServer/DBName, username, password
	
	con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/appoinment","root","");
	}catch(

	Exception e)
	{
		e.printStackTrace();
	}return con;
	}

	public String insertAppoinment(String patientName, String doctorName, String hospitalName, String description)  
 {   
		String output = "";  
  try   {   
	  Connection con = connect();  
   if (con == null)    {
	   return "Error while connecting to the database for inserting."; 
	   }  
   // create a prepared statement    
   String query = " insert into appoin (appinmentId,patientName,doctorName,hospitalName,description)" + " values (?, ?, ?, ?, ?)";  
   PreparedStatement preparedStmt = con.prepareStatement(query);  
   // binding values    
   preparedStmt.setInt(1, 0);    
   preparedStmt.setString(2, patientName);    
   preparedStmt.setString(3, doctorName);   
   preparedStmt.setString(4, hospitalName);   
   preparedStmt.setString(5, description);  
  
   // execute the statement    
   preparedStmt.execute();    
   con.close();  
   output = "Inserted successfully";   
   }   catch (Exception e)   
  {    output = "Error while inserting the appoinment.";   
  System.err.println(e.getMessage());   }  
  return output;  }
	
	

	public String readAppoinment()  
	{   
		String output = "";  
	try   {    
	  Connection con = connect();  
   if (con == null)    {
	   return "Error while connecting to the database for reading."; 
	   }  
   // Prepare the html table to be displayed   
   output = "<table border=\"1\"><tr><th>Patient Name</th><th>Doctor Name</th><th>Hospital Name</th><th>Description</th><th>Update</th><th>Remove</th></tr>";
   
   String query = "select * from appoin";    
   Statement stmt = con.createStatement();   
   ResultSet rs = stmt.executeQuery(query);  
   // iterate through the rows in the result set    
   while (rs.next())    {     
	   String appinmentId = Integer.toString(rs.getInt("appinmentId"));     
	   String patientName = rs.getString("patientName");     
	   String doctorName = rs.getString("doctorName");     
	   String hospitalName = rs.getString("hospitalName");     
	   String description = rs.getString("description");  
   
    // Add into the html table     
 


   	
	
	output += "<tr><td>" + patientName + "</td>";     
  
	
	output += "<td>" + doctorName + "</td>";     
   	
	
	output += "<td>" + hospitalName + "</td>";     
   	
	
	output += "<td>" + description + "</td>";  
    // buttons     
   



output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"    
    + "<td><form method=\"post\" action=\"items.jsp\">"    
		   + "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"      "
		   + "class=\"btn btn-danger\">"    
		   + "<input name=\"appinmentId\" type=\"hidden\" value=\"" + appinmentId   
   
		   + "\">" + "</form></td></tr>";  
   }
   con.close();  
   // Complete the html table   
   output += "</table>";  
   }catch(

	Exception e)
	{
		output = "Error while reading the Appionments.";
		System.err.println(e.getMessage());
	}return output;
	}
	
	public String updateAppoinment(String appinmentId, String patientName, String doctorName, String hospitalName, String description)  { 
	 String output = "";  
  try   {    Connection con = connect();  
   if (con == null)    {
	   return "Error while connecting to the database for updating."; }  
   // create a prepared statement    
   String query = "UPDATE appoin SET patientName=?,doctorName=?,hospitalName=?,description=?      WHERE appinmentId=?";  
   PreparedStatement preparedStmt = con.prepareStatement(query);  
   // binding values    
   preparedStmt.setString(1, patientName);    
   preparedStmt.setString(2, doctorName);    
   preparedStmt.setString(3, hospitalName);    
   preparedStmt.setString(4, description);    
   preparedStmt.setInt(5, Integer.parseInt(appinmentId));  
   // execute the statement    
   preparedStmt.execute();    
   con.close();  
   output = "Updated successfully";   
   }   catch (Exception e)   
  {    output = "Error while updating the Appoinments.";    
  System.err.println(e.getMessage());   }  
  return output; 
  }
	
	

	public String deleteAppoinment(String appinmentId)  
 { 
	 String output = "";  
  try   {    
	  Connection con = connect();  
   if (con == null)   
   {return "Error while connecting to the database for deleting."; }  
   // create a prepared statement   
   String query = "delete from appoin where appinmentId=?";  
   PreparedStatement preparedStmt = con.prepareStatement(query);  
   // binding values    
   preparedStmt.setInt(1, Integer.parseInt(appinmentId));  
   // execute the statement    
   preparedStmt.execute();    
   con.close();  
   output = "Deleted successfully";   
   }   catch (Exception e)  
  {    output = "Error while deleting the Appoionments.";   
  System.err.println(e.getMessage());   }  
  return output;  
  }
	
	
	
}