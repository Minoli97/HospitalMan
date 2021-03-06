package com;

import model.Appoinment;
import model.User;
import utils.JwtUtils;

//For REST Service 
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON 
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Appoinment")
public class AppoinmentService {
	Appoinment app = new Appoinment();

//	@GET
//	@Path("/read")
//	@Produces(MediaType.TEXT_HTML)
//	public String readAppoinment() {
//		return app.readAppoinment();
//	}
	
	@GET
	@Path("/pay")
	@Produces(MediaType.TEXT_HTML)
	public String readPayment(@HeaderParam("token") String token) {
		{

			if(token != null) {
				User u = JwtUtils.parseToken(token);
				
				if(u != null && u.getUserType().equals("Admin")) {
					return app.readAppoinment();				
				}else {
					return "UnAuthorized user";
				}
			}else {
				return "UnAuthorized user";
			}
			
		}
	}

	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertAppoinment(@HeaderParam("token") String token,@FormParam("patientName") String patientName,
			@FormParam("doctorName") String doctorName, @FormParam("hospitalName") String hospitalName,
			@FormParam("description") String description) {
		
		if(token != null) {
			User u = JwtUtils.parseToken(token);
			
			if(u != null && u.getUserType().equals("appointment")) {
				String output = app.insertAppoinment(patientName, doctorName, hospitalName, description);
				return output;
			}else {
				return "UnAuthorized user";
			}
		}else {
			return "UnAuthorized user";
		}
		
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateAppoinment(String appoinmentdata) { // Convert the input string to a JSON object

		JsonObject appObj = new JsonParser().parse(appoinmentdata).getAsJsonObject();
		// Read the values from the JSON object

		String appinmentId = appObj.get("appinmentId").getAsString();
		String patientName = appObj.get("patientName").getAsString();
		String doctorName = appObj.get("doctorName").getAsString();
		String hospitalName = appObj.get("hospitalName").getAsString();
		String description = appObj.get("description").getAsString();
		String output = app.updateAppoinment(appinmentId, patientName, doctorName, hospitalName, hospitalName);
		return output;
	}

	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String appoinmentdata) { // Convert the input string to an XML document 
		Document doc = Jsoup.parse(appoinmentdata, "", 
				Parser.xmlParser()); //Read the value from the element <itemID> 
		String appinmentId = doc.select("appinmentId").text();
		String output = app.deleteAppoinment(appinmentId);
		return output;
	}
	
	@POST
	@Path("/log")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String readLogin(@FormParam("username") String username,
				 @FormParam("password") String password)
	{
		System.out.println(username + " and " + password);
		User user = app.readLogin(username, password);
		
		if(user != null ) {
			return JwtUtils.generateToken(user);
		}else {
			return "Invalid credentials!";
		}
		
	}
	
	
	@POST
	@Path("/Adlog")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String readLoginAdmin(@FormParam("Admin_username") String Admin_username,
				 @FormParam("Admin_password") String Admin_password)
	{
		System.out.println(Admin_username + " and " + Admin_password);
		boolean output = app.readLoginAdmin(Admin_username, Admin_password);
		System.out.println(output);
		if(output) {
			return "Login success!";
		}else {
			return "Invalid credentials!";
		}
		
	}
	
//	@POST
//	@Path("/Adlog")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.TEXT_PLAIN)
//	public String insertPayment(@HeaderParam("token") String token, @FormParam("Card_holder") String Card_holder,
//			@FormParam("Card_number") String Card_number, @FormParam("CVV") String CVV, @FormParam("Date") String Date,
//			@FormParam("Total_amount") String Total_amount)
//
//	{
//
//		if(token != null) {
//			User u = JwtUtils.parseToken(token);
//			
//			if(u != null && u.getUserType().equals("patient")) {
//				String output = app.insertPayment(Card_holder, Card_number, CVV, Date, Total_amount);
//				return output;
//			}else {
//				return "UnAuthorized user";
//			}
//		}else {
//			return "UnAuthorized user";
//		}
//		
//		
//		
//
//	}
	

}