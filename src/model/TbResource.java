package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tb_resource database table.
 * 
 */
@Entity
@Table(name="tb_resource")
@NamedQuery(name="TbResource.findAll", query="SELECT t FROM TbResource t")
public class TbResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="resource_id")
	private String resourceId;

	@Lob
	@Column(name="created_timestamp")
	private String createdTimestamp;

	@Column(name="is_observable")
	private byte isObservable;

	@Lob
	@Column(name="last_timestamp")
	private String lastTimestamp;

	@Column(name="ref_count")
	private short refCount;

	@Column(name="resource_name")
	private String resourceName;

	@Column(name="resource_type")
	private String resourceType;

	@Column(name="resource_uri")
	private String resourceUri;

	//bi-directional many-to-one association to TbProperty
	@OneToMany(mappedBy="tbResource")
	private List<TbProperty> tbProperties;

	//bi-directional many-to-one association to TbDevice
	@ManyToOne
	@JoinColumn(name="device_id")
	private TbDevice tbDevice;

	public TbResource() {
	}

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public byte getIsObservable() {
		return this.isObservable;
	}

	public void setIsObservable(byte isObservable) {
		this.isObservable = isObservable;
	}

	public String getLastTimestamp() {
		return this.lastTimestamp;
	}

	public void setLastTimestamp(String lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}

	public short getRefCount() {
		return this.refCount;
	}

	public void setRefCount(short refCount) {
		this.refCount = refCount;
	}

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceUri() {
		return this.resourceUri;
	}

	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	public List<TbProperty> getTbProperties() {
		return this.tbProperties;
	}

	public void setTbProperties(List<TbProperty> tbProperties) {
		this.tbProperties = tbProperties;
	}

	public TbProperty addTbProperty(TbProperty tbProperty) {
		getTbProperties().add(tbProperty);
		tbProperty.setTbResource(this);

		return tbProperty;
	}

	public TbProperty removeTbProperty(TbProperty tbProperty) {
		getTbProperties().remove(tbProperty);
		tbProperty.setTbResource(null);

		return tbProperty;
	}

	public TbDevice getTbDevice() {
		return this.tbDevice;
	}

	public void setTbDevice(TbDevice tbDevice) {
		this.tbDevice = tbDevice;
	}

}