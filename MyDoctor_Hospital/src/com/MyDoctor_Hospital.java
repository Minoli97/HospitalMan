package com;

import model.Hospital;
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

@Path("/hospital_test_db")
public class MyDoctor_Hospital
	{
		Hospital hospitalObj = new Hospital();
		
		
		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
		public String readPayment(@HeaderParam("token") String token) {
			{

				if(token != null) {
					User u = JwtUtils.parseToken(token);
					
					if(u != null && u.getUserType().equals("Admin")) {
						return hospitalObj.readHospitals();				
					}else {
						return "UnAuthorized admin";
					}
				}else {
					return "UnAuthorized admin";
				}
				
			}
		}
	
//		@GET
//		@Path("/")
//		@Produces(MediaType.TEXT_HTML)
//		public String readHospitals()
//		{
//			return hospitalObj.readHospitals();
//		}
						
		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String insertHospital(@HeaderParam("token") String token,
									@FormParam("hospital_Name") String hospital_Name,
									 @FormParam("hospital_Address") String hospital_Address,
									 @FormParam("hospital_ContactNo") String hospital_ContactNo,
									 @FormParam("hospital_Email") String hospital_Email,
									 @FormParam("hospital_Details") String hospital_Details,
									 @FormParam("hospital_Username") String hospital_Username,
									 @FormParam("hospital_Password") String hospital_Password,
									 @FormParam("hospital_Charge") String hospital_Charge)
		 
		{

		if(token != null) {
			User u = JwtUtils.parseToken(token);
			
			if(u != null && u.getUserType().equals("hospital")) {
				String output = hospitalObj.insertHospital(hospital_Name, hospital_Address, hospital_ContactNo,hospital_Email, hospital_Details, hospital_Charge, hospital_Username, hospital_Password);
				return output;
			}else {
				return "UnAuthorized user";
			}
		}else {
			return "UnAuthorized user";
		}
		
		
		
		
		}
	
		
	
				
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String updateHospital(String hospitalData)

		{
		
			//Convert the input string to a JSON object
		 
			JsonObject hospitalObject = new JsonParser().parse(hospitalData).getAsJsonObject();
		
			//Read the values from the JSON object
			 String hospital_Id = hospitalObject.get("hospital_Id").getAsString();
			 String hospital_Name = hospitalObject.get("hospital_Name").getAsString();
			 String hospital_Address = hospitalObject.get("hospital_Address").getAsString();
			 String hospital_ContactNo = hospitalObject.get("hospital_ContactNo").getAsString();
			 String hospital_Email = hospitalObject.get("hospital_Email").getAsString();
			 String hospital_Details = hospitalObject.get("hospital_Details").getAsString();
			 String hospital_Charge= hospitalObject.get("hospital_Charge").getAsString();
			 String hospital_Username= hospitalObject.get("hospital_Username").getAsString();
			 String hospital_Password= hospitalObject.get("hospital_Password").getAsString();
			 
			 String output = hospitalObj.updateHospital(hospital_Id, hospital_Name, hospital_Address, hospital_ContactNo,hospital_Email, hospital_Details,hospital_Charge,hospital_Username,hospital_Password);
		return output;
		}	
		
		@DELETE
		@Path("/")
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteHospital(String hospitalData)
		{
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(hospitalData, "", Parser.xmlParser());

		//Read the value from the element <itemID>
		 String hospital_Id = doc.select("hospital_Id").text();
		 String output = hospitalObj.deleteHospital(hospital_Id);
		return output;
		}

		/////////userlogin
		@POST
		@Path("/log")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String readLogin(@FormParam("hospital_Username") String hospital_Username,
					 @FormParam("hospital_Password") String hospital_Password)
		{
			System.out.println(hospital_Username + " and " + hospital_Password);
			User user = hospitalObj.readLogin(hospital_Username, hospital_Password);
			
			if(user != null) {
				return JwtUtils.generateToken(user);
			}else {
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
			User user = hospitalObj.readAdminlogin(Admin_username, Admin_password);
			
			if(user != null) {
				return JwtUtils.generateToken(user);
				//return "Login success!\n\n\n\n" + hospitalObj.readHospitals();
			}else {
				return "Invalid credentials!";
			}
			
		}
		
		//Admin login
//		@POST
//		@Path("/adminlog")
//		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//		@Produces(MediaType.TEXT_PLAIN)
//		public String readAdminLogin(@FormParam("Admin_username") String Admin_username,
//					 @FormParam("Admin_password") String Admin_password)
//		{
//			System.out.println(Admin_username + " and " + Admin_password);
//			boolean output = hospitalObj.readAdminLogin(Admin_username, Admin_password);
//			System.out.println(output);
//			if(output) {
//				return "Login success!\n\n\n\n" + hospitalObj.readHospitals();
//			}else {
//				return "Invalid credentials!";
//			}
//			
//		}
}
