package services;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import entities.*;



@Remote
public interface TransactionServiceRemote {
//*****************Crud************************
	public int ajouterTransaction(Transaction transaction);
    public void updateTransaction(Transaction transactionupdated);
	public void removeTransaction(int id);
	public Transaction getTransactionById(int transactionId);
	public ActifFinancier getActifById(int ida);
	public Compte getCompteById(ComptePK pck);
	public Compte getCompteByNumero(long num);
	
//******************Validation du transaction par compte********************************************************************
	
	public double affecterActifTransaction(int actifId, int transactionId)throws IOException;
    public double affecterTransactionCompte1( long num , int actifId, int transactionId,String devisecompt)throws IOException;
    public double affecterTransactionCompte( ComptePK pck, int actifId, int transactionId,String devisecompt)throws IOException;
//////********************Consultation des transactions	*****************************		
    public List<Transaction> consulterTransactionssnv();
	public List<Transaction> consulterTransactions();
	public Map<Integer, Float> classerTransactionValidéesById();
	public List<Transaction> classerTransactionByIdliste();
	public List<ActifFinancier> consulterTransactionParCompte(long numc);
	public List<ActifFinancier> consulterActifs();
	public List<ActifFinancier> consulterActifsv();
	public List<ActifFinancier> consulterActifsnv();
	public List<Compte> consulterCompte();
	public List<Transaction> consulterTransactionsv();
	public List<Transaction> consulterTransactionsnv();
	public List<Transaction> classerTransactionNonScoré();
	List<Transaction> classerTransactionScoré();
//**************Statistique**********************************
	public int Scooringtransaction(int id);
	public int getNbtransvalide();
	public int getNbtransNonvalide();
	public int getNbtransvalideparJour()throws ParseException;
	public int getNbtransNvalideparJour() throws ParseException;
	public int getNbtransvalideparMois() throws ParseException;
	public int getNbtransNvalideparMois() throws ParseException;
	public int getNbtransvalideparAnnee() throws ParseException;
	public int getNbtransNvalideparAnnee() throws ParseException;
	public double profitparJ();
	public double profitparM();
	public double profitparannee();
	public double profittotal();
//************************************
	
	public Map<String, String> ProduitDeBourse() throws IOException;
	
//***********************************************************************************
	void verserPrixTransaction(float solde, ComptePK pck);
	void retirerPrixTransaction(float solde, ComptePK pck);
	public Utilisateur getUserByEmailAndPassword(String login, String password);
	public Utilisateur loginUser(String login, String password);
	public Agence loginAgence(String login, String password);
	public List<Transaction> consulterTransactionParCompte1(long numc);
	public List<Transaction> consulterTransactionScoreTop();
	public List<Transaction> consulterTransactionScoreMoyen();
	public List<Transaction> consulterTransactionScoreFaible();
	
	
	
	

	
	
	
	
	
	
	
	
	
	

	
	
	

	}
