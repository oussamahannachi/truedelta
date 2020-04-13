package tn.esprit.pi.ressource;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import implementation.ActifFinancierService;

 

@Path("actif")
@RequestScoped
public class ActifResource {

	
		@EJB
		ActifFinancierService metier;
		
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Response getUser() {
			return Response.ok(metier.FindAllstock()).build();
		}
}
