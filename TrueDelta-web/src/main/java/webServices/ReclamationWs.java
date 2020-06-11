package webServices;


import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.Reclamation;
import services.ReclamationImp;





@Path("reclamationss")
@RequestScoped
public class ReclamationWs {
	@EJB
	ReclamationImp recWs;
	
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	@Path("allrec")
	
	
	public Response getComplaints() {
		return Response.ok(recWs.GetAllReclams()).build();
	}
	
	@DELETE
	@Path("delete/{id}")
	public Response deleteComplain(@PathParam("id") int id) {
		recWs.deleteComplain(id);
		return Response.status(200).entity("complain is deleted").build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("GetReclamByState/{state}")
	public List<Reclamation> GetReclamByState(@PathParam("state") String state) {
		return recWs.GetReclamByState(state);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("searchcomplaint/{motcle}")
	public List<Reclamation> SearchComplaint(@PathParam("motcle") String motcle) {
		return recWs.SearchComplaint(motcle);
	}
}
