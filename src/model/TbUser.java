package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tb_user database table.
 * 
 */
@Entity
@Table(name="tb_user")
@NamedQuery(name="TbUser.findAll", query="SELECT t FROM TbUser t")
public class TbUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String uid;

	@Column(name="access_token")
	private String accessToken;

	@Lob
	@Column(name="created_timestamp")
	private String createdTimestamp;

	@Lob
	@Column(name="last_timestamp")
	private String lastTimestamp;

	//bi-directional many-to-one association to TbDevice
	@OneToMany(mappedBy="tbUser")
	private List<TbDevice> tbDevices;

	//bi-directional many-to-one association to TbService
	@OneToMany(mappedBy="tbUser")
	private List<TbService> tbServices;

	public TbUser() {
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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

	public List<TbDevice> getTbDevices() {
		return this.tbDevices;
	}

	public void setTbDevices(List<TbDevice> tbDevices) {
		this.tbDevices = tbDevices;
	}

	public TbDevice addTbDevice(TbDevice tbDevice) {
		getTbDevices().add(tbDevice);
		tbDevice.setTbUser(this);

		return tbDevice;
	}

	public TbDevice removeTbDevice(TbDevice tbDevice) {
		getTbDevices().remove(tbDevice);
		tbDevice.setTbUser(null);

		return tbDevice;
	}

	public List<TbService> getTbServices() {
		return this.tbServices;
	}

	public void setTbServices(List<TbService> tbServices) {
		this.tbServices = tbServices;
	}

	public TbService addTbService(TbService tbService) {
		getTbServices().add(tbService);
		tbService.setTbUser(this);

		return tbService;
	}

	public TbService removeTbService(TbService tbService) {
		getTbServices().remove(tbService);
		tbService.setTbUser(null);

		return tbService;
	}

}