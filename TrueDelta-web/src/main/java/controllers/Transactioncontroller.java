package controllers;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.ActifFinancier;
import entities.Transaction;
import implementations.TransactionServiceImp;
@RequestScoped
@Path("transaction1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class Transactioncontroller {
	@Inject
	TransactionServiceImp ts ;
	@POST
	@Path("create")
	public Response ajoutertransaction1(Transaction t) {
		 ts.ajouterTransaction(t);
		return Response.ok().build();
		
	}
	@DELETE
	@Path("delete")
	public  Response removeTransaction1(@QueryParam(value="id") int id) {
		ts.removeTransaction(id);
		return Response.ok().build();
	}
	@PUT
	@Path("update")
	public Response updateTransaction1(Transaction t) {
		return Response.ok().build();
	}
	@GET
	@Path("getbyid")
	public Response getTransactionById1(@QueryParam(value="id") int id) {
		Transaction trans = ts.getTransactionById(id);
	 return Response.ok(trans).build();
		
		
	}
	@GET
	@Path("getTbyidc")
	public Response getTransactionByCompte(@QueryParam(value="numc") long numc) {
		List<ActifFinancier> actifs = ts.consulterTransactionParCompte(numc);
	 return Response.ok(actifs).build();
		
		
	}

}
