package tn.esprit.pi.ressource;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import implementation.CompaniesService;;

@Path("comp")
@RequestScoped
public class CompnyResource {
	@EJB
	CompaniesService metier;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser() {
		return Response.ok(metier.GetAllCompanies()).build();
	}
}
