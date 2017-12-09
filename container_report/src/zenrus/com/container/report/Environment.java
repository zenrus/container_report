package zenrus.com.container.report;

import org.hibernate.Session;

public class Environment {

	private static Environment instance;
	
	private Session session;

	public static Environment getInstance() {
		if(instance == null){
			instance = new Environment();
		}
		return instance;
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
}
