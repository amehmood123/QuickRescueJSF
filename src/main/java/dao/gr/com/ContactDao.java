package dao.gr.com;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.mysql.jdbc.UpdatableResultSet;

import entity.gr.com.Account;
import entity.gr.com.Contact;
import factory.gr.com.QrSessionFactory;

@SuppressWarnings({ "deprecation", "unused" })
public class ContactDao {

	@SuppressWarnings({ "unchecked" })
	public List<Contact> getAll(int accountId) {
		
	Session session = QrSessionFactory.startTransaction();
	String sql;
	if(accountId!=0){
		 sql= "SELECT * FROM contact where ACCOUNT_ID="+accountId;
		}
	else{
		sql = "SELECT * FROM contact";
		}
	
	SQLQuery query = session.createSQLQuery(sql).addEntity(Contact.class);
	List<Contact> contacts = (List<Contact>) query.list();
	
	for(Contact contact: contacts) {
		Hibernate.initialize(contact.getAddress());
		Hibernate.initialize(contact.getAccount());
		if(contact.getAccount() != null) {
			contact.getAccount().setContacts(null);
			contact.getAccount().setAlertprofile(null);
		}
		
		if(contact.getAddress() != null) {
			contact.getAddress().setContact(null);
		}
	}
	QrSessionFactory.endTransaction(session);
	return contacts;
	}
	
	
	public Contact getSingle(int contactId) {
		
		Session session = QrSessionFactory.startTransaction();
		String sql;
		sql= "SELECT * FROM qr.contact where id="+contactId;

		
		SQLQuery query = session.createSQLQuery(sql).addEntity(Contact.class);
		Contact contact = (Contact) query.uniqueResult();
		
		QrSessionFactory.endTransaction(session);
		return contact;
	}
	
	
	public Contact addContact(Contact contact)
	{
		try{
			Session session = QrSessionFactory.startTransaction();
			 Criteria criteria = session.createCriteria(Account.class)
	                    .add(Restrictions.eq("id", contact.getAccountId()));
			 Object result = criteria.uniqueResult();
			 Account account=(Account) result;
			contact.setAccount(account);
			account.getContacts().add(contact);
			session.save(contact);
			QrSessionFactory.endTransaction(session);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return contact;
		
	}

	public Boolean deleteContact(int contactId)
	{
		try {
			Session session = QrSessionFactory.startTransaction();
			Contact contact = (Contact) session.get(Contact.class, contactId);
            session.delete(contact);
			QrSessionFactory.endTransaction(session);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Contact updateContact(Contact contact){
		Contact contactb=getSingle(contact.getId());
		contactb=updateValues(contactb,contact);
		
		try {
			Session session = QrSessionFactory.startTransaction();
			session.evict(contactb);
			session.update(contactb);
			session.flush();
			session.clear();
			QrSessionFactory.endTransaction(session);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return contactb;
	}
	
	public Contact updateValues(Contact original, Contact updated)
	{

		original.getAddress().setCountry(updated.getAddress().getCountry());
		original.getAddress().setState(updated.getAddress().getState());
		original.getAddress().setStreetAddress(updated.getAddress().getStreetAddress());
		original.getAddress().setCity(updated.getAddress().getCity());
		original.setEmail(updated.getEmail());
		original.setFirstName(updated.getFirstName());
		original.setGender(updated.getGender());
		original.setLastName(updated.getLastName());
		original.setPhoneNumber(updated.getPhoneNumber());
		original.setStatus(updated.getStatus());
		return original;
	}
	
}

