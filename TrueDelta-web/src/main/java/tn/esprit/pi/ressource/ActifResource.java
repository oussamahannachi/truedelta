package tn.esprit.pi.ressource;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.ActifFinancier;
import entities.Company;
import implementation.ActifFinancierService;

 

@Path("actif")
@RequestScoped
public class ActifResource {

	
		@EJB
		ActifFinancierService metier;

		
		@POST
		@Produces(MediaType.APPLICATION_JSON)
		@Path("create")
	    public Response CreateSecurity(ActifFinancier a) {
			
	        int id = metier.AddStock(a);
	        return Response.ok(id).build();
	    }
		@DELETE
		@Produces(MediaType.APPLICATION_JSON)
		@Path("delete/{id}")
	    public Response RemoveCompany(@PathParam("id") int id) {
			metier.RemoveActif(id);
	        return Response.ok().build();
	    }
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Response FindAllstock() {
			return Response.ok(metier.FindAllstock()).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("ById/{id}")
		public Response GetStockById(@PathParam("id")int id) {
			return Response.ok(metier.GetStockById(id)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("ByCompany/{name}")
		public Response GetStockByCompany(@PathParam("name")String name) {
			return Response.ok(metier.GetStockByCompany(name)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("SortAscending/{criteria}")
		public Response SortAscending(@PathParam("criteria")String criteria) {
			return Response.ok(metier.SortAscending(criteria)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("SortDescending/{criteria}")
		public Response SortDescending(@PathParam("criteria")String criteria) {
			return Response.ok(metier.SortDescending(criteria)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("Top/{limit}")
		public Response findOrderedBySeatNumberLimitedTo(@PathParam("limit")int limit) {
			return Response.ok(metier.findOrderedBySeatNumberLimitedTo(limit)).build();
		}
		@PUT
		@Produces(MediaType.APPLICATION_JSON)
	    @Path("affecterActifAcompany/{ActifId}/{compid}")
	    public Response affecterActifAcompany(@PathParam("ActifId")int ActifId,@PathParam("compid") int compid) {
			metier.affecterActifAcompany(ActifId, compid);
	        return Response.ok().build();
	    }
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("getYahoStock/{stockNames}")
		public Response getStock(@PathParam("stockNames")String[] stockNames) throws IOException {
			return Response.ok(metier.getStock(stockNames)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("getYahoHistory/{stockName}")
		public Response getHistory(@PathParam("stockName")String stockName) throws IOException {
			return Response.ok(metier.getHistory(stockName)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("getYahoHistory/{stockName}/{year}/{searchType}")
		public Response getHistory(@PathParam("stockName")String stockName,@PathParam("year") int year,@PathParam("searchType") String searchType) throws IOException {
			return Response.ok(metier.getHistory(stockName,year,searchType)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("Volatility/{stockName}")
		public Response Volatility(@PathParam("stockName")String stockName)  {
			return Response.ok(metier.Volatility(stockName)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("RendementAnnuel/{stockName}")
		public Response RendementAnnuel(@PathParam("stockName")String stockName,@PathParam("annee")int annee) throws ParseException  {
			return Response.ok(metier.RendementAnnuel(stockName,annee)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("ByCompanyPeriode/{stockName}/{Period1}/{Period2}")
		public Response GetStockByCompanyPeriode(@PathParam("stockName")String stockName,@PathParam("Period1") Date Period1,@PathParam("Period2") Date Period2) throws ParseException  {
			return Response.ok(metier.GetStockByCompanyPeriode(stockName,Period1,Period2)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("ByCompanyPeriode/{stockName}/{Date}")
		public Response GetStockByCompanyDate(@PathParam("stockName")String stockName,@PathParam("Date") Date Date) throws ParseException  {
			return Response.ok(metier.GetStockByCompanyDate(stockName, Date)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("ByCompanyPeriode/{stockName}/{Period1}/{Period2}")
		public Response RendementByPeriod(@PathParam("stockName")String stockName,@PathParam("Period1") Date Period1,@PathParam("Period2") Date Period2) throws ParseException  {
			return Response.ok(metier.RendementByPeriod(stockName,Period1,Period2)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("GetObligation")
		public Response GetObligation() {
			return Response.ok(metier.GetObligation()).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("GetAction")
		public Response GetAction() {
			return Response.ok(metier.GetAction()).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("CashFlow")
		public Response CashFlow(ActifFinancier a) {
			return Response.ok(metier.CashFlow(a)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("PVCashFlow")
		public Response PVCashFlow(ActifFinancier a) {
			return Response.ok(metier.PVCashFlow(a)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("PVCashFlow")
		public Response DurationCalcul(ActifFinancier a) {
			return Response.ok(metier.DurationCalcul(a)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("Duration")
		public Response Duration(ActifFinancier a) {
			return Response.ok(metier.Duration(a)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("Sensibilite")
		public Response Sensibilite(ActifFinancier a) {
			return Response.ok(metier.Sensibilite(a)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("Convexité")
		public Response Convexité(ActifFinancier a) {
			return Response.ok(metier.Convexité(a)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("ConvexitéCalcul")
		public Response ConvexitéCalcul(ActifFinancier a) {
			return Response.ok(metier.ConvexitéCalcul(a)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("SommeConvexité")
		public Response SommeConvexité(ActifFinancier a) {
			return Response.ok(metier.SommeConvexité(a)).build();
		}
		////à terminer 
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("getStockByClient/{idp}/{ida}")
		public Response getStockByClient(@PathParam("idp")int idp,@PathParam("ida") int ida) throws ParseException  {
			return Response.ok(metier.getStockByClient(idp, ida)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("getStockByClient/{idp}")
		public Response getStockByClient(@PathParam("idp")int idp) throws ParseException  {
			return Response.ok(metier.getStockByClient(idp)).build();
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("getStockBynum/{num}")
		public Response getStockBynumeroCompte(@PathParam("num")String num) throws ParseException  {
			return Response.ok(metier.getStockBynumeroCompte(num)).build();
		}
}
