package zenrus.com.container.filereader;

public class ExcelField {

	private String name;
	
	private boolean date;
	
	private int columnIndex;
	
	private String beanFieldName;
	
	private int width;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDate() {
		return date;
	}

	public void setDate(boolean date) {
		this.date = date;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public String getBeanFieldName() {
		return beanFieldName;
	}

	public void setBeanFieldName(String beanFieldName) {
		this.beanFieldName = beanFieldName;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	
}
