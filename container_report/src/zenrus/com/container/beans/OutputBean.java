package zenrus.com.container.beans;

import java.util.Date;

import javax.persistence.Column;

import zenrus.com.container.annotation.ExcelColumn;

public class OutputBean {

	private Integer idInput;
	
	private Date reportDate;

	private String titleTrain;
	
	@ExcelColumn(name = "№ п/п")
	private Integer number;
	
	@ExcelColumn(name = "№ контейнера")
	private String containerNumber;
	
	private Integer containerType;
	
	private String fillingContainer;
	
	private String smgs;
	
	@Column(name = "GETTING_ON")
	@ExcelColumn(name = "Дата прибытия на ст. Брест", isDate = true)
	private Date gettingOnBrw;
	
	private Integer ppv;
	
	@ExcelColumn(name = "Станция отправления")
	private String staLoading;

	@ExcelColumn(name = "Станция назначения")
	private String staDirection;
	
	@ExcelColumn(name = "№ платформы БТЛЦ")
	private String vagonNumber;
	
	private Integer prin;
	
	private Double length;
	
	private Date dateReloading;
	
	private Integer numberTrain;

	private String indexTrain;
	
	@ExcelColumn(name = "Дата отправления до ст. Достык ", isDate = true)
	private Date departure;
	
	private String owner;
	
	private String operator;
	
	@ExcelColumn(name = "Вознаграждение за доп.услуги по Бресту, USD")
	private Double feeAdditionalServices = 2.00d;
	
	@ExcelColumn(name = "Кол-во суток работы фитинговой платформы БТЛЦ")
	private Integer daysWorkBtlc;
	
	@ExcelColumn(name = "Вознаграждение за перевозки с использованием фит. Платформ БТЛЦ , USD")
	private Double feeBtlc;
	
	@ExcelColumn(name = "Вознаграждение за организацию и формирование КП, USD")
	private Double feeFormingKp;
	
	@ExcelColumn(name = "Итого, USD")
	private Double feeTotal;

	public Integer getIdInput() {
		return idInput;
	}

	public void setIdInput(Integer idInput) {
		this.idInput = idInput;
	}

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

	public Double getFeeAdditionalServices() {
		return feeAdditionalServices;
	}

	public void setFeeAdditionalServices(Double feeAdditionalServices) {
		this.feeAdditionalServices = feeAdditionalServices;
	}

	public Integer getDaysWorkBtlc() {
		return daysWorkBtlc;
	}

	public void setDaysWorkBtlc(Integer daysWorkBtlc) {
		this.daysWorkBtlc = daysWorkBtlc;
	}

	public Double getFeeBtlc() {
		return feeBtlc;
	}

	public void setFeeBtlc(Double feeBtlc) {
		this.feeBtlc = feeBtlc;
	}

	public Double getFeeFormingKp() {
		return feeFormingKp;
	}

	public void setFeeFormingKp(Double feeFormingKp) {
		this.feeFormingKp = feeFormingKp;
	}

	public Double getFeeTotal() {
		return feeTotal;
	}

	public void setFeeTotal(Double feeTotal) {
		this.feeTotal = feeTotal;
	}
	
}
