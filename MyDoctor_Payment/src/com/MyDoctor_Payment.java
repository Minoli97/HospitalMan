package com;

import model.payment;

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
	public String readPayment()
	 {
	 return paymentObj.readPayment();
	 } 
	
	
	@GET
	@Path("/log")
	@Produces(MediaType.TEXT_HTML)
	public String readLogin()
	 {
	 return paymentObj.readPayment();
	 } 
	
	@POST
	@Path("/pay")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@FormParam("Card_holder") String Card_holder,
	 @FormParam("Card_number") String Card_number,
	 @FormParam("CVV") String CVV,
	 @FormParam("Date") String Date,
	 @FormParam("Total_amount") String Total_amount)
	{
	 String output = paymentObj.insertPayment(Card_holder, Card_number, CVV, Date, Total_amount);
	return output;
	
	}
	
	
	@POST
	@Path("/log")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertLogin(@FormParam("Username") String Username,
	 @FormParam("Password") String Password)
	{
	 String output = paymentObj.insertLogin(Username, Password);
	return output;
	
	}
	
	
	
	@PUT
	@Path("/pay")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(String paymentData)
	{
	//Convert the input string to a JSON object
	 JsonObject paymentObject = new JsonParser().parse(paymentData).getAsJsonObject();
	//Read the values from the JSON object
	 String pay_id = paymentObject.get("pay_id").getAsString();
	 String Card_holder = paymentObject.get("Card_holder").getAsString();
	 String Card_number = paymentObject.get("Card_number").getAsString();
	 String CVV = paymentObject.get("CVV").getAsString();
	 String Date = paymentObject.get("Date").getAsString();
	 String Total_amount = paymentObject.get("Total_amount").getAsString();
	 String output = paymentObj.updatePayment(pay_id, Card_holder, Card_number, CVV, Date, Total_amount);
	return output;
	}


	@DELETE
	@Path("/pay")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String paymentData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());

	//Read the value from the element <pay_id>
	 String pay_id = doc.select("pay_id").text();
	 String output = paymentObj.deletePayment(pay_id);
	return output;
	}

	

}
