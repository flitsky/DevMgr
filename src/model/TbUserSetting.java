package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_user_setting database table.
 * 
 */
@Entity
@Table(name="tb_user_setting")
@NamedQuery(name="TbUserSetting.findAll", query="SELECT t FROM TbUserSetting t")
public class TbUserSetting implements Serializable {
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

	@Column(name="user_setting_default_value")
	private String userSettingDefaultValue;

	@Column(name="user_setting_name")
	private String userSettingName;

	@Column(name="user_setting_type")
	private int userSettingType;

	@Column(name="user_setting_value")
	private String userSettingValue;

	//bi-directional one-to-one association to TbService
	@OneToOne
	@JoinColumn(name="service_id")
	private TbService tbService;

	public TbUserSetting() {
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

	public String getUserSettingDefaultValue() {
		return this.userSettingDefaultValue;
	}

	public void setUserSettingDefaultValue(String userSettingDefaultValue) {
		this.userSettingDefaultValue = userSettingDefaultValue;
	}

	public String getUserSettingName() {
		return this.userSettingName;
	}

	public void setUserSettingName(String userSettingName) {
		this.userSettingName = userSettingName;
	}

	public int getUserSettingType() {
		return this.userSettingType;
	}

	public void setUserSettingType(int userSettingType) {
		this.userSettingType = userSettingType;
	}

	public String getUserSettingValue() {
		return this.userSettingValue;
	}

	public void setUserSettingValue(String userSettingValue) {
		this.userSettingValue = userSettingValue;
	}

	public TbService getTbService() {
		return this.tbService;
	}

	public void setTbService(TbService tbService) {
		this.tbService = tbService;
	}

}