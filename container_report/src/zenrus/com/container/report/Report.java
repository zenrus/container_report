package zenrus.com.container.report;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import zenrus.com.container.exception.ApplicationException;
import zenrus.com.container.exception.DbException;
import zenrus.com.container.filereader.FileReader;
import zenrus.com.container.persistance.InputControl;

public class Report {

	public static void main(String[] args) throws ApplicationException {
		try{
			setUp();
			FileReader fileReader = new FileReader();
			
			//readExcel("C:/123.xls");
			InputControl.saveAll(fileReader.test("C:/excel"));
			
		}catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			throw new ApplicationException(e);
		}
	}

	protected static void setUp() throws Exception {
		
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		SessionFactory sf = null;
		try {
			sf = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
			Environment environment = Environment.getInstance();
			environment.setSessionFactory(sf);
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			if(sf != null){
				sf.close();
			}
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy( registry );
			throw new DbException("Can't init DB");
		}
	}
	

	
}
