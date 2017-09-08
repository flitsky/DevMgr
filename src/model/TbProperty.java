package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_property database table.
 * 
 */
@Entity
@Table(name="tb_property")
@NamedQuery(name="TbProperty.findAll", query="SELECT t FROM TbProperty t")
public class TbProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="property_id")
	private String propertyId;

	@Lob
	@Column(name="created_timestamp")
	private String createdTimestamp;

	@Lob
	@Column(name="last_timestamp")
	private String lastTimestamp;

	@Column(name="last_value")
	private String lastValue;

	@Column(name="property_name")
	private String propertyName;

	@Column(name="property_permission")
	private byte propertyPermission;

	@Column(name="property_type")
	private String propertyType;

	//bi-directional many-to-one association to TbResource
	@ManyToOne
	@JoinColumn(name="resource_id")
	private TbResource tbResource;

	public TbProperty() {
	}

	public String getPropertyId() {
		return this.propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getLastTimestamp() {
		return this.lastTimestamp;
	}

	public void setLastTimestamp(String lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}

	public String getLastValue() {
		return this.lastValue;
	}

	public void setLastValue(String lastValue) {
		this.lastValue = lastValue;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public byte getPropertyPermission() {
		return this.propertyPermission;
	}

	public void setPropertyPermission(byte propertyPermission) {
		this.propertyPermission = propertyPermission;
	}

	public String getPropertyType() {
		return this.propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public TbResource getTbResource() {
		return this.tbResource;
	}

	public void setTbResource(TbResource tbResource) {
		this.tbResource = tbResource;
	}

}