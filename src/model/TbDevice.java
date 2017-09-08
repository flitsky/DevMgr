package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tb_device database table.
 * 
 */
@Entity
@Table(name="tb_device")
@NamedQuery(name="TbDevice.findAll", query="SELECT t FROM TbDevice t")
public class TbDevice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="device_id")
	private String deviceId;

	@Lob
	@Column(name="created_timestamp")
	private String createdTimestamp;

	@Column(name="device_name")
	private String deviceName;

	@Column(name="device_status")
	private int deviceStatus;

	@Column(name="device_type")
	private String deviceType;

	@Lob
	@Column(name="last_timestamp")
	private String lastTimestamp;

	private String manufacturer;

	@Column(name="server_version")
	private String serverVersion;

	@Column(name="spec_version")
	private String specVersion;

	@Column(name="user_defined_name")
	private String userDefinedName;

	//bi-directional many-to-one association to TbUser
	@ManyToOne
	@JoinColumn(name="user_id")
	private TbUser tbUser;

	//bi-directional many-to-one association to TbResource
	@OneToMany(mappedBy="tbDevice")
	private List<TbResource> tbResources;

	public TbDevice() {
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public int getDeviceStatus() {
		return this.deviceStatus;
	}

	public void setDeviceStatus(int deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getLastTimestamp() {
		return this.lastTimestamp;
	}

	public void setLastTimestamp(String lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getServerVersion() {
		return this.serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}

	public String getSpecVersion() {
		return this.specVersion;
	}

	public void setSpecVersion(String specVersion) {
		this.specVersion = specVersion;
	}

	public String getUserDefinedName() {
		return this.userDefinedName;
	}

	public void setUserDefinedName(String userDefinedName) {
		this.userDefinedName = userDefinedName;
	}

	public TbUser getTbUser() {
		return this.tbUser;
	}

	public void setTbUser(TbUser tbUser) {
		this.tbUser = tbUser;
	}

	public List<TbResource> getTbResources() {
		return this.tbResources;
	}

	public void setTbResources(List<TbResource> tbResources) {
		this.tbResources = tbResources;
	}

	public TbResource addTbResource(TbResource tbResource) {
		getTbResources().add(tbResource);
		tbResource.setTbDevice(this);

		return tbResource;
	}

	public TbResource removeTbResource(TbResource tbResource) {
		getTbResources().remove(tbResource);
		tbResource.setTbDevice(null);

		return tbResource;
	}

}