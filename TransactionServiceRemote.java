package services;

import javax.ejb.Remote;

import entities.*;


@Remote
public interface TransactionServiceRemote {
//*****************Crud************************
	public int ajouterTransaction(Transaction transaction);
    public void updateTransaction(Transaction transactionupdated);
	public void removeTransaction(int id);
	public String getTransactionById(int transactionId);

	
	

	}
