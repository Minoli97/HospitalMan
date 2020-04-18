package com;

import model.Patient;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;
@Path("/Patient")
public class MyDoctor_Patient
{
	Patient itemObj = new Patient();


@GET
@Path("/")
@Produces(MediaType.TEXT_HTML)
public String readDetails()
{
return itemObj.readDetails();
}




@POST
@Path("/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_PLAIN)
public String insertDetails(@FormParam("name") String name,@FormParam("gender") String gender,@FormParam("phone") String phone,
		@FormParam("NIC") String NIC,@FormParam("email") String email,@FormParam("username") String username,@FormParam("password") String password)
 
{
 String output = itemObj.insertDetails(name,gender,phone,NIC,email,username, password);
return output;
}


@PUT
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public String updateDetails(String update_patient)
{
//Convert the input string to a JSON object
 JsonObject itemObject = new JsonParser().parse(update_patient).getAsJsonObject();
//Read the values from the JSON object
 String patientId = itemObject.get("patientId").getAsString();
 String name = itemObject.get("name").getAsString();
 String gender = itemObject.get("gender").getAsString();
 String phone = itemObject.get("phone").getAsString();
 String NIC = itemObject.get("NIC").getAsString();
 String email = itemObject.get("email").getAsString();
 String username = itemObject.get("username").getAsString();
 String password = itemObject.get("password").getAsString();
 String output = itemObj.updateDetails(patientId, name, gender,phone,NIC,email,username,password);
return output;
}


@DELETE
@Path("/")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.TEXT_PLAIN)
public String deleteDetails(String delete_patient)
{
//Convert the input string to an XML document
 Document doc = Jsoup.parse(delete_patient, "", Parser.xmlParser());

//Read the value from the element <itemID>
 String patientId = doc.select("patientId").text();
 String output = itemObj.deleteDetails(patientId);
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
	boolean output = itemObj.readLogin(username, password);
	System.out.println(output);
	if(output) {
		return "Login success!";
		
		
	}else {
		return "Invalid credentials!";
	}
	
}

@POST
@Path("/adminlog")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_PLAIN)
public String adminLogin(@FormParam("Admin_username") String Admin_username,
			 @FormParam("Admin_password") String Admin_password)
{
	System.out.println(Admin_username + " and " + Admin_password);
	boolean output = itemObj.adminLogin(Admin_username, Admin_password);
	System.out.println(output);
	if(output) {
		return "Login success!\n\n\n\n" + itemObj.readDetails();
		
	}else {
		return "Invalid credentials!";
	}
	
}
}

