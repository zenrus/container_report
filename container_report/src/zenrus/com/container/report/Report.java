package zenrus.com.container.report;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import zenrus.com.container.beans.InputBean;

public class Report {

	public static void main(String[] args) {
		try{
			
			readExcel("C:/123.xls");
			setUp();
			Environment environment = Environment.getInstance();
			SessionFactory sf = environment.getSessionFactory();			
			Session session = sf.openSession();
			session.beginTransaction();
			session.save( new InputBean() );
			session.flush();
			session.getTransaction().commit();
			session.close();
			sf.close();
		}catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
	}

	protected static void setUp() throws Exception {
		
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		try {
			Environment environment = Environment.getInstance();
			environment.setSessionFactory(new MetadataSources( registry ).buildMetadata().buildSessionFactory());
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy( registry );
		}
	}
	
	protected static void readExcel(String path) throws IOException, EncryptedDocumentException, InvalidFormatException {
		String excelFilePath = path;
        InputStream inputStream = new FileInputStream(new File(excelFilePath));
       
      
        Workbook workbook = WorkbookFactory.create(new BufferedInputStream(inputStream));
        
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
         
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
             
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                System.out.print(getCellValue(cell,false,evaluator));
                System.out.print(" - ");
            }
            System.out.println();
        }
        workbook.close();
        inputStream.close();
	}
	
	private static Object getCellValue(CellValue cellValue, boolean date){
		if (cellValue != null) {
			switch (cellValue.getCellTypeEnum()) {
			case BOOLEAN:
				return cellValue.getBooleanValue();
			case NUMERIC:
				Double cellDoubleValue = cellValue.getNumberValue();
				if (date){
					if (DateUtil.isValidExcelDate(cellDoubleValue)) {
						return DateUtil.getJavaDate(cellDoubleValue);
					} else {
						//throw new UploaderComponentException("Invalid date value");
					}
				} else {
					return cellDoubleValue;
				}
			case STRING:
				return replaceUmlauts(cellValue.getStringValue());
			case BLANK:
				return null;
			case ERROR:
				return null;
			//	throw new UploaderComponentException("Cell type ERROR");
			default:
				return null;
			///	throw new UploaderComponentException("unsupported column type='" + cellValue.getCellTypeEnum() + "'");
			}
		} else {
			return null;
		}
	}
	
	private static String replaceUmlauts(String input) {
		if (isReplaceUmlauts()) {
			input = Normalizer.normalize(input, Normalizer.Form.NFD);
			return input.replaceAll("\\p{M}", "");
		} else {
			return input;
		}
	}

	private static boolean isReplaceUmlauts() {
		return true;
	}
	
	protected static Object getCellValue(Cell cell, boolean date, FormulaEvaluator evaluator){
		if (cell != null) {
			CellValue cellValue = evaluator.evaluate(cell);
			return getCellValue(cellValue, date);
		} else {
			return null;
		}
	}
	
}
