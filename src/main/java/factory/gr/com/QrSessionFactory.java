package factory.gr.com;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class QrSessionFactory {

	private static SessionFactory sessionFactory = null;

	private static SessionFactory getFactory() {
		if (sessionFactory == null) {
			sessionFactory = new Configuration().configure("hbm/hibernate.cfg.xml").buildSessionFactory();
		}
		return sessionFactory;
	}

	public static Session startTransaction() {
		Session session = getFactory().openSession();
		session.beginTransaction();
		return session;
	}

	public static void endTransaction(Session session) {
		session.getTransaction().commit();
		session.close();
	}
}
