package com;

import model.Appoinment;
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

	@GET
	@Path("/y")
	@Produces(MediaType.TEXT_HTML)
	public String readAppoinment() {
		return app.readAppoinment();
	}

	@POST
	@Path("/x")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertAppoinment(@FormParam("patientName") String patientName,
			@FormParam("doctorName") String doctorName, @FormParam("hospitalName") String hospitalName,
			@FormParam("description") String description) {
		String output = app.insertAppoinment(patientName, doctorName, hospitalName, description);
		return output;
	}

	@PUT
	@Path("/z")
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
	@Path("/m")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String appoinmentdata) { // Convert the input string to an XML document 
		Document doc = Jsoup.parse(appoinmentdata, "", 
				Parser.xmlParser()); //Read the value from the element <itemID> 
		String appinmentId = doc.select("itemID").text();
		String output = app.deleteAppoinment(appinmentId);
		return output;
	}

}