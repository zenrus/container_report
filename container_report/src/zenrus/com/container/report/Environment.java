package zenrus.com.container.report;

import org.hibernate.SessionFactory;

public class Environment {

	private static Environment instance;
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public static Environment getInstance() {
		if(instance == null){
			instance = new Environment();
		}
		return instance;
	}

}
