package zenrus.com.container.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import zenrus.com.container.annotation.ExcelColumn;

@Entity
@Table(name = "INPUT")
public class InputBean {

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return idInput + " " + containerNumber +" " + length; 
	}
	
	@Id
	@Column(name = "ID_INPUT")
	@GeneratedValue
	private Integer idInput = 0;
	
	@Column(name = "REPORT_DATE")
	private Date reportDate;

	@Column(name = "TITLE_TRAIN")
	private String titleTrain;
	
	@Column(name = "NN")
	@ExcelColumn(name = "№ п/п")
	private Integer number;
	
	@Column(name = "CONTAINER_NUMBER")
	@ExcelColumn(name = "Контейнер")
	private String containerNumber;
	
	@Column(name = "CONTAINER_TYPE")
	@ExcelColumn(name = "Тип")
	private Integer containerType;
	
	@Column(name = "PRIZNOK_POGRUZKI")
	@ExcelColumn(name = "Гр/пор")
	private String fillingContainer;
	
	@Column(name = "SMSG")
	@ExcelColumn(name = "СМГС")
	private String smgs;
	
	@Column(name = "GETTING_ON")
	@ExcelColumn(name = "Принят на БЧ", isDate = true)
	private Date gettingOnBrw;
	
	@Column(name = "PPV")
	@ExcelColumn(name = "ППВ")
	private Integer ppv;
	
	@Column(name = "STA_LOADING")
	@ExcelColumn(name = "Ст.отправления")
	private String staLoading;
	
	@Column(name = "STA_DIRECTION")
	@ExcelColumn(name = "Ст.назначения")
	private String staDirection;
	
	@Column(name = "VAGON_NUMBER")
	@ExcelColumn(name = "Вагон")
	private String vagonNumber;
	
	@Column(name = "PRIN")
	@ExcelColumn(name = "Прин.")
	private Integer prin;
	
	@Column(name = "LENGTH")
	@ExcelColumn(name = "Усл.длина")
	private Double length;
	
	@Column(name = "DATE_RELOADING")
	@ExcelColumn(name = "Перегружен", isDate = true)
	private Date dateReloading;
	
	@Column(name = "NUMBER_TRAIN")
	@ExcelColumn(name = "№ поезда")
	private Integer numberTrain;
	
	@Column(name = "INDEX_TRAIN")
	@ExcelColumn(name = "Индекс поезда")
	private String indexTrain;
	
	@Column(name = "DEPARTURE")
	@ExcelColumn(name = "Отправлен", isDate = true)
	private Date departure;
	
	@Column(name = "OWNER")
	@ExcelColumn(name = "Владелец")
	private String owner;
	
	@Column(name = "OPERATOR")
	@ExcelColumn(name = "Оператор")
	private String operator;

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getTitleTrain() {
		return titleTrain;
	}

	public void setTitleTrain(String titleTrain) {
		this.titleTrain = titleTrain;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public Integer getContainerType() {
		return containerType;
	}

	public void setContainerType(Integer containerType) {
		this.containerType = containerType;
	}

	public String getFillingContainer() {
		return fillingContainer;
	}

	public void setFillingContainer(String fillingContainer) {
		this.fillingContainer = fillingContainer;
	}

	public String getSmgs() {
		return smgs;
	}

	public void setSmgs(String smgs) {
		this.smgs = smgs;
	}

	public Date getGettingOnBrw() {
		return gettingOnBrw;
	}

	public void setGettingOnBrw(Date gettingOnBrw) {
		this.gettingOnBrw = gettingOnBrw;
	}

	public Integer getPpv() {
		return ppv;
	}

	public void setPpv(Integer ppv) {
		this.ppv = ppv;
	}

	public String getStaLoading() {
		return staLoading;
	}

	public void setStaLoading(String staLoading) {
		this.staLoading = staLoading;
	}

	public String getStaDirection() {
		return staDirection;
	}

	public void setStaDirection(String staDirection) {
		this.staDirection = staDirection;
	}

	public String getVagonNumber() {
		return vagonNumber;
	}

	public void setVagonNumber(String vagonNumber) {
		this.vagonNumber = vagonNumber;
	}

	public Integer getPrin() {
		return prin;
	}

	public void setPrin(Integer prin) {
		this.prin = prin;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Date getDateReloading() {
		return dateReloading;
	}

	public void setDateReloading(Date dateReloading) {
		this.dateReloading = dateReloading;
	}

	public Integer getNumberTrain() {
		return numberTrain;
	}

	public void setNumberTrain(Integer numberTrain) {
		this.numberTrain = numberTrain;
	}

	public String getIndexTrain() {
		return indexTrain;
	}

	public void setIndexTrain(String indexTrain) {
		this.indexTrain = indexTrain;
	}

	public Date getDeparture() {
		return departure;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setIdInput(Integer idInput) {
		this.idInput = idInput;
	}
	
	
}
