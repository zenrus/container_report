package zenrus.com.container.filereader.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import zenrus.com.container.filereader.ExcelField;
import zenrus.com.container.filereader.SourceControl;

public class XMLReader extends SourceReader {

	private static final Logger LOG = LogManager.getLogger( XMLReader.class );
	
	private static final String DATE_INPUT_FILE_FORMAT = "dd.MM.yyyy HH:mm";
	private static final String DATE_INPUT_FILE_FORMAT2 = "dd/MM/yyyy H:mm";
	
	
	@Override
	protected List<Map<String, Object>> read(File file, int headerRow, Class<?> beanClass)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
	
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Map<Integer,String> header = new HashMap<Integer,String>();
		Map<String, ExcelField> mapColumn = SourceControl.getMapExcelToFieldExcel(beanClass); 
		
		XMLStreamReader xmlr = null;
		
		try(InputStream inputStream = new FileInputStream(file)) {
	            xmlr = XMLInputFactory.newInstance().createXMLStreamReader(file.getName(), inputStream);
	           
	           
	            int rowNumber = 0;
	            while (xmlr.hasNext()) {
	                xmlr.next();
	                int event = xmlr.getEventType();
	                switch(event) {
		                case(XMLStreamConstants.START_ELEMENT):		                	
		                	if( "Row".equals(xmlr.getLocalName()) && ++rowNumber >= headerRow ) {
		                		Integer cellNumber = 0; 
		                		Map<String, Object> row = new HashMap<String, Object>();;
		    	                boolean isTableRow = false;
		    	                while(event != XMLStreamConstants.END_ELEMENT || !"Row".equals(xmlr.getLocalName()) ) {
		    	                	xmlr.next();
			    	                event = xmlr.getEventType();
		    	                	if(event == XMLStreamConstants.START_ELEMENT && "Cell".equals(xmlr.getLocalName())) {
		    	                			boolean hasIndex = false;
		    	                			for(int i = 0, n = xmlr.getAttributeCount(); i < n; ++i) {
		    	                                if(cellNumber == 0 &&  "Index".equals(xmlr.getAttributeLocalName(i)) ){
		    	                                	hasIndex = true;
		    	                                	break;
		    	                                }
		    	                			}
		    	                			if(!hasIndex && !isTableRow) {
		    	                				isTableRow = true;
		    	                			}
		    	                			if(isTableRow) {
				    	                		cellNumber++;	
				    	                		while(event!=XMLStreamConstants.CHARACTERS) {
				    	                			xmlr.next();
				    		    	                event = xmlr.getEventType();
				    	                		}
				    	                		String value = xmlr.getText();
				    	                		if(rowNumber == headerRow) {			    	                			 
				    	                			header.put(cellNumber, value);
				    	                		}else {
				    	                			
				    	                			ExcelField excelField = mapColumn.get(header.get(cellNumber));
				    	         	                if(excelField != null && excelField.isDate()){
				    	         	                	row.put(header.get(cellNumber), valueToDate(value));
				    	         	                }else if(excelField != null){
				    	         	                	row.put(header.get(cellNumber), value);
				    	         	                }
				    	                		}
				    	                	}else {
				    	                		break;
				    	                	}
		    	                		
		    	                	}
		    	                }
		    	                if(rowNumber != headerRow && isTableRow) {
		    	                	result.add(row);
		    	                }
		                	}
		                	break;
		                	
	                }
	               
	          }
	    } catch (XMLStreamException ex) {
	            ex.printStackTrace();
	    }finally{
	    	if(xmlr != null) {
	    		try {
					xmlr.close();
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}
	    	}
	    }

		return result;
	}

	private Object valueToDate(String value) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_INPUT_FILE_FORMAT);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(DATE_INPUT_FILE_FORMAT2);
		try {
			return simpleDateFormat.parse(value);
		} catch (ParseException e) {
			try {
				return simpleDateFormat2.parse(value);
			}catch (ParseException e2) {
				if(!"".equals(value.trim())) {
					LOG.error("Date parse ERROR '" +  value+"'  format = " + DATE_INPUT_FILE_FORMAT, e2);
				}
			}
		}
		return null;
	}

	@Override
	public String getTitleTrain() {
		return getFileName().substring(0, getFileName().indexOf('.'));
	}

}
