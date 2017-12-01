package zenrus.com.container.filereader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import zenrus.com.container.annotation.ExcelColumn;
import zenrus.com.container.beans.InputBean;
import zenrus.com.container.exception.FileException;

public class ExcelControl {

	public static List<InputBean> readInputfile(File excelFile) throws FileException{
		List<InputBean> beans = new ArrayList<InputBean>();
	
		try {
			Map<String, ExcelField> mapColumn = getMapExcelToFieldExcel(InputBean.class);
			List<Map<String,Object>> excelRows = ExcelReader.readExcel(excelFile, 2, InputBean.class);
			
			String fileName = excelFile.getName();
			String titleTrain = fileName.substring(0, fileName.indexOf(' '));
			for(Map<String, Object> row : excelRows){
				//TODO End of data
				if(row.get("¹ ï/ï") != null){
					InputBean bean = new InputBean();
					fill(bean, row, mapColumn);
					
					bean.setTitleTrain(titleTrain);
					beans.add(bean);
					
					System.out.print(" row ");
					for(String s : row.keySet()){
						System.out.print( s + " = " + row.get(s) + ";\t" );
					}
					System.out.println("");
				}
			}
			
			
		
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new FileException("Error read file" + excelFile.getName());
		}
		return beans;
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
				result.put(field.getName(), excelField);
			}
		}
		
		return result;
		
	}
	
	
}
