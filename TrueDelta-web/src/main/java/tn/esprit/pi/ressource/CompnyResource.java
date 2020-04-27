package tn.esprit.pi.ressource;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.Company;
import implementation.CompaniesService;;

@Path("comp")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
public class CompnyResource {
	@EJB
	CompaniesService metier;
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("create")
    public Response AddCompany(Company c) {
		
        int id = metier.AddCompany(c);
        return Response.ok(id).build();
    }
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
    @Path("update")
    public Response UpdateCompany(Company c) {
		metier.UpdateCompany(c);
        return Response.ok().build();
    }
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("delete/{id}")
    public Response RemoveCompany(@PathParam("id") int id) {
		metier.RemoveCompany(id);
        return Response.ok().build();
    }
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCompanies() {
		return Response.ok(metier.GetAllCompanies()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("ByAdresse/{address}")
	public Response getCompByAdresse(@PathParam("address") String address) {
		return Response.ok(metier.GetCompByAdresse(address)).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("BySymbol/{Symbol}")
	public Response GetCompanyBySymbol(@PathParam("Symbol") String Symbol) {
		return Response.ok(metier.GetCompanyBySymbol(Symbol)).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("ByID/{id}")
	public Response GetCompanyByID(@PathParam("id") int id) {
		return Response.ok(metier.GetCompanyByID(id)).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("GetMaxReveuu")
	public Response GetMaxReveuu() {
		return Response.ok(metier.GetMaxReveuu()).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("GetAllSector")
	public Response GetAllSector() {
		return Response.ok(metier.GetAllSector()).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("GetCompByMaxReveuu")
	public Response GetCompByMaxReveuu() {
		return Response.ok(metier.GetCompByMaxReveuu()).build();
	}
	
}
