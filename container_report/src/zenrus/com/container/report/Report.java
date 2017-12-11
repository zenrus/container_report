package zenrus.com.container.report;

import java.io.FileOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import zenrus.com.container.exception.ApplicationException;
import zenrus.com.container.filereader.FileReader;
import zenrus.com.container.persistance.HibernateUtil;
import zenrus.com.container.persistance.HibernateControl;

public class Report {

	private static final Logger LOG = LogManager.getLogger( Report.class );
	
	public static void main(String path,String fileName) throws ApplicationException {
		try(SessionFactory sf = HibernateUtil.getSessionFactory();
				Session session = sf.openSession();){
			Environment.getInstance().setSession(session);
			FileReader fileReader = new FileReader();
			
			//readExcel("C:/123.xls");
			HibernateControl.saveAll(fileReader.test(path));
			
			try( FileOutputStream fileOut = new FileOutputStream(fileName);){
				  Workbook wb = new HSSFWorkbook();
				  Sheet sheet = wb.createSheet("Отчет");
				  ReportContainersBuilder reportBuilder = new ReportContainersBuilder(wb, sheet);
				  reportBuilder.build();
				  
				  wb.write(fileOut);
				  fileOut.flush();
			}catch (Exception e) {
				LOG.error("Error write to file", e);
				throw new ApplicationException("Error write to file", e);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Error", e);
		}
		
	}
	
}
