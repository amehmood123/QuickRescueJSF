package dao.gr.com;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import entity.gr.com.Account;
import factory.gr.com.QrSessionFactory;

@SuppressWarnings("deprecation")
public class AccountDao {

	public List<Account> getAll() {

		List<Account> accounts = null;
		Session session = QrSessionFactory.startTransaction();
		String sql = "SELECT * FROM account";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Account.class);
		accounts = query.list();
		QrSessionFactory.endTransaction(session);
		return accounts;
	}

	public Boolean validateAccount(String email) {
		Session session = QrSessionFactory.startTransaction();
		String sql = "SELECT * FROM account where email='" + email + "'";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Account.class);
		Account account = (Account) query.uniqueResult();
		QrSessionFactory.endTransaction(session);
		if (account != null)
			return true;
		else
			return false;
	}

	public Account validateLogin(Account account) {
		Session session = QrSessionFactory.startTransaction();
		String sql = "SELECT * FROM account where email='" + account.getEmail() + "'";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Account.class);
		Account accounts = (Account) query.uniqueResult();
		QrSessionFactory.endTransaction(session);

		if (accounts.getPassword().equals(account.getPassword()))
			return accounts;
		else
			return null;
	}

	public Boolean addAccount(Account account) {
		try {
			Session session = QrSessionFactory.startTransaction();
			session.save(account);
			QrSessionFactory.endTransaction(session);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Boolean deleteAccount(int id) {
		try {
			Session session = QrSessionFactory.startTransaction();
			/*
			 * Query q = session.createQuery("delete " + Contact.class.getName() +
			 * " where ACCOUNT_ID = " + id); q.executeUpdate(); q =
			 * session.createQuery("delete " + Account.class.getName() + " where ID = " +
			 * id); q.executeUpdate();
			 */
			Account account = session.get(Account.class, id);
			session.delete(account);
			QrSessionFactory.endTransaction(session);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Boolean updateAccount(Account account) {
		try {
			Session session = QrSessionFactory.startTransaction();
			session.evict(account);
			session.update(account);
			session.flush();
			session.clear();
			QrSessionFactory.endTransaction(session);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
