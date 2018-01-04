package zenrus.com.container.filereader;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import zenrus.com.container.annotation.ExcelColumn;
import zenrus.com.container.beans.InputBean;
import zenrus.com.container.exception.FileException;
import zenrus.com.container.filereader.reader.SourceReader;
import zenrus.com.container.filereader.reader.XMLReader;

public class SourceControl {

	
	private static final Logger LOG = LogManager.getLogger( SourceControl.class );
	
	public static List<InputBean> readInputfile(File excelFile) throws FileException{
		List<InputBean> beans = new ArrayList<InputBean>();
	
		try {
			Map<String, ExcelField> mapColumn = getMapExcelToFieldExcel(InputBean.class);
			SourceReader reader = getReader();
			List<Map<String,Object>> excelRows = reader.readFromFile(excelFile, 2, InputBean.class);
			
			String titleTrain = reader.getTitleTrain();
			for(Map<String, Object> row : excelRows){
				//TODO End of data
				if(row.get("¹ ï/ï") != null){
					InputBean bean = new InputBean();
					fill(bean, row, mapColumn);
					
					bean.setTitleTrain(titleTrain);
					beans.add(bean);
					
					LOG.debug(" row ");
					for(String s : row.keySet()){
						LOG.debug( s + " = " + row.get(s) + ";\t" );
					}
					LOG.debug("");
				}
			}
		} catch (FileException e) {
			LOG.error(e);
			throw new FileException("Error read file" + excelFile.getName());
		}
		
		return beans;
	}

	private static SourceReader getReader() {
	//	return new ExcelReader();
		return new XMLReader();
	}
	
	private static void fill(InputBean bean, Map<String, Object> row, Map<String, ExcelField> mapColumn) throws FileException {
		for(String s : mapColumn.keySet()){
//			System.out.println(s + " - " +mapColumn.get(s).getBeanFieldName() + " " +row.get(s) + " " +row.get(s).getClass());
			try {
				ExcelField excelField =  mapColumn.get(s);
				
				if(row.get(s) != null){
					//XXX HARDCODE
					
					if( "length".equals(excelField.getBeanFieldName()) ){
						double value = Double.parseDouble( ((String)row.get(s)).replace(",",".") );
						row.put(s, value);
					}
					
					
					BeanUtils.setProperty(bean, excelField.getBeanFieldName(), row.get(s));
					
					if( "departure".equals(excelField.getBeanFieldName()) ){
						Calendar calendar = Calendar.getInstance();
						Calendar departure = Calendar.getInstance();
						departure.setTime(bean.getDeparture());
						calendar.clear();
						calendar.set(departure.get(Calendar.YEAR), departure.get(Calendar.MONTH), 1);
						bean.setReportDate(departure.getTime());
					}
				}
				
			} catch (InvocationTargetException | IllegalAccessException e) {
				throw new FileException("can't parse field "+ s, e);
			} 
			
		}
		
	}

	public static Map<String,ExcelField> getMapExcelToFieldExcel(Class<?> clazz){
		Map<String,ExcelField> result = new HashMap<String,ExcelField>();
		
		for(Field field : clazz.getDeclaredFields()){
			ExcelField excelField = new ExcelField();
			ExcelColumn excelColumn = field.getDeclaredAnnotation(ExcelColumn.class);
			if(excelColumn != null){
				excelField.setColumnIndex(excelColumn.numberColumn());
				excelField.setDate(excelColumn.isDate());
				excelField.setName(excelColumn.name());
				excelField.setBeanFieldName(field.getName());
				result.put(excelColumn.name(), excelField);
			}
		}
		
		return result;
		
	}
	
	public static Map<String,ExcelField> getMapBeanToFieldExcel(Class<?> clazz){
		Map<String,ExcelField> result = new HashMap<String,ExcelField>();
		
		for(Field field : clazz.getDeclaredFields()){
			ExcelField excelField = new ExcelField();
			ExcelColumn excelColumn = field.getDeclaredAnnotation(ExcelColumn.class);
			if(excelColumn != null){
				excelField.setColumnIndex(excelColumn.numberColumn());
				excelField.setDate(excelColumn.isDate());
				excelField.setName(excelColumn.name());
				excelField.setBeanFieldName(field.getName());
				excelField.setWidth(excelColumn.width());
				result.put(field.getName(), excelField);
			}
		}
		
		return result;
		
	}
	
	
}
