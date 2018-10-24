package dao.gr.com;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import entity.gr.com.Account;
import entity.gr.com.Address;
import entity.gr.com.AlertProfile;
import factory.gr.com.QrSessionFactory;




public class AlertProfileDao {

@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public List<AlertProfile> getAll(int accountId) {
		
		List<AlertProfile> alertprofile=null;
		Session session = QrSessionFactory.startTransaction();
		String sql;
		if(accountId!=0){
			 sql= "SELECT * FROM alertprofile where ACCOUNT_ID="+accountId;
			}
		else{
			sql = "SELECT * FROM alertprofile";
			}
		
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(AlertProfile.class);
		alertprofile = query.list();
		QrSessionFactory.endTransaction(session);
		return alertprofile;
		}
		
		public Boolean addAlertProfile(AlertProfile alertprofile)
		{
			try{
				int accountID=alertprofile.getAccountId();
				Session session = QrSessionFactory.startTransaction();
				 Criteria criteria = session.createCriteria(Account.class)
		                    .add(Restrictions.eq("id", accountID));
				 Object result = criteria.uniqueResult();
				 Account account=(Account) result;
				alertprofile.setAccount(account);
				session.save(alertprofile);
				QrSessionFactory.endTransaction(session);
			}catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
			
		}

		@SuppressWarnings("deprecation")
		public static List<AlertProfile> matchProfile(Address address,int accountId)
		{
			Session session = QrSessionFactory.startTransaction();
			
		/*	List<AlertProfile> alertprofiles=null;
			Criteria cr = session.createCriteria(AlertProfile.class);
			cr.createCriteria("account").add(Restrictions.eq("id", accountId));
			Criterion city = Restrictions.eq("city", address.getCity());
	        Criterion country = Restrictions.eq("country", address.getCountry());
	        LogicalExpression orExp = Restrictions.or(city,country);
			cr.add(orExp);
			if(alertprofiles.size()==0){	
				return alertprofiles;
				} 
			else{
				return alertprofiles;
				}
			*/
			
			String sql = "SELECT * FROM qr.alertprofile where \r\n" + 
					"account_id="+accountId+" and \r\n" + 
					"(city='"+address.getCity()+"' or country='"+address.getCountry()+"');";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(AlertProfile.class);
			List<AlertProfile> results=null;
			results = query.list();
			return results;
			
			
		}
		
		public Boolean deleteAlertProfile( int profileId)
		{
			try {
				Session session = QrSessionFactory.startTransaction();
				Query q = session.createQuery("DELETE FROM " + AlertProfile.class.getName() + " WHERE ID = " + profileId);
				q.executeUpdate();
				QrSessionFactory.endTransaction(session);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		public Boolean updateAlertProfile(AlertProfile alertprofile){
			try {
				Session session = QrSessionFactory.startTransaction();
				session.evict(alertprofile);
				session.update(alertprofile);
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
