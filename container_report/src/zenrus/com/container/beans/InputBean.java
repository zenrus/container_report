package zenrus.com.container.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INPUT")
public class InputBean {

	private Integer idInput = 0;

	@Id
	@Column(name = "ID_INPUT")
	public Integer getIdInput() {
		return idInput;
	}

	public void setIdInput(Integer idInput) {
		this.idInput = idInput;
	}
	
}
