package com;

import model.User;
import model.payment;
import utils.JwtUtils;

import java.sql.Date;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/payment")

public class MyDoctor_Payment {

	payment paymentObj = new payment();

	@GET
	@Path("/pay")
	@Produces(MediaType.TEXT_HTML)
	public String readPayment(@HeaderParam("token") String token) {
		{

			if(token != null) {
				User u = JwtUtils.parseToken(token);
				
				if(u != null && u.getUserType().equals("Admin")) {
					return paymentObj.readPayment();				
				}else {
					return "UnAuthorized user";
				}
			}else {
				return "UnAuthorized user";
			}
			
		}
	}

//	@GET
//	@Path("/log")
//	@Produces(MediaType.TEXT_HTML)
//	public String readLogin()
//	 {
//	 return paymentObj.readPayment();
//	 } 

	@POST
	@Path("/pay")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@HeaderParam("token") String token, @FormParam("Card_holder") String Card_holder,
			@FormParam("Card_number") String Card_number, @FormParam("CVV") String CVV, @FormParam("Date") String Date,
			@FormParam("Total_amount") String Total_amount)

	{

		if(token != null) {
			User u = JwtUtils.parseToken(token);
			
			if(u != null && u.getUserType().equals("patient")) {
				String output = paymentObj.insertPayment(Card_holder, Card_number, CVV, Date, Total_amount);
				return output;
			}else {
				return "Un Authorized Administrator.";
			}
		}else {
			return "Un Authorized Administrator";
		}
		
		
		

	}

//	@POST
//	@Path("/log")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.TEXT_PLAIN)
//	public String insertLogin(@FormParam("Username") String Username,
//							  @FormParam("Password") String Password)
//	{
//	 String output = paymentObj.insertLogin(Username, Password);
//	return output;
//	
//	}

	@PUT
	@Path("/pay")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(@HeaderParam("token") String token,String paymentData) {
		// Convert the input string to a JSON object
		JsonObject paymentObject = new JsonParser().parse(paymentData).getAsJsonObject();
		// Read the values from the JSON object
		String pay_id = paymentObject.get("pay_id").getAsString();
		String Card_holder = paymentObject.get("Card_holder").getAsString();
		String Card_number = paymentObject.get("Card_number").getAsString();
		String CVV = paymentObject.get("CVV").getAsString();
		String Date = paymentObject.get("Date").getAsString();
		String Total_amount = paymentObject.get("Total_amount").getAsString();
		String output = paymentObj.updatePayment(pay_id, Card_holder, Card_number, CVV, Date, Total_amount);
		
		if(token != null) {
			User u = JwtUtils.parseToken(token);
			
			if(u != null && u.getUserType().equals("patient")) {
				return output;
			}else {
				return "Un Authorized User";
			}
		}else {
			return "Un Authorized User";
		}
		
	
	}

	@DELETE
	@Path("/pay")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(@HeaderParam ("token")String token,String paymentData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());

		// Read the value from the element <pay_id>
		String pay_id = doc.select("pay_id").text();
		String output = paymentObj.deletePayment(pay_id);
		
		if(token != null) {
			User u = JwtUtils.parseToken(token);
			
			if(u != null && u.getUserType().equals("Admin")) {
				return output;				
			}else {
				return "Un Authorized Administrator";
			}
		}else {
			return "Un Authorized Administrator";
		}
		
		
	}

	@POST
	@Path("/log")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String readLogin(@FormParam("Patient_username") String Patient_username,
			@FormParam("Patient_password") String Patient_password) {
		System.out.println(Patient_username + " and " + Patient_password);
		User user = paymentObj.readLogin(Patient_username, Patient_password);

		if (user != null) {
			return JwtUtils.generateToken(user);

		} else {
			return "Invalid credentials!";
		}

	}

	@POST
	@Path("/logadmin")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String readAdminlogin(@FormParam("Admin_username") String Admin_username,
				 @FormParam("Admin_password") String Admin_password)
	{
		System.out.println(Admin_username + " and " + Admin_password);
		User user = paymentObj.readAdminlogin(Admin_username, Admin_password);
		
		if(user != null) {
			return JwtUtils.generateToken(user);
			//return "Login success!\n\n\n\n" + paymentObj.readPayment();
		}else {
			return "Invalid credentials!";
		}
		
	}

}
