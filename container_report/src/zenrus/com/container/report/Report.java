package zenrus.com.container.report;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import zenrus.com.container.exception.ApplicationException;
import zenrus.com.container.exception.DbException;
import zenrus.com.container.filereader.FileReader;
import zenrus.com.container.persistance.InputControl;

public class Report {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws ApplicationException {
		try{
			setUp();
			FileReader fileReader = new FileReader();
			
			//readExcel("C:/123.xls");
			InputControl.saveAll(fileReader.test("C:/excel"));
			
			
			try( FileOutputStream fileOut = new FileOutputStream("C:/report.xls");){
				  Workbook wb = new HSSFWorkbook();
				  Sheet sheet = wb.createSheet("Отчет");
				  ReportBuilder reportBuilder = new ReportBuilder(wb, sheet);
				  reportBuilder.buildHeader();
				  
				  wb.write(fileOut);
				  fileOut.flush();
			}catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationException("Error write to file", e);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Error", e);
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
