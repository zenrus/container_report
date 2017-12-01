package zenrus.com.container.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import zenrus.com.container.beans.OutputBean;
import zenrus.com.container.filereader.ExcelControl;
import zenrus.com.container.filereader.ExcelField;

public class ReportBuilder {

	private static final String REPORT_TITLE_0 = "ОТЧЕТ за октябрь 2017 к договору  от 10.08.2015 № КП 20/Д043"; 
	private static final String REPORT_TITLE_1 = "о выполнении по поручению Клиента дополнительных транспортно-экспедиционных услуг  в Брестском железнодорожном узле"; 

	
	private static final String[] REPORT_COLUMNS = {"number","containerNumber","vagonNumber", "gettingOnBrw", "departure", "staLoading", 
						"staDirection", "feeAdditionalServices", "daysWorkBtlc","feeBtlc","feeFormingKp","feeTotal"}; 
	
	private Cell activeCell;
	
	private Map<String,ExcelField> mapBeanToExcel = new HashMap<String,ExcelField>();
	
	private Sheet sheet ;
	
	private Workbook wb;
	
	public ReportBuilder(Workbook wb, Sheet sheet) {
		this.setWb(wb);
		this.setSheet(sheet);
		this.setMapBeanToExcel(ExcelControl.getMapBeanToFieldExcel(OutputBean.class));
	}

	public void buildHeader(){
		CellStyle cellStyle = wb.createCellStyle();
		CellStyle cellStyle1 = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		
		Row row = sheet.createRow((short) 0);
	    Cell cell = row.createCell((short) 0);
	    cell.setCellValue(REPORT_TITLE_0);	   
	    cell.setCellStyle(cellStyle);
	  
	    
	    row = sheet.createRow((short) 1);
	    cell = row.createCell((short) 0);
	    cell.setCellValue(REPORT_TITLE_1);	   
	    cell.setCellStyle(cellStyle);
	    
	    sheet.addMergedRegion(new CellRangeAddress(0,0,0,REPORT_COLUMNS.length-1));
	    sheet.addMergedRegion(new CellRangeAddress(1,1,0,REPORT_COLUMNS.length-1));
	    
	    row =  sheet.createRow((short) 3);
	    row.setHeightInPoints(60.0f);
	    for(int i = 0; i < REPORT_COLUMNS.length; i++) {
	    	cell = row.createCell(i);
	    	cell.setCellValue(this.mapBeanToExcel.get(REPORT_COLUMNS[i]).getName());
	    	cellStyle1.setAlignment(HorizontalAlignment.CENTER);
	    	cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
	    	cellStyle1.setWrapText(true);
	    	cell.setCellStyle(cellStyle1);
	    	sheet.autoSizeColumn(i);
	    }
	}
	
	private String getTitleColumn(String string) {
		
		return null;
	}

	public void buildTrainTable(List<OutputBean> beans){
		
	}
	
	public void buildTitleTrainTable(){
		
	}

	public Cell getActiveCell() {
		return activeCell;
	}

	public void setActiveCell(Cell activeCell) {
		this.activeCell = activeCell;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public Workbook getWb() {
		return wb;
	}

	public void setWb(Workbook wb) {
		this.wb = wb;
	}

	public Map<String,ExcelField> getMapBeanToExcel() {
		return mapBeanToExcel;
	}

	public void setMapBeanToExcel(Map<String,ExcelField> mapBeanToExcel) {
		this.mapBeanToExcel = mapBeanToExcel;
	}
	
	
}
