package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_resource_map database table.
 * 
 */
@Entity
@Table(name="tb_resource_map")
@NamedQuery(name="TbResourceMap.findAll", query="SELECT t FROM TbResourceMap t")
public class TbResourceMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="service_id")
	private String serviceId;

	@Lob
	@Column(name="created_timestamp")
	private String createdTimestamp;

	@Lob
	@Column(name="last_timestamp")
	private String lastTimestamp;

	@Column(name="resource_id")
	private String resourceId;

	@Column(name="shortened_resource_name")
	private String shortenedResourceName;

	@Column(name="trigger_status")
	private int triggerStatus;

	//bi-directional one-to-one association to TbService
	@OneToOne
	@JoinColumn(name="service_id")
	private TbService tbService;

	public TbResourceMap() {
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
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

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getShortenedResourceName() {
		return this.shortenedResourceName;
	}

	public void setShortenedResourceName(String shortenedResourceName) {
		this.shortenedResourceName = shortenedResourceName;
	}

	public int getTriggerStatus() {
		return this.triggerStatus;
	}

	public void setTriggerStatus(int triggerStatus) {
		this.triggerStatus = triggerStatus;
	}

	public TbService getTbService() {
		return this.tbService;
	}

	public void setTbService(TbService tbService) {
		this.tbService = tbService;
	}

}