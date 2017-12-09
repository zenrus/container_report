package zenrus.com.container.filereader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import zenrus.com.container.exception.FileException;

public class ExcelReader {

	private static final Logger LOG = LogManager.getLogger( ExcelReader.class );
	
	private static final String DATE_INPUT_FILE_FORMAT = "dd.MM.yyyy HH:mm";
	private static final String DATE_INPUT_FILE_FORMAT2 = "dd/MM/yyyy H:mm";
	

	protected static List<Map<String,Object>> readExcel(File file, int headerRow, Class<?> beanClass) throws IOException, EncryptedDocumentException, InvalidFormatException {
		
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Map<Integer,String> header = new HashMap<Integer,String>();
       
		try(	InputStream inputStream = new FileInputStream(file);
				Workbook workbook = WorkbookFactory.create(new BufferedInputStream(inputStream));	){
		      
	        Sheet firstSheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = firstSheet.iterator();
	        Map<String, ExcelField> mapColumn = ExcelControl.getMapExcelToFieldExcel(beanClass); 
	        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	        int indexRow = 0; 
	        while (iterator.hasNext()) {
	        	indexRow++;
	        	Row nextRow = iterator.next();
	        	Iterator<Cell> cellIterator = nextRow.cellIterator();
	        	
	        	if(indexRow < headerRow){
	        		continue;
	        	}else if(indexRow == headerRow){
	        		 Integer cellNumber = 0; 
	        		 while (cellIterator.hasNext()) {
	        			 Cell cell = cellIterator.next();
	        			 cellNumber++;
	                     header.put(cellNumber, getCellValue(cell,false,evaluator).toString());	                
	                 }
	        		 continue;
	        	}
	            
	        	//TODO Multikey
	        	Map<String, Object> row = new HashMap<String, Object>();
	        	Integer cellNumber = 0; 
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                if(cellNumber == 0 &&  cell.getColumnIndex() != 0){
                   	 break;
                    }
	                ++cellNumber;
	                ExcelField excelField = mapColumn.get(header.get(cellNumber));
	                if(excelField != null && excelField.isDate()){
	                	row.put(header.get(cellNumber), getCellValue(cell,true,evaluator));
	                }else{
	                	row.put(header.get(cellNumber), getCellValue(cell,false,evaluator));
	                }
	            }
	            result.add(row);
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
        
		return result;
	}
	
	private static Object getCellValue(CellValue cellValue, boolean date) throws FileException{
		if (cellValue != null) {
			switch (cellValue.getCellTypeEnum()) {
			case BOOLEAN:
				return cellValue.getBooleanValue();
			case NUMERIC:
				Double cellDoubleValue = cellValue.getNumberValue();
				if (DateUtil.isValidExcelDate(cellDoubleValue)) {
					return DateUtil.getJavaDate(cellDoubleValue);
				} else {
					return cellDoubleValue;
				}
			case STRING:
				if(date){
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_INPUT_FILE_FORMAT);
					SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(DATE_INPUT_FILE_FORMAT2);
					try {
						return simpleDateFormat.parse(cellValue.getStringValue());
					} catch (ParseException e) {
						try {
							return simpleDateFormat2.parse(cellValue.getStringValue());
						}catch (ParseException e2) {
							LOG.error("Date parse ERROR '" +  cellValue.getStringValue()+"'  format = " + DATE_INPUT_FILE_FORMAT, e2);
							return null;
						}
					}
				}else{
					return replaceUmlauts(cellValue.getStringValue());
				}
			case BLANK:
				return null;
			case ERROR:
				throw new FileException("Cell type ERROR");
			default:
				throw new FileException("unsupported column type='" + cellValue.getCellTypeEnum() + "'");
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
		return false;
	}
	
	protected static Object getCellValue(Cell cell, boolean date, FormulaEvaluator evaluator) throws FileException{
		if (cell != null) {
			CellValue cellValue = evaluator.evaluate(cell);
			return getCellValue(cellValue, date);
		} else {
			return null;
		}
	}
}
