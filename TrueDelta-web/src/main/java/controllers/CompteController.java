package controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.Compte;
import interfaces.CompteServiceLocal;

@RequestScoped
@Path("compte")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompteController {

	@Inject
	CompteServiceLocal cs;
	
	@POST
	@Path("create")
	public Response ajouterCompte1(Compte c) {
		long num = cs.ajouterCompte(c);
		return Response.ok(num).build();
	}
	
	@DELETE
	@Path("delete")
	public Response deleteCompte(@QueryParam(value="num") long num) {
		cs.supprimerCompte(num);
		return Response.ok().build();
	}
	
	
	@GET
	@Path("getById")
	public Response getCompteById(@QueryParam(value="num") long num) {
		Compte c =  cs.getCompteByNumero(num);
		return Response.ok(c).build();
	}
	
	@PUT
	@Path("update")
	public Response updateCompte(Compte c) {
		return Response.ok(cs.modifierCompte(c)).build();
	}
	
	
	
	
	
	
	
	
}
