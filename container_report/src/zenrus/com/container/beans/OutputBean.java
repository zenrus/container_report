package zenrus.com.container.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import zenrus.com.container.annotation.ExcelColumn;

@Entity
@Table(name = "INPUT")
public class OutputBean {

	@Id
	@Column(name = "ID_INPUT")
	private Integer idInput;
	
	@Column(name = "REPORT_DATE")
	private Date reportDate;

	@Column(name = "TITLE_TRAIN")
	private String titleTrain;
	
	@Column(name = "NN")
	@ExcelColumn(name = "№ п/п", width= 2)
	private Integer number;
	
	@Column(name = "CONTAINER_NUMBER")
	@ExcelColumn(name = "№ контейнера", width= 8)
	private String containerNumber;
	
	@Column(name = "CONTAINER_TYPE")
	private Integer containerType;
	
	@Column(name = "PRIZNOK_POGRUZKI")
	private String fillingContainer;
	
	@Column(name = "SMSG")
	private String smgs;
	
	@Column(name = "GETTING_ON")
	@ExcelColumn(name = "Дата прибытия на ст. Брест", isDate = true, width = 8)
	private Date gettingOnBrw;
	
	@Column(name = "PPV")
	private Integer ppv;
	
	@Column(name = "STA_LOADING")
	@ExcelColumn(name = "Станция отправления", width = 8)
	private String staLoading;

	@Column(name = "STA_DIRECTION")
	@ExcelColumn(name = "Станция назначения", width = 8)
	private String staDirection;
	
	@Column(name = "VAGON_NUMBER")
	@ExcelColumn(name = "№ платформы БТЛЦ", width = 8)
	private String vagonNumber;
	
	@Column(name = "PRIN")
	private Integer prin;
	
	@Column(name = "LENGTH")
	private Double length;
	
	@Column(name = "DATE_RELOADING")
	private Date dateReloading;
	
	@Column(name = "NUMBER_TRAIN")
	private Integer numberTrain;

	@Column(name = "INDEX_TRAIN")
	private String indexTrain;
	
	@Column(name = "DEPARTURE")
	@ExcelColumn(name = "Дата отправления до ст. Достык ", isDate = true, width = 8)
	private Date departure;
	
	@Column(name = "OWNER")
	private String owner;
	
	@Column(name = "OPERATOR")
	private String operator;
	
	@ExcelColumn(name = "Вознаграждение за доп.услуги по Бресту, USD", width = 8)
	private Double feeAdditionalServices = 25.00d;
	
	@ExcelColumn(name = "Кол-во суток работы фитинговой платформы БТЛЦ", width = 10)
	private Integer daysWorkBtlc;
	
	@ExcelColumn(name = "Вознаграждение за перевозки с использованием фит. Платформ БТЛЦ , USD", width = 10)
	private Double feeBtlc;
	
	@ExcelColumn(name = "Вознаграждение за организацию и формирование КП, USD", width = 10)
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
