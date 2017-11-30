package zenrus.com.container.persistance;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import zenrus.com.container.beans.InputBean;
import zenrus.com.container.report.Environment;

public class InputControl {

	public static void saveAll(List<InputBean> beans){
		Environment environment = Environment.getInstance();
		Transaction transaction = null;
		
		try( SessionFactory sf = environment.getSessionFactory();
				Session session = sf.openSession()){
				
			transaction = session.beginTransaction();
			
			for(InputBean bean : beans){
				System.out.println(bean);
				session.save( bean );
			}
			session.flush();
			transaction.commit();
		}catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}
		
	}
}
