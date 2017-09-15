package aries.DAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import aries.DTO.TbDevice;
import aries.DTO.TbUser;
import aries.interoperate.Schema1Body;
import aries.interoperate.Schema2Device;

public class DeviceManagerDAO {
	static String curUid;
	static String curToken;
	
	public boolean storeUserInfo(Schema1Body body) {
		final AriesDAO ariesDAO = new AriesDAO();
		curUid = body.uid;
		curToken = body.accesstoken;
		try {
			TbUser userVO = new TbUser();
			userVO.setUid(body.uid);
			userVO.setAccessToken(body.accesstoken);
			
			Date now = Calendar.getInstance().getTime();
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String lastTimestamp = fmt.format(now);
			userVO.setCreatedTimestamp(lastTimestamp);
			userVO.setLastTimestamp(lastTimestamp);
			
			ariesDAO.storeUser(userVO);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean storeDiscoveredDevices(Schema1Body body) {
		final AriesDAO ariesDAO = new AriesDAO();
		try {
			for(Schema2Device device : body.devices) {
				TbUser userVO = new TbUser();
				userVO.setUid(curUid);
				TbDevice deviceVO = new TbDevice();
				deviceVO.setDeviceId(device.dev_id);
				deviceVO.setTbUser(userVO);
				deviceVO.setDeviceName(device.dev_name);
				deviceVO.setSpecVersion(device.spec_ver);
				deviceVO.setDeviceType(device.dev_type);
				
				Date now = Calendar.getInstance().getTime();
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String lastTimestamp = fmt.format(now);
				deviceVO.setCreatedTimestamp(lastTimestamp);
				deviceVO.setLastTimestamp(lastTimestamp);
				
				ariesDAO.createDevice(deviceVO);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
