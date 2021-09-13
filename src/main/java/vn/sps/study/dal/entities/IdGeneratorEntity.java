package vn.sps.study.dal.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "id_generator")
@Access(AccessType.PROPERTY)
public class IdGeneratorEntity implements Serializable{
	private static final long serialVersionUID = 3203029499478943372L;
	private String generatorName;
	private String currentValue;
	private String dataType;
	
	@Id
	public String getGeneratorName() {
		return generatorName;
	}
	public void setGeneratorName(String generatorName) {
		this.generatorName = generatorName;
	}
	public String getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
