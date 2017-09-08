package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_service database table.
 * 
 */
@Entity
@Table(name="tb_service")
@NamedQuery(name="TbService.findAll", query="SELECT t FROM TbService t")
public class TbService implements Serializable {
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

	@Column(name="script_path")
	private String scriptPath;

	@Column(name="service_name")
	private String serviceName;

	@Column(name="service_status")
	private int serviceStatus;

	@Column(name="service_version")
	private String serviceVersion;

	//bi-directional one-to-one association to TbResourceMap
	@OneToOne(mappedBy="tbService")
	private TbResourceMap tbResourceMap;

	//bi-directional many-to-one association to TbUser
	@ManyToOne
	@JoinColumn(name="user_id")
	private TbUser tbUser;

	//bi-directional one-to-one association to TbUserSetting
	@OneToOne(mappedBy="tbService")
	private TbUserSetting tbUserSetting;

	public TbService() {
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

	public String getScriptPath() {
		return this.scriptPath;
	}

	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getServiceStatus() {
		return this.serviceStatus;
	}

	public void setServiceStatus(int serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getServiceVersion() {
		return this.serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public TbResourceMap getTbResourceMap() {
		return this.tbResourceMap;
	}

	public void setTbResourceMap(TbResourceMap tbResourceMap) {
		this.tbResourceMap = tbResourceMap;
	}

	public TbUser getTbUser() {
		return this.tbUser;
	}

	public void setTbUser(TbUser tbUser) {
		this.tbUser = tbUser;
	}

	public TbUserSetting getTbUserSetting() {
		return this.tbUserSetting;
	}

	public void setTbUserSetting(TbUserSetting tbUserSetting) {
		this.tbUserSetting = tbUserSetting;
	}

}