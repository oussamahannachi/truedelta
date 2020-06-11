package implementations;



import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



import entities.*;
import services.TransactionServiceRemote;


@Stateless
@LocalBean

public class TransactionServiceImp implements TransactionServiceRemote {
	@PersistenceContext(unitName = "truedelta-ejb")
	private EntityManager em;

	public TransactionServiceImp() {
	}
	//***************************CRUD TRANSACTION***************************************************
	@Override
	public int ajouterTransaction(Transaction transaction) {
		System.out.println("AddTransaction : ");
		em.persist(transaction);
		System.out.println("TransactionAdded : ");
		return transaction.getId();
		}
	@Override
	public void removeTransaction(int id) {
	System.out.println("RemoveTransaction : ");
	em.remove(em.find(Transaction.class, id));
	System.out.println("TransactionRemoved : ");
	} 
	@Override
	public void updateTransaction(Transaction transactionupdated) {
	System.out.println("UpdatedTransaction : ");
	Transaction transaction = em.find(Transaction.class, transactionupdated.getId());
	transaction.setType(transactionupdated.getType());
	System.out.println("Out of updateUser : ");
	}
	@Override
	public String getTransactionById(int transactionId) {
		System.out.println("In getTransactionById : ");
		Transaction trans = em.find(Transaction.class, transactionId);
		System.out.println("Out of getTransactionById : ");
		return trans.getType();
	}


	
}
