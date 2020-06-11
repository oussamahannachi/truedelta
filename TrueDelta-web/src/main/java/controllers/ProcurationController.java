package controllers;
import java.text.ParseException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.Procuration;
import entities.ProcurationPK;
import services.ProcurationServiceImpl;

@RequestScoped
@Path("procuration")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class ProcurationController {
	
	
	@Inject
	ProcurationServiceImpl ps;
	
	@POST
	@Path("create")
	public Response ajouterProcuration1(Procuration proc) {
		ProcurationPK num = ps.ajouterProcuration( proc);
		return Response.ok(num).build();
	}
	
	/*@DELETE
	@Path("delete")
	public Response deleteProc1(@QueryParam(value="num") ProcurationPK num) {
		ps.deleteProc(num);
		return Response.ok().build();
	}	*/
	
	
	@GET
	@Path("GetAll")
	public Response findAllProc1() {
		List<Procuration> c =  ps.findAllProc();
		return Response.ok(c).build();
	}
	
	
	@GET
	@Path("GetType")
	public Response findAllProc2(@QueryParam(value="type") String type) {
		List<Procuration> c =  ps.GetProcByType(type);
		return Response.ok(c).build();
	
	}
	/*
	@PUT
	@Path("update")
	public Response updateProcuration(ProcurationPK id, String descr) {
		ps.modifdescrip(id,descr);
		return Response.ok().build();
	}*/
	
	@GET
	@Path("nbrProc")
	public Response nbrProc1() {
		Long c =  ps.nbrProc();
		return Response.ok(c).build();
	
	}
	
	
	@GET
	@Path("KNN")
	public Response KNN1(@QueryParam(value="k") int k, @QueryParam(value="nbrAct") int nbrAct,@QueryParam(value="nbrObg") int nbrObg,@QueryParam(value="solde") float solde) {
		  ps.KNN(k,nbrAct, nbrObg,solde);
		return Response.ok().build();
	
	}
	
	

}
