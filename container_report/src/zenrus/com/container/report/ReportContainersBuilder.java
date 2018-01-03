package zenrus.com.container.report;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;

import zenrus.com.container.beans.OutputBean;
import zenrus.com.container.beans.Train;
import zenrus.com.container.exception.DbException;
import zenrus.com.container.filereader.ExcelControl;
import zenrus.com.container.filereader.ExcelField;
import zenrus.com.container.persistance.HibernateControl;

public class ReportContainersBuilder {

	private static final Logger LOG = LogManager.getLogger( ReportContainersBuilder.class );
	
	private static final String TRAIN = "Поезд ";

	private static final String BTLC = "БТЛЦ";

//	private static final Logger LOG = LogManager.getLogger( ReportContainersBuilder.class );
	
	private static final String TOTAL_REPORT_TITLE = "Всего за ";
	private static final String DATE_FORMAT = "dd/MM/yyyy hh:mm";
	private static final double FEE_ADDITIONAL_SERVICE = 25.00d;
	private static final double FEE_FORMING_KP = 775.00d;
	private static final String TOTAL_TRAIN_TITLE = "Итого за поезд";
	private static final String REPORT_TITLE_0 = "ОТЧЕТ за октябрь 2017 к договору  от 10.08.2015 № КП 20/Д043"; 
	private static final String REPORT_TITLE_1 = "о выполнении по поручению Клиента дополнительных транспортно-экспедиционных услуг  в Брестском железнодорожном узле"; 
	private static final String TOTAL_REPORT_CONT_TITLE = "Кол-во контейнеров за ";
	
	private static final String[] REPORT_COLUMNS = {"number","containerNumber","vagonNumber", "gettingOnBrw", "departure", "staLoading", 
						"staDirection", "feeAdditionalServices", "daysWorkBtlc","feeBtlc","feeFormingKp","feeTotal"};
	 
	
	private Cell activeCell;
	
	private Map<String,ExcelField> mapBeanToExcel = new HashMap<String,ExcelField>();
	
	private Sheet sheet ;
	
	private short rowNumber = 0;
	
	private List<Integer> totalRows = new ArrayList<Integer>();
	
	private Integer countOfContainers = 0;
	
	private Workbook wb;
	
	private CreationHelper createHelper;
	
	public ReportContainersBuilder(Workbook wb, Sheet sheet) {
		this.setWb(wb);
		this.setSheet(sheet);
		this.setMapBeanToExcel(ExcelControl.getMapBeanToFieldExcel(OutputBean.class));
	}
	
	public void build() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, DbException{
		String reportDate = "октябрь 2017";
		createHelper = wb.getCreationHelper();
		buildHeader();
	 	countOfContainers = buildTables();
	 	buildFooter(reportDate);
	}

	private void buildFooter(String reportDate) {
		
		CellStyle borderedStyle = getBorderedStyle();
		Font boldFont = wb.createFont();
		boldFont.setBold(true);
		Row row;
		Cell cell;
		row = sheet.createRow(rowNumber++);
	 	Cell cellTitle = null;
	 	
	 	CellStyle cs = wb.createCellStyle();
		cs.cloneStyleFrom(borderedStyle);
		cs.setFont(boldFont);
		
		CellStyle doubleStyle = wb.createCellStyle();
		doubleStyle.cloneStyleFrom(cs);
		doubleStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		Font font = wb.createFont();
		font.setBold(true);
		font.setFontHeight((short) (font.getFontHeight()+20));
		doubleStyle.setFont(font);
		CellStyle csr = wb.createCellStyle();
		csr.cloneStyleFrom(cs);
		csr.setAlignment(HorizontalAlignment.RIGHT);
		CellStyle csr2 = wb.createCellStyle();
			csr2.cloneStyleFrom(cs);
			csr2.setAlignment(HorizontalAlignment.RIGHT);
 		for(int cellnum = 0; cellnum < REPORT_COLUMNS.length; cellnum++) {
 			cell = row.createCell(cellnum);
 			cell.setCellStyle(cs);
 			if("number".equals(REPORT_COLUMNS[cellnum])) {
 				cell.setCellStyle(csr);
				cell.setCellValue(TOTAL_REPORT_CONT_TITLE + reportDate);
 			} else if("gettingOnBrw".equals(REPORT_COLUMNS[cellnum])) {	
 				cell.setCellValue(countOfContainers);
 			}else if("staLoading".equals(REPORT_COLUMNS[cellnum])) {		
 				cellTitle = cell;
 				cell.setCellStyle(csr2);
				cell.setCellValue(TOTAL_REPORT_TITLE + reportDate);
 			}else if("daysWorkBtlc".equals(REPORT_COLUMNS[cellnum]) 
 					|| "feeAdditionalServices".equals(REPORT_COLUMNS[cellnum])
 					|| "feeBtlc".equals(REPORT_COLUMNS[cellnum])
 					|| "feeFormingKp".equals(REPORT_COLUMNS[cellnum])
 					|| "feeTotal".equals(REPORT_COLUMNS[cellnum])
 					) {
 				cell.setCellFormula(getTotalFormula(totalRows, cellnum));
 				cell.setCellStyle(doubleStyle);
 			}
 		}
	 	sheet.addMergedRegion(new CellRangeAddress(cellTitle.getAddress().getRow(), cellTitle.getAddress().getRow(), cellTitle.getAddress().getColumn(),cellTitle.getAddress().getColumn()+1 ));
	 	sheet.addMergedRegion(new CellRangeAddress(cellTitle.getAddress().getRow(), cellTitle.getAddress().getRow(), 0, 2 ));
	}

