package com;

import model.Doctor;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Doctors")
public class DoctorService {
	Doctor doctorObj = new Doctor();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readDoctors() 
	{
		return doctorObj.readDoctors();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertItem(@FormParam("doctorName") String doctorName, @FormParam("address") String address,
			@FormParam("phoneNum") String phoneNum, @FormParam("email") String email,
			@FormParam("gender") String gender, @FormParam("age") String age, @FormParam("status") String status,
			@FormParam("specialization") String specialization, @FormParam("hospitalWork") String hospitalWork,
			@FormParam("details") String details, @FormParam("username") String username,
			@FormParam("password") String password)
	{
		String output = doctorObj.insertItem(doctorName, address, phoneNum, email, gender, age, status, specialization,
				hospitalWork, details, username, password);
		return output;
	}

	
	@PUT 
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN) public String updateItem(String doctorData) 
	{  
		//Convert the input string to a JSON object  JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject(); 
		JsonObject doctorObject = new JsonParser().parse(doctorData).getAsJsonObject();
		 
		 //Read the values from the JSON object  String itemID = itemObject.get("itemID").getAsString();  String itemCode = itemObject.get("itemCode").getAsString();  String itemName = itemObject.get("itemName").getAsString();  String itemPrice = itemObject.get("itemPrice").getAsString();  String itemDesc = itemObject.get("itemDesc").getAsString(); 
		String doctorID = doctorObject.get("doctorID").getAsString();  
		String doctorName = doctorObject.get("doctorName").getAsString();  
		String address = doctorObject.get("address").getAsString();  
		String phoneNum = doctorObject.get("phoneNum").getAsString();  
		String email = doctorObject.get("email").getAsString();
		String gender = doctorObject.get("gender").getAsString();
		String age = doctorObject.get("age").getAsString();
		String status = doctorObject.get("status").getAsString();
		String specialization = doctorObject.get("specialization").getAsString();
		String hospitalWork = doctorObject.get("hospitalWork").getAsString();
		String details = doctorObject.get("details").getAsString();
		String username = doctorObject.get("username").getAsString();
		String password = doctorObject.get("password").getAsString();
		
		 String output = doctorObj.updateItem(doctorID, doctorName, address, phoneNum, email, gender, age, status, specialization,
				 hospitalWork, details, username, password); 
		 
		 return output; 
	}
	
	@DELETE 
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) public String deleteItem(String doctorData) 
	{  
		//Convert the input string to an XML document  
		Document doc = Jsoup.parse(doctorData, "", Parser.xmlParser());     
		
		//Read the value from the element <itemID>  
		String doctorID = doc.select("doctorID").text(); 
		 
		 String output = doctorObj.deleteItem(doctorID); 
		 
		 return output; }
}