	private void buildHeader() {
		CellStyle cellStyle = wb.createCellStyle();
		CellStyle cellStyle1 = wb.createCellStyle();
		CellStyle cellStyle2 = wb.createCellStyle();
		
		Font boldFont = wb.createFont();
		boldFont.setBold(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFont(boldFont);
		
		
		
		Row row = sheet.createRow(rowNumber++);
	    Cell cell = row.createCell((short) 0);
	    cell.setCellValue(REPORT_TITLE_0);	   
	    cell.setCellStyle(cellStyle);
	  
	    row = sheet.createRow(rowNumber++);
	    cell = row.createCell((short) 0);
	    cell.setCellValue(REPORT_TITLE_1);	   
	    cell.setCellStyle(cellStyle);
	    cellStyle2.setBorderBottom(BorderStyle.THIN);
	
	    sheet.addMergedRegion(new CellRangeAddress(0,0,0,REPORT_COLUMNS.length-1));
	    sheet.addMergedRegion(new CellRangeAddress(1,1,0,REPORT_COLUMNS.length-1));
	    
	    row =  sheet.createRow(rowNumber++);
	    row.setHeightInPoints(60.0f);
	    
	    for(int cellnum = 0; cellnum < REPORT_COLUMNS.length; cellnum++) {
	    	cell = row.createCell(cellnum);
	    	ExcelField ef = this.mapBeanToExcel.get(REPORT_COLUMNS[cellnum]);
	    	String tempValue = getTempString(ef.getWidth());
	    	cell.setCellValue(tempValue);
	    	sheet.autoSizeColumn(cellnum);
	    	cell.setCellValue(this.mapBeanToExcel.get(REPORT_COLUMNS[cellnum]).getName());
	    	cellStyle1.setAlignment(HorizontalAlignment.CENTER);
	    	cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
	    	cellStyle1.setWrapText(true);
	    	cell.setCellStyle(cellStyle1);
	    	cell.getCellStyle().setBorderTop(BorderStyle.THIN);
		    
	    	if(cellnum == 0) {
				 cell.getCellStyle().setBorderLeft(BorderStyle.THIN);
			}else if(cellnum == REPORT_COLUMNS.length -1 ) {
				 cell.getCellStyle().setBorderRight(BorderStyle.THIN);
			}
	    }
	    
	    row = sheet.createRow(rowNumber++);
    
		for (short cellnum = (short) 0; cellnum < REPORT_COLUMNS.length; cellnum++){
			cell = row.createCell(cellnum);
			if(cellnum == 0) {
				cellStyle2.setBorderLeft(BorderStyle.THIN);
			}else if(cellnum == REPORT_COLUMNS.length -1 ) {
				cellStyle2.setBorderRight(BorderStyle.THIN);
			}
			cell.setCellStyle(cellStyle2);
		 }
		 rowNumber++;
	}

	private Integer buildTables() throws DbException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Row row;
		Cell cell;
		CellStyle cellStyle = wb.createCellStyle();
		CellStyle borderedStyle = getBorderedStyle();
		
		Font boldFont = wb.createFont();
		boldFont.setBold(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFont(boldFont);
		
		CellStyle doubleStyle = wb.createCellStyle();
		doubleStyle.cloneStyleFrom(borderedStyle);
		doubleStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		doubleStyle.setAlignment(HorizontalAlignment.CENTER);
		
		CellAddress feeAdditionalServices = null;
		CellAddress feeBtlc = null;
		CellAddress feeFormingKp = null;
		List<Train> trains = HibernateControl.getTrains();
		
		sortTtrainsByNumber(trains);
	 
		CellStyle csR = wb.createCellStyle();
		csR.cloneStyleFrom(doubleStyle);
		csR.setFont(boldFont);
		CellStyle titleStyle = wb.createCellStyle();
		titleStyle.cloneStyleFrom(csR);
		titleStyle.setAlignment(HorizontalAlignment.RIGHT);
		
		CellStyle csDate = wb.createCellStyle();
		csDate.cloneStyleFrom(borderedStyle);
		csDate.setAlignment(HorizontalAlignment.LEFT);
		csDate.setDataFormat(createHelper.createDataFormat().getFormat(DATE_FORMAT));
		
		CellStyle csCal = wb.createCellStyle();
		csCal.cloneStyleFrom(borderedStyle);
		csCal.setAlignment(HorizontalAlignment.LEFT);
		csCal.setDataFormat(createHelper.createDataFormat().getFormat(DATE_FORMAT));
		
		for(Train train : trains) {
	 		short firstRow = rowNumber;
	 		row = sheet.createRow(rowNumber++);
	 		cell = row.createCell((short) 0);
	 		cell.setCellValue(getTitleColumn(train));	   
	 		cell.setCellStyle(cellStyle);
	 		
	 		countOfContainers += train.getContainers().size();
	 		
	 		sheet.addMergedRegion(new CellRangeAddress(rowNumber-1,rowNumber-1,0,REPORT_COLUMNS.length-1));
	 		Set<String> vagonBTLC = new HashSet<String>();
		 	for(OutputBean bean : train.getContainers()) {
		 		if(!BTLC.equals(bean.getOwner()) || vagonBTLC.contains(bean.getVagonNumber()) ) {
		 			bean.setVagonNumber("");
		 		}
				row = sheet.createRow(rowNumber++);
		 		for(int cellnum = 0; cellnum < REPORT_COLUMNS.length; cellnum++) {
			 		cell = row.createCell(cellnum);
			 		cell.setCellStyle(borderedStyle);
			 		if("feeAdditionalServices".equals(REPORT_COLUMNS[cellnum])) {
			 			feeAdditionalServices = new CellAddress(row.getRowNum(), cellnum);
			 			cell.setCellValue(FEE_ADDITIONAL_SERVICE);
			 			cell.setCellStyle(doubleStyle);
			 		}else if("feeBtlc".equals(REPORT_COLUMNS[cellnum])) {
			 			feeBtlc = new CellAddress(row.getRowNum(), cellnum);
			 			cell.setCellStyle(doubleStyle);
			 		}else if("feeFormingKp".equals(REPORT_COLUMNS[cellnum])) {
			 			feeFormingKp = new CellAddress(row.getRowNum(), cellnum);
			 			cell.setCellStyle(doubleStyle);
			 		}else if("feeTotal".equals(REPORT_COLUMNS[cellnum])) {
			 			cell.setCellFormula(feeAdditionalServices.formatAsString()+"+"+feeBtlc.formatAsString()+"+"+feeFormingKp.formatAsString());
			 			cell.setCellStyle(doubleStyle);
			 		}else{
			 			if(this.mapBeanToExcel.get(REPORT_COLUMNS[cellnum]).isDate()) {
			 				
			 				cell.setCellType(CellType.NUMERIC);
			 			}
			 				Object value = PropertyUtils.getProperty(bean, this.mapBeanToExcel.get(REPORT_COLUMNS[cellnum]).getBeanFieldName());
			 				setCellValue(cell, value, csDate, csCal);
			 			
			 		}
		 		}
	 		}
	 		
	 		row = sheet.createRow(rowNumber++);
	 		totalRows.add(rowNumber-1);
	 		
	 		for(int cellnum = 0; cellnum < REPORT_COLUMNS.length; cellnum++) {
	 			cell = row.createCell(cellnum);
	 			cell.setCellStyle(csR);
	 			if("staDirection".equals(REPORT_COLUMNS[cellnum])) {
	 				cell.setCellValue(TOTAL_TRAIN_TITLE);
	 				cell.setCellStyle(titleStyle);
	 			}else if("daysWorkBtlc".equals(REPORT_COLUMNS[cellnum])) {
	 				cell.setCellFormula("SUM("+new CellAddress(firstRow, cellnum).formatAsString()+":"+new CellAddress(rowNumber-2, cellnum).formatAsString()+")");
	 			}else if("feeAdditionalServices".equals(REPORT_COLUMNS[cellnum])) {
	 				feeAdditionalServices = new CellAddress(row.getRowNum(), cellnum);
	 				cell.setCellFormula("SUM("+new CellAddress(firstRow, cellnum).formatAsString()+":"+new CellAddress(rowNumber-2, cellnum).formatAsString()+")");
	 			}else if("feeBtlc".equals(REPORT_COLUMNS[cellnum])) {
	 				feeBtlc = new CellAddress(row.getRowNum(), cellnum);
	 				cell.setCellFormula("SUM("+new CellAddress(firstRow, cellnum).formatAsString()+":"+new CellAddress(rowNumber-2, cellnum).formatAsString()+")");
	 			}else if("feeFormingKp".equals(REPORT_COLUMNS[cellnum])) {
	 				feeFormingKp = new CellAddress(row.getRowNum(), cellnum);
	 				cell.setCellValue(FEE_FORMING_KP);
	 			}else if("feeTotal".equals(REPORT_COLUMNS[cellnum])) {
	 				cell.setCellFormula(feeAdditionalServices.formatAsString()+"+"+feeBtlc.formatAsString()+"+"+feeFormingKp.formatAsString());
	 			}
	 		}
	 	}
		return countOfContainers;
	}

	private void sortTtrainsByNumber(List<Train> trains) {
		trains.sort(new Comparator<Train>() {
			@Override
			public int compare(Train o1, Train o2) {
				Integer i1 = 0;
				Integer i2 = 0;
				try{
					i1 = Integer.parseInt(o1.getTitle().split("_")[0]);
					i2 = Integer.parseInt(o2.getTitle().split("_")[0]);
				}catch (Exception e) {
					LOG.error(e);
				}
				
				return	i1 - i2;
				
			}
		});
	}

	private CellStyle getBorderedStyle() {
		CellStyle borderedStyle =wb.createCellStyle();
		borderedStyle.setBorderBottom(BorderStyle.THIN);
		borderedStyle.setBorderLeft(BorderStyle.THIN);
		borderedStyle.setBorderRight(BorderStyle.THIN);
		borderedStyle.setBorderTop(BorderStyle.THIN);
		return borderedStyle;
	}

	private String getTempString(int width) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < width; i++) {
			sb.append("@");
		}
		return sb.toString();
	}

	private String getTotalFormula(List<Integer> totalRows, int cellnum) {
		String formula = "";
		for(Integer r : totalRows) {
			if(!"".equals(formula)){
				formula += "+";
			}
			formula += new CellAddress(r, cellnum).formatAsString();
		}
		return formula;
	}
	
	private void setCellValue(Cell cell, Object value, CellStyle csDate, CellStyle csCal ) {
		if(value != null) {
			if(value instanceof String) {
				cell.setCellValue((String) value );
			}else if(value instanceof Date) {
				cell.setCellStyle(csDate);
				cell.setCellValue((Date)value);
			}else if(value instanceof Calendar) {
				cell.setCellStyle(csCal);
				cell.setCellValue((Calendar)value);
			}else if(value instanceof Double ) {
				cell.setCellValue((double)value);
			}else if( value instanceof Integer) {
				cell.setCellValue((double) new Double(value.toString()));
			}else if(value instanceof Boolean) {
				cell.setCellValue((boolean)value);
			}
		}else {
			cell.setCellValue("");
		}
	}

	private String getTitleColumn(Train train) {
		return TRAIN+train.getIndex()+" ("+train.getTitle().replace('_', '/')+")";
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

	public short getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(short rowNumber) {
		this.rowNumber = rowNumber;
	}

	public List<Integer> getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(List<Integer> totalRows) {
		this.totalRows = totalRows;
	}
	
}
